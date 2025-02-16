package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class AuthPointValueNotSetException extends IAMException {

    public AuthPointValueNotSetException(String authPoint) {
        super(IAMError.AUTH_POINT_VALUE_NOT_SET, String.format("Auth point type does not have any value set for identity", authPoint));
    }

}
