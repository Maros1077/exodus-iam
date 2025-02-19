package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class OidcClientDoesNotExistsException extends IAMException {

    public OidcClientDoesNotExistsException(String clientId, String application) {
        super(IAMError.OIDC_CLIENT_DOES_NOT_EXISTS, String.format("OIDC client %s is not defined for application %s", clientId, application));
    }

}
