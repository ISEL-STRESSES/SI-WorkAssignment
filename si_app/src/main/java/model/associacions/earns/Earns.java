package model.associacions.earns;

import model.entities.game.badge.Badge;
import model.entities.player.Player;

/**
 * Represents a player that has won a badge.
 */
public interface Earns {
    /**
     * Getter function for the player that won the badge
     *
     * @return the player that won the badge
     */
    GanhaId getId();

    /**
     * Setter function for the player that won the badge
     *
     * @param id the player that won the badge
     */
    void setId(GanhaId id);

    /**
     * Getter function for the player that won the badge
     *
     * @return the player that won the badge
     */
    Player getIdPlayer();

    /**
     * Setter function for the player that won the badge
     *
     * @param idPlayer the player that won the badge
     */
    void setIdPlayer(Player idPlayer);

    /**
     * Getter function for the badge that was won
     *
     * @return the badge that was won
     */
    Badge getBadge();

    /**
     * Setter function for the badge that was won
     *
     * @param badge the badge that was won
     */
    void setBadge(Badge badge);
}
