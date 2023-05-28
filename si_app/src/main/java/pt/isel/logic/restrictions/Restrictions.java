package pt.isel.logic.restrictions;

/**
 * Interface IRestrictions has the signature that all the tables restrictions classes are going to implement.
 *
 * @param <T>
 */
public interface Restrictions<T> {
    Boolean checkDBRestrictions(T entity) throws IllegalArgumentException, RestrictionException;
}
