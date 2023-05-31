package logic.mappers.game.match;

import logic.mappers.DataMapper;
import model.entities.game.matches.Match;
import model.entities.game.matches.Partida;
import model.entities.game.matches.PartidaId;

/**
 * {@link Match} mapper interface to be used by the data mappers.
 */
public interface MatchMapper extends DataMapper<Partida, PartidaId> {
}
