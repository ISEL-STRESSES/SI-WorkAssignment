package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.matches.MultiPlayerMatch;

import java.util.List;

/**
 * Interface to access the multi-player matches repository
 * TODO: Check the type of the primary key (Alphanumeric, Integer)
 */
public interface MultiPlayerMatchRepository extends Repository<MultiPlayerMatch, List<MultiPlayerMatch>, Integer> {
}
