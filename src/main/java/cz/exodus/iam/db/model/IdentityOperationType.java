package cz.exodus.iam.db.model;

import cz.exodus.iam.exception.IAMException;
import cz.exodus.iam.exception.OperationDoesNotExistsException;
import cz.exodus.iam.exception.TagTypeDoesNotExistsException;

public enum IdentityOperationType {
    UPDATE,
    DELETE;

    public static IdentityOperationType getByValue(String value) throws IAMException {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new OperationDoesNotExistsException(value);
        }
    }
}
