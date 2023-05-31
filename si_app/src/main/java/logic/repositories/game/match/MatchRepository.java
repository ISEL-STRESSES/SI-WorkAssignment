package logic.repositories.game.match;

import logic.repositories.Repository;
import model.entities.game.matches.Match;
import model.entities.game.matches.PartidaId;

import java.util.List;

/**
 * {@link Match} repository interface.
 */
public interface MatchRepository extends Repository<Match, List<Match>, PartidaId> {
}
