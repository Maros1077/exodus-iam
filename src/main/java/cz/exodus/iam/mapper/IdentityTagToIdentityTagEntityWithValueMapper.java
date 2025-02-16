package cz.exodus.iam.mapper;

import cz.exodus.iam.db.entity.TagTypeEntity;
import cz.exodus.iam.db.repository.TagTypeRepository;
import cz.exodus.iam.exception.IAMException;
import cz.exodus.iam.exception.TagTypeDoesNotExistsException;
import cz.exodus.iam.model.IdentityTag;
import cz.exodus.iam.model.IdentityTagEntityWithValue;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class IdentityTagToIdentityTagEntityWithValueMapper {

    private final TagTypeRepository tagTypeRepository;

    public List<IdentityTagEntityWithValue> mapToEntityWithValue(List<IdentityTag> identityTags ) throws IAMException {
        List<IdentityTagEntityWithValue> result = new ArrayList<>();
        if (identityTags == null) {
            return result;
        }
        List<TagTypeEntity> tagTypeEntities = getTags(identityTags);

        for (int i = 0; i < identityTags.size(); i++) {
            IdentityTag identityTag = identityTags.get(i);
            TagTypeEntity tagTypeEntity = tagTypeEntities.get(i);
            IdentityTagEntityWithValue entityWithValue = new IdentityTagEntityWithValue(tagTypeEntity, identityTag.getValue());
            result.add(entityWithValue);
        }
        return result;
    }

    private List<TagTypeEntity> getTags(List<IdentityTag> tags) throws IAMException {
        List<TagTypeEntity> list = new ArrayList<>();
        for (IdentityTag tag : tags) {
            Optional<TagTypeEntity> entity = Optional.ofNullable(getTagByType(tag.getType()));
            if (entity.isEmpty()) {
                throw new TagTypeDoesNotExistsException(tag.getType());
            }
            list.add(entity.get());
        }
        return list;
    }

    private TagTypeEntity getTagByType(String tagName) {
        return tagTypeRepository.findByName(tagName);
    }
}
