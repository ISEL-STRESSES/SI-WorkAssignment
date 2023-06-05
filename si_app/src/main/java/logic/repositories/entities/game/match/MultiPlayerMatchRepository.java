package logic.repositories.entities.game.match;

import logic.repositories.Repository;
import model.entities.game.matches.PartidaId;
import model.entities.game.matches.multiplayer.MultiPlayerMatch;
import model.entities.game.matches.multiplayer.PartidaMultijogador;

import java.util.List;

/**
 * {@link MultiPlayerMatch} repository interface.
 */
public interface MultiPlayerMatchRepository extends Repository<PartidaMultijogador, List<PartidaMultijogador>, PartidaId> {
}
