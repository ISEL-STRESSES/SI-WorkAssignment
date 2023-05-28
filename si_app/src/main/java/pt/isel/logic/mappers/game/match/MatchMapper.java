package pt.isel.logic.mappers.game.match;

import pt.isel.logic.mappers.DataMapper;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.PartidaId;

/**
 * Interface to access the matches repository
 */
public interface MatchMapper extends DataMapper<Match, PartidaId> {
}
