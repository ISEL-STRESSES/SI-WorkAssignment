package pt.isel.model.entities.chat;

import pt.isel.model.entities.player.Player;

import java.time.LocalDate;

/**
 * Represents a message entity
 */
public interface Message {
    /**
     * Getter function for the message id
     *
     * @return the message id
     */
    MensagemId getId();

    /**
     * Setter function for the message id
     *
     * @param id the message id
     */
    void setId(MensagemId id);

    /**
     * Getter function for the message number
     *
     * @return the message number
     */
    Integer getMessageNumber();

    /**
     * Setter function for the message number
     *
     * @param messageNumber the message number
     */
    void setMessageNumber(Integer messageNumber);

    /**
     * Getter function for the chat id
     *
     * @return the chat id
     */
    Integer getChatId();

    /**
     * Setter function for the chat id
     *
     * @param chatId the chat id
     */
    void setChatId(Integer chatId);

    /**
     * Getter function for the chat
     *
     * @return the chat
     */
    Chat getChat();

    /**
     * Setter function for the chat
     *
     * @param chat the chat
     */
    void setChat(Chat chat);

    /**
     * Getter function for the user id
     *
     * @return the user id
     */
    Integer getUserId();

    /**
     * Setter function for the user id
     *
     * @param userId the user id
     */
    void setUserId(Integer userId);

    /**
     * Getter function for the user
     *
     * @return the user
     */
    Player getPlayer();

    /**
     * Setter function for the user
     *
     * @param player the user
     */
    void setPlayer(Player player);

    /**
     * Getter function for the message
     *
     * @return the message
     */
    String getText();

    /**
     * Setter function for the message
     *
     * @param text the message
     */
    void setText(String text);

    /**
     * Getter function for the message date
     *
     * @return the message date
     */
    LocalDate getDate();

    /**
     * Setter function for the message date
     *
     * @param date the message date
     */
    void setDate(LocalDate date);
}
