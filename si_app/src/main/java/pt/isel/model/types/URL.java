package pt.isel.model.types;

public record URL(String url) {

    private static final Integer MAX_URL_LENGTH = 255;
    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$";

    boolean isValid() {
        return url.matches(URL_REGEX);
    }
}
