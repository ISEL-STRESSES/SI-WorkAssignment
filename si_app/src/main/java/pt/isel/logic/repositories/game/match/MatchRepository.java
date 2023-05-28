package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.PartidaId;

import java.util.List;

/**
 * {@link Match} repository interface.
 */
public interface MatchRepository extends Repository<Match, List<Match>, PartidaId> {
}
