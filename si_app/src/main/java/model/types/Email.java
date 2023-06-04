package model.types;

/**
 * Represents an email
 *
 * @param email the email
 */
public record Email(String email) {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Integer MAX_EMAIL_LENGTH = 254;

    /**
     * Checks if the email is valid
     *
     * @return true if the email is valid, false otherwise
     */
    public boolean isValid() {
        return email.matches(EMAIL_REGEX) && email.length() <= MAX_EMAIL_LENGTH;
    }

    @Override
    public String toString() {
        return email;
    }
}
