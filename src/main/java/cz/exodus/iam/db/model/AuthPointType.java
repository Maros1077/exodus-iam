package cz.exodus.iam.db.model;

public enum AuthPointType {
    PASSWORD,
    OTP,
    OTHER;

    public static AuthPointType getByValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            return OTHER;
        }
    }
}
