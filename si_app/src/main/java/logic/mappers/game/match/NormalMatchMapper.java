package logic.mappers.game.match;

import logic.mappers.DataMapper;
import model.entities.game.matches.PartidaId;
import model.entities.game.matches.normal.NormalMatch;
import model.entities.game.matches.normal.PartidaNormal;

/**
 * {@link NormalMatch} mapper interface to be used by the data mappers.
 */
public interface NormalMatchMapper extends DataMapper<PartidaNormal, PartidaId> {
}
