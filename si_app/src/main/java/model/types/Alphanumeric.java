package model.types;

/**
 * Represents an alphanumeric string
 *
 * @param alphanumeric the alphanumeric string
 */
public record Alphanumeric(String alphanumeric) {

    private static final Integer MAX_LENGTH = 10;
    private static final String ALPHANUMERIC_REGEX = "^[A-Z0-9]+$";

    /**
     * Checks if the alphanumeric string is valid
     *
     * @return true if the alphanumeric string is valid, false otherwise
     */
    public boolean isValid() {
        return alphanumeric.matches(ALPHANUMERIC_REGEX) && alphanumeric.length() <= MAX_LENGTH;
    }
}
