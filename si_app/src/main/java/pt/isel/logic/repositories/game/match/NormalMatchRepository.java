package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.matches.normal.NormalMatch;
import pt.isel.model.entities.game.matches.normal.PartidaNormalId;

import java.util.List;

/**
 * Interface to access the normal matches repository
 */
public interface NormalMatchRepository extends Repository<NormalMatch, List<NormalMatch>, PartidaNormalId> {
}
