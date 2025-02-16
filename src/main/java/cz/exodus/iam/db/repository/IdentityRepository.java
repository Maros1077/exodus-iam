package cz.exodus.iam.db.repository;

import cz.exodus.iam.db.entity.IdentityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityRepository extends JpaRepository<IdentityEntity, Long> {

}
