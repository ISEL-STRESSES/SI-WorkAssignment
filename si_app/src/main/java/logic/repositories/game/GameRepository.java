package logic.repositories.game;

import logic.repositories.Repository;
import model.entities.game.Game;
import model.types.Alphanumeric;

import java.util.List;

/**
 * {@link Game} repository interface.
 */
public interface GameRepository extends Repository<Game, List<Game>, Alphanumeric> {
}
