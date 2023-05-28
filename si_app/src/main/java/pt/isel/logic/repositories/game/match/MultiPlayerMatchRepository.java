package pt.isel.logic.repositories.game.match;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.matches.multiplayer.MultiPlayerMatch;
import pt.isel.model.entities.game.matches.multiplayer.PartidaMultijogadorId;

import java.util.List;

/**
 * Interface to access the multiplayer matches repository
 */
public interface MultiPlayerMatchRepository extends Repository<MultiPlayerMatch, List<MultiPlayerMatch>, PartidaMultijogadorId> {
}
