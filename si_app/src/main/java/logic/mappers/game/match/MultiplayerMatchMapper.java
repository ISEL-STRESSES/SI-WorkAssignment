package logic.mappers.game.match;

import logic.mappers.DataMapper;
import model.entities.game.matches.multiplayer.MultiPlayerMatch;
import model.entities.game.matches.multiplayer.PartidaMultijogador;
import model.entities.game.matches.multiplayer.PartidaMultijogadorId;

/**
 * {@link MultiPlayerMatch} mapper interface to be used by the data mappers.
 */
public interface MultiplayerMatchMapper extends DataMapper<PartidaMultijogador, PartidaMultijogadorId> {
}
