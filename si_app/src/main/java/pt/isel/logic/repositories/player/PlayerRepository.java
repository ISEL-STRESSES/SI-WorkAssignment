package pt.isel.logic.repositories.player;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.player.Player;
import pt.isel.model.types.Email;

import java.util.List;

/**
 * {@link Player} repository interface.
 */
public interface PlayerRepository extends Repository<Player, List<Player>, Integer> {

    /**
     * Finds a player by username.
     *
     * @param username The username of the player.
     * @return The player with the given username.
     */
    Player findByUsername(String username);

    /**
     * Finds a player by email.
     *
     * @param email The email of the player.
     * @return The player with the given email.
     */
    Player findByEmail(Email email);
}
