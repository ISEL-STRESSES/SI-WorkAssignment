package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.game.matches.normal.NormalMatch;

import java.util.List;

/**
 * {@link NormalMatch} repository interface.
 * TODO: Check the type of the primary key (Alphanumeric, Integer)
 */
public interface NormalMatchRepository extends Repository<NormalMatch, List<NormalMatch>, Integer> {
}
