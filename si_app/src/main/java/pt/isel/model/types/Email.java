package pt.isel.model.types;

public record Email(String email) {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,}$";

    boolean isValid() {
        return email.matches(EMAIL_REGEX);
    }
};

class some {
    void test() {
        var e = new Email("a");
    }
}
