package cz.exodus.iam.db.repository;

import cz.exodus.iam.db.entity.ApplicationEntity;
import cz.exodus.iam.db.entity.IdentityEntity;
import cz.exodus.iam.db.entity.IdentityTagEntity;
import cz.exodus.iam.db.entity.TagTypeEntity;
import cz.exodus.iam.model.IdentityTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentityTagRepository extends JpaRepository<IdentityTagEntity, Long> {

    boolean existsIdentityTagEntityByTagTypeAndTagValueAndApplication(TagTypeEntity tagType, String tagValue, ApplicationEntity application);

    long deleteByIdentityAndTagTypeAndApplication(IdentityEntity identity, TagTypeEntity tagType, ApplicationEntity application);

    long deleteByIdentityAndTagTypeAndTagValueAndApplication(IdentityEntity identity, TagTypeEntity tagType, String tagValue, ApplicationEntity application);

    IdentityTagEntity findByIdentityAndTagTypeAndApplication(IdentityEntity identity, TagTypeEntity tagType, ApplicationEntity application);

    IdentityTagEntity findByTagTypeAndTagValueAndApplication(TagTypeEntity tagType, String tagValue, ApplicationEntity application);


    @Query("SELECT new cz.exodus.iam.model.IdentityTag(it.tagType.name, it.tagValue) " +
            "FROM IdentityTagEntity it " +
            "WHERE it.identity.id = :identityId AND it.application.id = :applicationId")
    List<IdentityTag> findTagsByIdentityAndApplication(
            @Param("identityId") Long identityId,
            @Param("applicationId") Long applicationId);
}
