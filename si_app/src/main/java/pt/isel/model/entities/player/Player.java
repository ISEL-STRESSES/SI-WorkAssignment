package pt.isel.model.entities.player;

import pt.isel.model.associacions.earns.Ganha;
import pt.isel.model.associacions.friend.Amigo;
import pt.isel.model.associacions.participates.Participa;
import pt.isel.model.associacions.plays.Joga;
import pt.isel.model.associacions.purchase.Compra;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Email;
import pt.isel.model.types.PlayerState;

import java.util.Set;

/**
 * Represents a player
 */
public interface Player {
    /**
     * Getter function for the player id
     *
     * @return the player id
     */
    Integer getId();

    /**
     * Getter function for the player username
     *
     * @return the player username
     */
    String getUsername();

    /**
     * Setter function for the player username
     *
     * @param userName the player username
     */
    void setUsername(String userName);

    /**
     * Getter function for the player email
     *
     * @return the player email
     */
    Email getEmail();

    /**
     * Setter function for the player email
     *
     * @param email the player email
     */
    void setEmail(Email email);

    /**
     * Getter function for the player state
     *
     * @return the player state
     */
    PlayerState getState();

    /**
     * Setter function for the player state
     *
     * @param state the player state
     */
    void setState(PlayerState state);

    /**
     * Getter function for the player region
     *
     * @return the player region
     */
    Region getRegion();

    /**
     * Setter function for the player region
     *
     * @param region the player region
     */
    void setRegion(Region region);

    /**
     * Getter function for the player stats
     *
     * @return the player stats
     */
    PlayerStats getStats();

    /**
     * Setter function for the player stats
     *
     * @param stats the player stats
     */
    void setStats(PlayerStats stats);

    /**
     * Getter function for the player messages
     *
     * @return the player messages
     */
    Set<Message> getMessages();

    /**
     * Setter function for the player messages
     *
     * @param messages the player messages
     */
    void setMessages(Set<Message> messages);

    /**
     * Getter function for the player friends
     *
     * @return the player friends
     */
    Set<Amigo> getFriends();

    /**
     * Setter function for the player friends
     *
     * @param friends the player friends
     */
    void setFriends(Set<Amigo> friends);

    /**
     * Getter function for the player badges
     *
     * @return the player badges
     */
    Set<Ganha> getBadges();

    /**
     * Setter function for the player badges
     *
     * @param badges the player badges
     */
    void setBadges(Set<Ganha> badges);

    /**
     * Getter function for the player chats
     *
     * @return the player chats
     */
    Set<Participa> getChats();

    /**
     * Setter function for the player chats
     *
     * @param chats the player chats
     */
    void setChats(Set<Participa> chats);

    /**
     * Getter function for the player purchases
     *
     * @return the player purchases
     */
    Set<Compra> getPurchases();

    /**
     * Setter function for the player purchases
     *
     * @param purchases the player purchases
     */
    void setPurchases(Set<Compra> purchases);

    /**
     * Getter function for the player matches
     *
     * @return the player matches
     */
    Set<Joga> getPlayMatches();

    /**
     * Setter function for the player matches
     *
     * @param playMatches the player matches
     */
    void setPlayMatches(Set<Joga> playMatches);

    /**
     * Getter function for the player matches
     *
     * @return the player matches
     */
    boolean addMessage(Message message);

    /**
     * Getter function for the player matches
     *
     * @return the player matches
     */
    boolean removeMessage(Message message);
}
