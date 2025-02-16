package cz.exodus.iam.exception;

import lombok.Getter;

@Getter
public class IdentityNotFoundException extends IAMException {

    public IdentityNotFoundException() {
        super(IAMError.IDENTITY_NOT_FOUND, "Identity not found");
    }

}
