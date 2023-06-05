package logic.repositories.entities.game;

import logic.repositories.Repository;
import model.entities.game.Game;
import model.entities.game.Jogo;
import model.types.Alphanumeric;

import java.util.List;

/**
 * {@link Game} repository interface.
 */
public interface GameRepository extends Repository<Jogo, List<Jogo>, Alphanumeric> {
    /**
     * Gets the {@link Game} with the given name.
     *
     * @param name the {@link Game} name
     * @return the {@link Game} with the given name
     */
    Jogo findByName(String name);
}
