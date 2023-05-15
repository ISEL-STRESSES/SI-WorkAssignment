package pt.isel.model.types;

public record Alphanumeric(String alphanumeric) {
    private static final String ALPHANUMERIC_REGEX = "^[A-Z0-9]+$";

    boolean isValid() {
        return alphanumeric.matches(ALPHANUMERIC_REGEX);
    }
}
