package pt.isel.logic.mappers.game.match;

import pt.isel.logic.mappers.DataMapper;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.PartidaId;

/**
 * {@link Match} mapper interface to be used by the data mappers.
 * TODO check the key
 */
public interface MatchMapper extends DataMapper<Match, PartidaId> {
}
