package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class DuplicateTypeException extends IAMException {

    public DuplicateTypeException(String duplicatedValue) {
        super(IAMError.DUPLICATION_IN_REQUEST_FOUND, String.format("Duplicate type found: %s", duplicatedValue));
    }

}
