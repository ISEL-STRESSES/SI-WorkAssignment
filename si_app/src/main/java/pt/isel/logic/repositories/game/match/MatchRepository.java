package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.matches.Match;

import java.util.List;

/**
 * Interface to access the matches repository
 * TODO: Check the type of the primary key (Alphanumeric, Integer)
 */
public interface MatchRepository extends Repository<Match, List<Match>, Integer> {
}
