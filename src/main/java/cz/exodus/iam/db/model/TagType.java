package cz.exodus.iam.db.model;

import cz.exodus.iam.exception.IAMException;
import cz.exodus.iam.exception.TagTypeDoesNotExistsException;

public enum TagType {
    IDID,
    EMAIL,
    NICK,
    PHONE_NUMBER;

    public static TagType getByValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
