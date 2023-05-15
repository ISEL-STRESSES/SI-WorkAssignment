package pt.isel.model.types;

public record URL(String url) {
    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$";

    boolean isValid() {
        return url.matches(URL_REGEX);
    }
}
