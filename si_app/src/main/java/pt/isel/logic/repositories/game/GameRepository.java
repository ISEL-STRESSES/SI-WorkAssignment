package pt.isel.logic.repositories.game;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.game.Game;
import pt.isel.model.types.Alphanumeric;

import java.util.List;

/**
 * {@link Game} repository interface.
 */
public interface GameRepository extends Repository<Game, List<Game>, Alphanumeric> {
}
