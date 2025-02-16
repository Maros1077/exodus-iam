package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class RequestInvalidException extends IAMException {

    public RequestInvalidException(String message) {
        this(message, null);
    }

    public RequestInvalidException(String message, Throwable cause) {
        super(IAMError.REQUEST_INVALID, message, cause);
    }

}
