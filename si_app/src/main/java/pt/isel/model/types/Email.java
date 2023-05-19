package pt.isel.model.types;

public record Email(String email) {

    private static final Integer MAX_EMAIL_LENGTH = 254;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";

    public boolean isValid() {
        return email.matches(EMAIL_REGEX) && email.length() <= MAX_EMAIL_LENGTH;
    }
};
