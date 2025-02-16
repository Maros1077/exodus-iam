package cz.exodus.iam.service;

import cz.exodus.iam.db.entity.*;
import cz.exodus.iam.db.model.AuthPointType;
import cz.exodus.iam.db.model.IdentityOperationType;
import cz.exodus.iam.db.model.TagType;
import cz.exodus.iam.db.repository.*;
import cz.exodus.iam.exception.*;
import cz.exodus.iam.mapper.AuthPointToAuthPointEntityWithValueMapper;
import cz.exodus.iam.mapper.IdentityTagToIdentityTagEntityWithValueMapper;
import cz.exodus.iam.model.*;
import cz.exodus.iam.rest.AuthResponse;
import cz.exodus.iam.rest.CreateIdentityResponse;
import cz.exodus.iam.rest.RetrieveIdentityResponse;
import cz.exodus.iam.rest.UpdateIdentityResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static cz.exodus.iam.utils.ValidationUtils.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class IAMService {

    private final IdentityRepository identityRepository;
    private final IdentityTagRepository identityTagRepository;
    private final TagTypeRepository tagTypeRepository;
    private final ApplicationRepository applicationRepository;
    private final AuthPointTypeRepository authPointTypeRepository;
    private final IdentityAuthPointRepository identityAuthPointRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(rollbackOn = IAMException.class)
    public CreateIdentityResponse createIdentity(List<IdentityTag> tags, List<AuthPoint> authPoints, String application) throws IAMException {
        ApplicationEntity applicationEntity = getApplication(application);
        checkForTagDuplicates(tags);
        checkForAuthPointDuplicates(authPoints);
        IdentityTagToIdentityTagEntityWithValueMapper tagMapper = new IdentityTagToIdentityTagEntityWithValueMapper(tagTypeRepository);
        List<IdentityTagEntityWithValue> tagEntities = tagMapper.mapToEntityWithValue(tags);
        AuthPointToAuthPointEntityWithValueMapper authPointMapper = new AuthPointToAuthPointEntityWithValueMapper(authPointTypeRepository);
        List<AuthPointEntityWithValue> authPointEntities = authPointMapper.mapToEntityWithValue(authPoints);
        if (areTagsAvailable(tagEntities, applicationEntity)) {
            throw new IdentityAlreadyExists();
        }
        String idid = "idid-" + UUID.randomUUID();
        IdentityEntity newIdentity = identityRepository.save(new IdentityEntity(null));
        identityTagRepository.save(new IdentityTagEntity(null, newIdentity, getTagByType(TagType.IDID.name()), idid, applicationEntity));
        tagEntities.forEach(tag -> identityTagRepository.save(new IdentityTagEntity(null, newIdentity, tag.getType(), tag.getValue(), applicationEntity)));
        authPointEntities.forEach(authPoint -> identityAuthPointRepository.save(new IdentityAuthPointEntity(null, newIdentity, authPoint.getType(), processNewAuthPointValue(authPoint.getType(), authPoint.getValue()), applicationEntity)));
        return new CreateIdentityResponse(idid);
    }

    @Transactional(rollbackOn = IAMException.class)
    public RetrieveIdentityResponse retrieveIdentity(String application, IdentityTag identificationTag, boolean retrieveTags, boolean retrieveAuthPoints) throws IAMException {
        ApplicationEntity applicationEntity = getApplication(application);
        IdentityTagEntity entityTag = getIdentity(identificationTag, applicationEntity);
        IdentityTagEntity entityIdidTag;
        if (TagType.getByValue(entityTag.getTagType().getName()) == TagType.IDID) {
            entityIdidTag = entityTag;
        } else {
            entityIdidTag = identityTagRepository.findByIdentityAndTagTypeAndApplication(entityTag.getIdentity(), getTagByType(TagType.IDID.name()), applicationEntity);
        }
        List<IdentityTag> identityTags = null;
        List<String> authPoints = null;
        if (retrieveTags) {
            identityTags = identityTagRepository.findTagsByIdentityAndApplication(entityTag.getIdentity().getId(), applicationEntity.getId());
        }
        if (retrieveAuthPoints) {
            authPoints = identityAuthPointRepository.findAuthPointNamesByIdentityAndApplication(entityTag.getIdentity().getId(), applicationEntity.getId());
        }
        return new RetrieveIdentityResponse(entityIdidTag.getTagValue(), applicationEntity.getName(), identityTags, authPoints);
    }

    @Transactional(rollbackOn = IAMException.class)
    public UpdateIdentityResponse updateIdentity(String application, IdentityTag identificationTag, List<EditTagModel> tags, List<EditAuthPointModel> authPoints) throws IAMException {
        ApplicationEntity applicationEntity = getApplication(application);
        checkForEditTagModelDuplicates(tags);
        checkForEditAuthPointModelDuplicates(authPoints);
        IdentityTagEntity entityTag = getIdentity(identificationTag, applicationEntity);
        int affectedTags = 0;
        if (tags != null) {
            for (EditTagModel tagModel : tags) {
                switch (IdentityOperationType.getByValue(tagModel.getOperation())) {
                    case UPDATE:
                        if (updateTag(entityTag.getIdentity(), tagModel, applicationEntity)) {
                            affectedTags++;
                        }
                        break;
                    case DELETE:
                        if (deleteTag(entityTag.getIdentity(), tagModel, applicationEntity))
                            affectedTags++;
                        break;
                }
            }
        }

        int affectedAuthPoints = 0;
        if (authPoints != null) {
            for (EditAuthPointModel authPointModel : authPoints) {
                switch (IdentityOperationType.getByValue(authPointModel.getOperation())) {
                    case UPDATE:
                        if (updateAuthPoint(entityTag.getIdentity(), authPointModel, applicationEntity)) {
                            affectedAuthPoints++;
                        }
                        break;
                    case DELETE:
                        if (deleteAuthPoint(entityTag.getIdentity(), authPointModel, applicationEntity)) {
                            affectedAuthPoints++;
                        }
                        break;
                }
            }
        }
        return new UpdateIdentityResponse(affectedTags, affectedAuthPoints);
    }

    public AuthResponse auth(IdentityTag identificationTag, AuthPoint authPoint, String application) throws IAMException {
        ApplicationEntity applicationEntity = getApplication(application);
        IdentityTagEntity identityTag;
        try {
            identityTag = getIdentity(identificationTag, applicationEntity);
        } catch (IdentityNotFoundException e) {
            log.debug("Authentication failed - Identity not found");
            throw new AuthenticationFailedException();
        }
        AuthPointTypeEntity authPointTypeEntity = getAuthByType(authPoint.getType());
        if (isAuthPointValueMatching(identityTag.getIdentity(), authPointTypeEntity, authPoint.getValue(), applicationEntity)) {
            // TODO - call to STS
            return new AuthResponse("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", 3600);
        } else {
            log.debug("Authentication failed - Wrong value");
            throw new AuthenticationFailedException();
        }
    }

    private boolean updateTag(IdentityEntity identity, EditTagModel tagModel, ApplicationEntity application) throws IAMException {
        TagTypeEntity tagTypeEntity = getTagByType(tagModel.getType());
        Optional<IdentityTagEntity> originalTagEntity = Optional.ofNullable(identityTagRepository.findByIdentityAndTagTypeAndApplication(identity, tagTypeEntity, application));
        if (originalTagEntity.isEmpty() && tagModel.getValue() == null) {
            identityTagRepository.save(new IdentityTagEntity(null, identity, tagTypeEntity, tagModel.getNewValue(), application));
            log.debug("IdentityTagEntity does not exist and the value from request is set to null: " + tagModel);
            return true;
        }
        if (originalTagEntity.isEmpty()) {
            log.debug("IdentityTagEntity does not exist and the value in request is specified: " + tagModel);
            return false;
        }
        if (tagModel.getValue() != null && !originalTagEntity.get().getTagValue().equals(tagModel.getValue())) {
            log.debug("The value from request does not match the real one: " + tagModel);
            return false;
        }
        IdentityTagEntity updatedTagEntity = originalTagEntity.get();
        updatedTagEntity.setTagValue(tagModel.getNewValue());
        identityTagRepository.save(updatedTagEntity);
        log.debug("IdentityTagEntity edited: " + tagModel);
        return true;
    }

    private boolean deleteTag(IdentityEntity identity, EditTagModel tagModel, ApplicationEntity application) throws IAMException {
        if (tagModel.getType().equalsIgnoreCase(TagType.IDID.name())) {
            throw new BadRequestException("IDID tag can not be deleted!");
        }
        TagTypeEntity tagTypeEntity = getTagByType(tagModel.getType());
        long deletedRecords;
        if (tagModel.getValue() == null) {
            deletedRecords = identityTagRepository.deleteByIdentityAndTagTypeAndApplication(identity, tagTypeEntity, application);
            log.debug("Deleting tag without value check: " + tagModel);
        } else {
            deletedRecords = identityTagRepository.deleteByIdentityAndTagTypeAndTagValueAndApplication(identity, tagTypeEntity, tagModel.getValue(), application);
            log.debug("Deleting tag with value check: " + tagModel);
        }
        return deletedRecords != 0;
    }

    private boolean updateAuthPoint(IdentityEntity identity, EditAuthPointModel authPointModel, ApplicationEntity application) throws IAMException {
        AuthPointTypeEntity authPointTypeEntity = getAuthByType(authPointModel.getType());
        Optional<IdentityAuthPointEntity> originalIdentityAuthPointEntity = Optional.ofNullable(identityAuthPointRepository.findByIdentityAndAuthPointTypeAndApplication(identity, authPointTypeEntity, application));
        if (originalIdentityAuthPointEntity.isEmpty()) {
            identityAuthPointRepository.save(new IdentityAuthPointEntity(null, identity, authPointTypeEntity, processNewAuthPointValue(authPointTypeEntity, authPointModel.getNewValue()), application));
            log.debug("IdentityAuthPointEntity does not exist, creating new: " + authPointModel);
        } else {
            IdentityAuthPointEntity updatedIdentityAuthPointEntity = originalIdentityAuthPointEntity.get();
            updatedIdentityAuthPointEntity.setAuthPointValue(processNewAuthPointValue(authPointTypeEntity, authPointModel.getNewValue()));
            identityAuthPointRepository.save(updatedIdentityAuthPointEntity);
            log.debug("IdentityAuthPointEntity exists, updating: " + authPointModel);
        }
        return true;
    }

    private boolean deleteAuthPoint(IdentityEntity identity, EditAuthPointModel authPointModel, ApplicationEntity application) throws IAMException {
        AuthPointTypeEntity authPointTypeEntity = getAuthByType(authPointModel.getType());
        long deletedRecords = identityAuthPointRepository.deleteByIdentityAndAuthPointTypeAndApplication(identity, authPointTypeEntity, application);
        log.debug("Deleted: " + deletedRecords + " authPoints: " + authPointModel);
        return deletedRecords != 0;
    }

    private boolean areTagsAvailable(List<IdentityTagEntityWithValue> tags, ApplicationEntity application) {
        return tags.stream().anyMatch(tag -> identityTagRepository
                .existsIdentityTagEntityByTagTypeAndTagValueAndApplication(tag.getType(), tag.getValue(), application));
    }

    private ApplicationEntity getApplication(String application) throws IAMException {
        Optional<ApplicationEntity> entity = Optional.ofNullable(applicationRepository.findByName(application));
        if (entity.isEmpty()) {
            throw new ApplicationDoesNotExistsException(application);
        }
        return entity.get();
    }

    private TagTypeEntity getTagByType(String tagName) throws TagTypeDoesNotExistsException {
        Optional<TagTypeEntity> tagTypeEntity = Optional.ofNullable(tagTypeRepository.findByName(tagName));
        if (tagTypeEntity.isEmpty()) {
            throw new TagTypeDoesNotExistsException(tagName);
        }
        return tagTypeEntity.get();
    }

    private AuthPointTypeEntity getAuthByType(String authName) throws AuthPointTypeDoesNotExistsException {
        Optional<AuthPointTypeEntity> authPointType = Optional.ofNullable(authPointTypeRepository.findByName(authName));
        if (authPointType.isEmpty()) {
            throw new AuthPointTypeDoesNotExistsException(authName);
        }
        return authPointType.get();
    }

    private String processNewAuthPointValue(AuthPointTypeEntity authPointType, String authPointValue) {
        if (authPointValue == null) {
            return null;
        }
        String proceedValue;
        switch (AuthPointType.getByValue(authPointType.getName())) {
            case PASSWORD -> proceedValue = hashPassword(authPointValue);
            default -> proceedValue = authPointValue;
        }
        return proceedValue;
    }

    private boolean isAuthPointValueMatching(IdentityEntity identity, AuthPointTypeEntity authPointType, String authPointValue, ApplicationEntity application) throws IAMException {
        IdentityAuthPointEntity identityAuthPoint = getIdentityAuthPoint(identity, authPointType, application);
        switch (AuthPointType.getByValue(authPointType.getName())) {
            case PASSWORD -> {
                if (identityAuthPoint.getAuthPointValue() == null) {
                    throw new AuthPointValueNotSetException(authPointType.getName());
                }
                return isPasswordMatching(authPointValue, identityAuthPoint.getAuthPointValue());
            }
            default -> {
                return identityAuthPoint.getAuthPointValue().equals(authPointValue);
            }
        }
    }

    private String hashPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    private boolean isPasswordMatching(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }

    private IdentityTagEntity getIdentity(IdentityTag identityTag, ApplicationEntity application) throws IAMException {
        TagTypeEntity tagTypeEntity = getTagByType(identityTag.getType());
        Optional<IdentityTagEntity> entityTag = Optional.ofNullable(identityTagRepository
                .findByTagTypeAndTagValueAndApplication(tagTypeEntity, identityTag.getValue(), application));
        if (entityTag.isEmpty()) {
            throw new IdentityNotFoundException();
        } else {
            return entityTag.get();
        }
    }

    private IdentityAuthPointEntity getIdentityAuthPoint(IdentityEntity identity, AuthPointTypeEntity authPoint, ApplicationEntity application) throws IAMException {
        Optional<IdentityAuthPointEntity> authPointEntity = Optional.ofNullable(identityAuthPointRepository.findByIdentityAndAuthPointTypeAndApplication(identity, authPoint, application));
        if (authPointEntity.isEmpty()) {
            throw new AuthPointNotSetException(authPoint.getName());
        } else {
            return authPointEntity.get();
        }
    }
}
