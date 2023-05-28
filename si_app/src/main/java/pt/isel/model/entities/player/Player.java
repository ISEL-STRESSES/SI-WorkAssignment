package pt.isel.model.entities.player;

import pt.isel.model.entities.chat.Message;
import pt.isel.model.types.Email;

import java.util.List;
import java.util.Set;

/**
 * Represents a player
 */
public interface Player {

    /**
     * Getter function for the player id
     * @return the player id
     */
    Integer getId();

    /**
     * Getter function for the player username
     * @return the player username
     */
    String getUsername();

    /**
     * Getter function for the player email
     * @return the player email
     */
    Email getEmail();

    /**
     * Getter function for the player state
     * @return the player state
     */
    String getState();

    /**
     * Getter function for the player region name
     * @return the player region name
     */
    String getRegionName();

    /**
     * Getter function for the player stats
     * @return the player stats
     */
    PlayerStats getStats();

    /**
     * Getter function for the player messages
     * @return the player messages
     */
    Set<Message> getMessages();

    /**
     * Setter function for the player username
     * @param userName the player username
     */
    void setUsername(String userName);

    /**
     * Setter function for the player email
     * @param email the player email
     */
    void setEmail(Email email);

    /**
     * Setter function for the player state
     * @param state the player state
     */
    void setState(String state);

    /**
     * Setter function for the player region name
     * @param regionName the player region name
     */
    void setRegionName(String regionName);

    /**
     * Setter function for the player stats
     * @param stats the player stats
     */
    void setStats(PlayerStats stats);

    /**
     * Setter function for the player messages
     * @param messages the player messages
     */
    void setMessages(Set<Message> messages);
}
