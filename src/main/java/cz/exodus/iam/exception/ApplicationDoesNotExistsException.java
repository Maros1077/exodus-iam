package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class ApplicationDoesNotExistsException extends IAMException {

    public ApplicationDoesNotExistsException(String application) {
        super(IAMError.APPLICATION_DOES_NOT_EXISTS, String.format("Application %s is not defined", application));
    }

}
