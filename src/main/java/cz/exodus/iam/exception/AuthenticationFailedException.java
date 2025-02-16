package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class AuthenticationFailedException extends IAMException {

    public AuthenticationFailedException() {
        super(IAMError.AUTHENTICATION_FAILED, "Authentication failed");
    }

}
