package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends IAMException {

    public BadRequestException() {
        super(IAMError.REQUEST_INVALID, "Bad request");
    }

    public BadRequestException(String message) {
        super(IAMError.REQUEST_INVALID, "Bad request: " + message);
    }
}
