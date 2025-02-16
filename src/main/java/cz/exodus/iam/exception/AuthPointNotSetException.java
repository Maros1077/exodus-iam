package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class AuthPointNotSetException extends IAMException {

    public AuthPointNotSetException(String authPoint) {
        super(IAMError.AUTH_POINT_NOT_SET, String.format("Auth point type %s is not set for identity", authPoint));
    }

}
