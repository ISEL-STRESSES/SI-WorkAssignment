package logic.repositories.game.match;

import logic.repositories.Repository;
import model.entities.game.matches.normal.NormalMatch;
import model.entities.game.matches.normal.PartidaNormalId;

import java.util.List;

/**
 * {@link NormalMatch} repository interface.
 */
public interface NormalMatchRepository extends Repository<NormalMatch, List<NormalMatch>, PartidaNormalId> {
}
