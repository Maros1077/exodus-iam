package cz.exodus.iam.utils;

import cz.exodus.iam.exception.DuplicateTypeException;
import cz.exodus.iam.model.AuthPoint;
import cz.exodus.iam.model.EditAuthPointModel;
import cz.exodus.iam.model.EditTagModel;
import cz.exodus.iam.model.IdentityTag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ValidationUtils {

    public static void checkForTagDuplicates(List<IdentityTag> tags) throws DuplicateTypeException {
        if (tags == null) {
            return;
        }
        Set<String> seenTypes = new HashSet<>();
        for (IdentityTag tag : tags) {
            if (!seenTypes.add(tag.getType())) {
                throw new DuplicateTypeException(tag.getType());
            }
        }
    }

    public static void checkForAuthPointDuplicates(List<AuthPoint> authPoints) throws DuplicateTypeException {
        if (authPoints == null) {
            return;
        }
        Set<String> seenTypes = new HashSet<>();
        for (AuthPoint authPoint : authPoints) {
            if (!seenTypes.add(authPoint.getType())) {
                throw new DuplicateTypeException("Duplicate type found: " + authPoint.getType());
            }
        }
    }
    public static void checkForEditTagModelDuplicates(List<EditTagModel> tags) throws DuplicateTypeException {
        if (tags == null) {
            return;
        }
        Set<String> seenTypes = new HashSet<>();
        for (EditTagModel tag : tags) {
            if (!seenTypes.add(tag.getType())) {
                throw new DuplicateTypeException(tag.getType());
            }
        }
    }

    public static void checkForEditAuthPointModelDuplicates(List<EditAuthPointModel> authPoints) throws DuplicateTypeException {
        if (authPoints == null) {
            return;
        }
        Set<String> seenTypes = new HashSet<>();
        for (EditAuthPointModel authPoint : authPoints) {
            if (!seenTypes.add(authPoint.getType())) {
                throw new DuplicateTypeException("Duplicate type found: " + authPoint.getType());
            }
        }
    }
}
