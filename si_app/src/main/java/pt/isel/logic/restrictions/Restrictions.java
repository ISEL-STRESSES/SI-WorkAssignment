package pt.isel.logic.restrictions;

/**
 * Restrictions interface that all restriction classes must implement.
 *
 * @param <T>
 */
public interface Restrictions<T> {

    /**
     * Checks if the given entity satisfies the restrictions.
     *
     * @param entity the entity to check
     * @return true if the entity satisfies the restrictions, false otherwise
     * @throws IllegalArgumentException if the entity is null
     * @throws RestrictionException      if the entity violates the restrictions
     */
    Boolean checkDBRestrictions(T entity) throws IllegalArgumentException, RestrictionException;
}
