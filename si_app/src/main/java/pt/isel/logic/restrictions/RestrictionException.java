package pt.isel.logic.restrictions;

/**
 * Exception thrown when a restriction is violated.
 * Extends {@link Exception}.
 */
public class RestrictionException extends Exception {
    public RestrictionException(String message) {
        super(message);
    }
}
