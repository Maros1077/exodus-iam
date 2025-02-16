package cz.exodus.iam.db.repository;

import cz.exodus.iam.db.entity.AuthPointTypeEntity;
import cz.exodus.iam.db.entity.TagTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthPointTypeRepository extends JpaRepository<AuthPointTypeEntity, Long> {

    AuthPointTypeEntity findByName(String name);
}
