package cz.exodus.iam.db.repository;

import cz.exodus.iam.db.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentityAuthPointRepository extends JpaRepository<IdentityAuthPointEntity, Long> {

    long deleteByIdentityAndAuthPointTypeAndApplication(IdentityEntity identity, AuthPointTypeEntity authPointType, ApplicationEntity application);

    boolean existsIdentityAuthPointEntityByIdentityAndAuthPointTypeAndApplication(IdentityEntity identity, AuthPointTypeEntity authPointType, ApplicationEntity application);

    boolean existsIdentityAuthPointEntityByIdentityAndAuthPointTypeAndAuthPointValueAndApplication(IdentityEntity identity, AuthPointTypeEntity authPointType, String authPointValue, ApplicationEntity application);

    IdentityAuthPointEntity findByIdentityAndAuthPointTypeAndApplication(IdentityEntity identity, AuthPointTypeEntity authPointType, ApplicationEntity application);

    @Query("SELECT iap.authPointType.name " +
            "FROM IdentityAuthPointEntity iap " +
            "WHERE iap.identity.id = :identityId AND iap.application.id = :applicationId")
    List<String> findAuthPointNamesByIdentityAndApplication(
            @Param("identityId") Long identityId,
            @Param("applicationId") Long applicationId);
}
