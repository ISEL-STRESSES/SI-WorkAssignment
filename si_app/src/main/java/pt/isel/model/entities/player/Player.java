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

public interface Player {
    Integer getId();

    String getUsername();

    void setUsername(String userName);

    Email getEmail();

    void setEmail(Email email);

    PlayerState getState();

    void setState(PlayerState state);

    Region getRegion();

    void setRegion(Region region);

    PlayerStats getStats();

    void setStats(PlayerStats stats);

    Set<Message> getMessages();

    void setMessages(Set<Message> messages);

    Set<Amigo> getFriends();

    void setFriends(Set<Amigo> friends);

    Set<Ganha> getBadges();

    void setBadges(Set<Ganha> badges);

    Set<Participa> getChats();

    void setChats(Set<Participa> chats);

    Set<Compra> getPurchases();

    void setPurchases(Set<Compra> purchases);

    Set<Joga> getPlayMatches();

    void setPlayMatches(Set<Joga> playMatches);

    boolean addMessage(Message message);

    boolean removeMessage(Message message);
}
