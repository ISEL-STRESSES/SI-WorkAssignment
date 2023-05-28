package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.game.matches.multiplayer.MultiPlayerMatch;

import java.util.List;

/**
 * {@link MultiPlayerMatch} repository interface.
 * TODO: Check the type of the primary key (Alphanumeric, Integer)
 */
public interface MultiPlayerMatchRepository extends Repository<MultiPlayerMatch, List<MultiPlayerMatch>, Integer> {
}
