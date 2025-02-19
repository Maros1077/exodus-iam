package cz.exodus.iam.db.repository;

import cz.exodus.iam.db.entity.*;
import cz.exodus.iam.model.IdentityTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OidcClientRepository extends JpaRepository<OidcClientEntity, Long> {

    boolean existsOidcClientEntityByApplicationAndClientIdAndGrantType(ApplicationEntity application, String clientId, String grantType);

    OidcClientEntity findByApplicationAndClientIdAndGrantType(ApplicationEntity application, String clientId, String grantType);

}
