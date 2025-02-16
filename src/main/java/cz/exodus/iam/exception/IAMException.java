package cz.exodus.iam.exception;

import cz.exodus.jsend.network.exception.BaseException;
import lombok.Getter;

import java.util.UUID;

@Getter
public abstract class IAMException extends BaseException {

    final IAMError iamError;

    public IAMException(IAMError iamError, String message) {
        this(iamError, message, null);
    }

    public IAMException(IAMError iamError, String message, Throwable cause) {
        super(message, cause, UUID.randomUUID().toString());
        this.iamError = iamError;
    }

}
