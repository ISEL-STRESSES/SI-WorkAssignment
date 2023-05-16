package pt.isel.model.types;

public record Alphanumeric(String alphanumeric) {

    private static final Integer MAX_LENGTH = 10;
    private static final String ALPHANUMERIC_REGEX = "^[A-Z0-9]+$";

    boolean isValid() {
        return alphanumeric.matches(ALPHANUMERIC_REGEX) && alphanumeric.length() <= MAX_LENGTH;
    }
}
