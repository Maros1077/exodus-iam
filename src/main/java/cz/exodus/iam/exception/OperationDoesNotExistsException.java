package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class OperationDoesNotExistsException extends IAMException {

    public OperationDoesNotExistsException(String operation) {
        super(IAMError.OPERATION_DOES_NOT_EXISTS, String.format("Operation type %s is not defined", operation));
    }

}
