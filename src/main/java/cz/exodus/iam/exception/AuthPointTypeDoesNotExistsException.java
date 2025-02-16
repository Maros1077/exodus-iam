package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class AuthPointTypeDoesNotExistsException extends IAMException {

    public AuthPointTypeDoesNotExistsException(String authPoint) {
        super(IAMError.AUTH_POINT_TYPE_DOES_NOT_EXISTS, String.format("Auth point type %s is not defined", authPoint));
    }

}
