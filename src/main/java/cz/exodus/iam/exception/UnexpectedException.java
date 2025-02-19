package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class UnexpectedException extends IAMException {

    public UnexpectedException(String message, Throwable cause) {
        super(IAMError.UNEXPECTED, message, cause);
    }

}
