package cz.exodus.iam.db.repository;

import cz.exodus.iam.db.entity.ApplicationEntity;
import cz.exodus.iam.db.entity.TagTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    ApplicationEntity findByName(String name);
}
