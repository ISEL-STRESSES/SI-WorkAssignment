package logic.repositories.entities.game.match;

import logic.repositories.Repository;
import model.entities.game.matches.Match;
import model.entities.game.matches.Partida;
import model.entities.game.matches.PartidaId;

import java.util.List;

/**
 * {@link Match} repository interface.
 */
public interface MatchRepository extends Repository<Partida, List<Partida>, PartidaId> {
}
