package pt.isel.model.entities.chat;

import pt.isel.model.entities.player.Player;

import java.time.LocalDate;

/**
 * Represents a message entity
 */
public interface Message {
    /**
     * Getter function for the message id
     * @return the message id
     */
    MensagemId getId();

    /**
     * Getter function for the message number
     * @return the message number
     */
    Integer getMessageNumber();

    /**
     * Getter function for the chat id
     * @return the chat id
     */
    Integer getChatId();

    /**
     * Getter function for the chat
     * @return the chat
     */
    Chat getChat();

    /**
     * Getter function for the user id
     * @return the user id
     */
    Integer getUserId();

    /**
     * Getter function for the user
     * @return the user
     */
    Player getPlayer();

    /**
     * Getter function for the message
     * @return the message
     */
    String getText();

    /**
     * Getter function for the message date
     * @return the message date
     */
    LocalDate getDate();

    /**
     * Setter function for the message id
     * @param id the message id
     */
    void setId(MensagemId id);

    /**
     * Setter function for the message number
     * @param messageNumber the message number
     */
    void setMessageNumber(Integer messageNumber);

    /**
     * Setter function for the chat id
     * @param chatId the chat id
     */
    void setChatId(Integer chatId);

    /**
     * Setter function for the chat
     * @param chat the chat
     */
    void setChat(Chat chat);

    /**
     * Setter function for the user id
     * @param userId the user id
     */
    void setUserId(Integer userId);

    /**
     * Setter function for the user
     * @param player the user
     */
    void setPlayer(Player player);

    /**
     * Setter function for the message
     * @param text the message
     */
    void setText(String text);

    /**
     * Setter function for the message date
     * @param date the message date
     */
    void setDate(LocalDate date);
}
