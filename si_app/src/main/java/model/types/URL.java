package model.types;

/**
 * Represents an URL
 *
 * @param url the URL
 */
public record URL(String url) {

    private static final Integer MAX_URL_LENGTH = 255;
    private static final String URL_REGEX = "^(http|https)://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$";

    /**
     * Checks if the URL is valid
     *
     * @return true if the URL is valid, false otherwise
     */
    boolean isValid() {
        return url.matches(URL_REGEX) && url.length() <= MAX_URL_LENGTH;
    }
}
