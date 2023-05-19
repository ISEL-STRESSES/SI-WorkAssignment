package pt.isel.logic.repositories;

import java.util.List;

/**
 * Interface that represents a repository
 * @param <T> Entity
 * @param <TCol> Collection of entities
 * @param <TK> Key of the entity
 */
public interface Repository<T,TCol,TK> {
    T findByKey(TK key);
    TCol find(String jpql, Object... params);
    List<T> findAll();

}
