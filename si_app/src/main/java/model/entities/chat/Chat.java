package model.entities.chat;

import model.associacions.participates.Participa;

import java.util.Set;

/**
 * Represents a chat entity
 */
public interface Chat {
    /**
     * Getter function for the chat id
     *
     * @return the chat id
     */
    Integer getId();

    /**
     * Setter function for the chat id
     *
     * @param id the chat id
     */
    void setId(Integer id);

    /**
     * Getter function for the chat name
     *
     * @return the chat name
     */
    String getName();

    /**
     * Setter function for the chat name
     *
     * @param name the chat name
     */
    void setName(String name);

    /**
     * Getter function for the chat messages
     *
     * @return the chat messages
     */
    Set<Message> getMessages();

    /**
     * Setter function for the chat messages
     *
     * @param messages the chat messages
     */
    void setMessages(Set<Message> messages);

    /**
     * Getter function for the chat players
     *
     * @return the chat players
     */
    Set<Participa> getPlayers();

    /**
     * Setter function for the chat players
     *
     * @param players the chat players
     */
    void setPlayers(Set<Participa> players);
}
