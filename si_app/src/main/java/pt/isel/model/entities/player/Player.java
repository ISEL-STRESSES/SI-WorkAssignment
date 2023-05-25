package pt.isel.model.entities.player;

import pt.isel.model.entities.chat.Message;
import pt.isel.model.types.Email;

import java.util.List;
import java.util.Set;

public interface Player {
    Integer getId();
    String getUsername();
    Email getEmail();
    String getState();
    String getRegionName();
    PlayerStats getStats();
    Set<Message> getMessages();

    void setUsername(String userName);
    void setEmail(Email email);
    void setState(String state);
    void setRegionName(String regionName);
    void setStats(PlayerStats stats);
    void setMessages(Set<Message> messages);
}
