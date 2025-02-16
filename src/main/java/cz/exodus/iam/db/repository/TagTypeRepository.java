package cz.exodus.iam.db.repository;

import cz.exodus.iam.db.entity.TagTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagTypeRepository extends JpaRepository<TagTypeEntity, Long> {

    TagTypeEntity findByName(String name);
}
