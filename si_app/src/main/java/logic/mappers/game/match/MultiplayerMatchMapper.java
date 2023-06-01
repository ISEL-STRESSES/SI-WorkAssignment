package logic.mappers.game.match;

import logic.mappers.DataMapper;
import model.entities.game.matches.PartidaId;
import model.entities.game.matches.multiplayer.MultiPlayerMatch;
import model.entities.game.matches.multiplayer.PartidaMultijogador;

/**
 * {@link MultiPlayerMatch} mapper interface to be used by the data mappers.
 */
public interface MultiplayerMatchMapper extends DataMapper<PartidaMultijogador, PartidaId> {
}
