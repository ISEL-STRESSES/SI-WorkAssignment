package logic.repositories.entities.game.match;

import logic.repositories.Repository;
import model.entities.game.matches.PartidaId;
import model.entities.game.matches.normal.NormalMatch;
import model.entities.game.matches.normal.PartidaNormal;

import java.util.List;

/**
 * {@link NormalMatch} repository interface.
 */
public interface NormalMatchRepository extends Repository<PartidaNormal, List<PartidaNormal>, PartidaId> {
}
