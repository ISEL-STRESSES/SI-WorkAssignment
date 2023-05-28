package pt.isel.model.associacions.participates;

import pt.isel.model.entities.chat.Chat;
import pt.isel.model.entities.player.Player;

/**
 * Represents a player belonging to a chat.
 */
public interface Participates {
    /**
     * Getter function for the player that won the badge
     *
     * @return the player that won the badge
     */
    ParticipaId getId();

    /**
     * Setter function for the player that won the badge
     *
     * @param id the player that won the badge
     */
    void setId(ParticipaId id);

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
    Chat getIdChat();

    /**
     * Setter function for the badge that was won
     *
     * @param idChat the badge that was won
     */
    void setIdChat(Chat idChat);
}
