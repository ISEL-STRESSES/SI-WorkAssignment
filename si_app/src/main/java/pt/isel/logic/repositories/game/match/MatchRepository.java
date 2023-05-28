package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.PartidaId;

import java.util.List;

/**
 * Interface to access the matches repository
 */
public interface MatchRepository extends Repository<Match, List<Match>, PartidaId> {
}
