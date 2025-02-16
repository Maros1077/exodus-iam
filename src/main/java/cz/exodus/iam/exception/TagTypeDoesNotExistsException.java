package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class TagTypeDoesNotExistsException extends IAMException {

    public TagTypeDoesNotExistsException(String tagType) {
        super(IAMError.TAG_TYPE_DOES_NOT_EXISTS, String.format("Tag type %s is not defined", tagType));
    }

}
