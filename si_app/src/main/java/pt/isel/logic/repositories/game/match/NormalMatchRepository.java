package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.matches.normal.NormalMatch;

import java.util.List;

/**
 * Interface to access the normal matches repository
 * TODO: Check the type of the primary key (Alphanumeric, Integer)
 */
public interface NormalMatchRepository extends Repository<NormalMatch, List<NormalMatch>, Integer> {
}
