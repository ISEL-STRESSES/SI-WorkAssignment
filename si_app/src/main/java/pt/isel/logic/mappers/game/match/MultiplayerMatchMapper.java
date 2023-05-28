package pt.isel.logic.mappers.game.match;

import pt.isel.logic.mappers.DataMapper;
import pt.isel.model.entities.game.matches.multiplayer.MultiPlayerMatch;
import pt.isel.model.entities.game.matches.multiplayer.PartidaMultijogadorId;

/**
 * {@link MultiPlayerMatch} mapper interface to be used by the data mappers.
 */
public interface MultiplayerMatchMapper extends DataMapper<MultiPlayerMatch, PartidaMultijogadorId> {
}
