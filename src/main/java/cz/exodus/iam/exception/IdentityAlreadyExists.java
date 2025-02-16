package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class IdentityAlreadyExists extends IAMException {

    public IdentityAlreadyExists() {
        super(IAMError.IDENTITY_ALREADY_EXISTS, "Identity already exists");
    }

}
