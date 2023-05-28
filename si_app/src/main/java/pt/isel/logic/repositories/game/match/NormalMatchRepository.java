package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.matches.normal.NormalMatch;
import pt.isel.model.entities.game.matches.normal.PartidaNormalId;

import java.util.List;

/**
 * {@link NormalMatch} repository interface.
 */
public interface NormalMatchRepository extends Repository<NormalMatch, List<NormalMatch>, PartidaNormalId> {
}
