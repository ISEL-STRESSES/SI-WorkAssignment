package pt.isel.logic.repositories;

import java.util.List;

/**
 * Interface that represents a repository
 *
 * @param <T>    Entity
 * @param <TCol> Collection of entities
 * @param <TK>   Key of the entity
 */
public interface Repository<T, TCol, TK> {
    /**
     * Finds an entity by its key
     *
     * @param key Key of the entity
     * @return Entity
     */
    T findByKey(TK key);

    /**
     * Finds a Collection of entities by the given query
     *
     * @param jpql   query to be executed
     * @param params parameters of the query
     * @return Collection of entities
     */
    TCol find(String jpql, Object... params);

    /**
     * Finds all entities
     *
     * @return Collection of entities
     */
    List<T> findAll();
}
