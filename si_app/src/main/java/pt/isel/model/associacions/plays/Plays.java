package pt.isel.model.associacions.plays;

import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.player.Player;

/**
 * Represents a match played by a players.
 */
public interface Plays {

    /**
     * Getter function for the player that played the game
     *
     * @return the player that played the game
     */
    JogaId getId();

    /**
     * Setter function for the player that played the game
     *
     * @param id the player that played the game
     */
    void setId(JogaId id);

    /**
     * Getter function for the player that played the game
     *
     * @return the player that played the game
     */
    Match getMatch();

    /**
     * Setter function for the player that played the game
     *
     * @param match the player that played the game
     */
    void setMatch(Match match);

    /**
     * Getter function for the player that played the game
     *
     * @return the player that played the game
     */
    Player getIdPlayer();

    /**
     * Setter function for the player that played the game
     *
     * @param idPlayer the player that played the game
     */
    void setIdPlayer(Player idPlayer);

    /**
     * Getter function for the player's score
     *
     * @return the player's score
     */
    Integer getPoints();

    /**
     * Setter function for the player's score
     *
     * @param points the player's score
     */
    void setPoints(Integer points);
}
