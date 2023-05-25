package pt.isel.model.entities.chat;

import jakarta.persistence.*;
import pt.isel.model.entities.player.Jogador;
import pt.isel.model.entities.player.Player;

import java.time.LocalDate;

@Entity
@NamedQuery(name= "Mensagem.findByKey", query = "SELECT m FROM Mensagem m WHERE m.id = :key")
@NamedQuery(name= "Mensagem.findAll", query = "SELECT m FROM Mensagem m")
@Table(name = "mensagem", schema = "public")
public class Mensagem implements Message{
    @EmbeddedId
    private MensagemId id;

    @MapsId("idConversa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_conversa", nullable = false)
    private Conversa chatId;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador playerId;

    @Lob
    @Column(name = "texto", nullable = false)
    private String text;

    @Column(name = "data", nullable = false)
    private LocalDate date;


    /**
     * Getter function for the message id
     *
     * @return the message id
     */
    @Override
    public MensagemId getId() {
        return this.id;
    }

    /**
     * Getter function for the message number
     *
     * @return the message number
     */
    @Override
    public Integer getMessageNumber() {
        return this.id.getNrOrdem();
    }

    /**
     * Getter function for the chat id
     *
     * @return the chat id
     */
    @Override
    public Integer getChatId() {
        return this.id.getIdConversa();
    }

    /**
     * Getter function for the chat
     *
     * @return the chat
     */
    @Override
    public Chat getChat() {
        return this.chatId;
    }

    /**
     * Getter function for the user id
     *
     * @return the user id
     */
    @Override
    public Integer getUserId() {
        return this.id.getIdJogador();
    }

    /**
     * Getter function for the user
     *
     * @return the user
     */
    @Override
    public Player getPlayer() {
        return this.playerId;
    }

    /**
     * Getter function for the message
     *
     * @return the message
     */
    @Override
    public String getText() {
        return this.text;
    }

    /**
     * Getter function for the message date
     *
     * @return the message date
     */
    @Override
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Setter function for the message id
     *
     * @param id the message id
     */
    @Override
    public void setId(MensagemId id) {
        this.id = id;
    }

    /**
     * Setter function for the message number
     *
     * @param messageNumber the message number
     */
    @Override
    public void setMessageNumber(Integer messageNumber) {
        this.id.setNrOrdem(messageNumber);
    }

    /**
     * Setter function for the chat id
     *
     * @param chatId the chat id
     */
    @Override
    public void setChatId(Integer chatId) {
        this.id.setIdConversa(chatId);
    }

    /**
     * Setter function for the chat
     *
     * @param chat the chat
     */
    @Override
    public void setChat(Chat chat) {
        this.chatId = (Conversa) chat;
    }

    /**
     * Setter function for the user id
     *
     * @param userId the user id
     */
    @Override
    public void setUserId(Integer userId) {
        this.id.setIdJogador(userId);
    }

    /**
     * Setter function for the user
     *
     * @param player the user
     */
    @Override
    public void setPlayer(Player player) {
        this.playerId = (Jogador) player;
    }

    /**
     * Setter function for the message
     *
     * @param text the message
     */
    @Override
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Setter function for the message date
     *
     * @param date the message date
     */
    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Default empty constructor
     */
    public Mensagem() {
    }

    /**
     * Constructor with all attributes except the id
     *
     * @param id the message id
     * @param conversa the chat
     * @param jogador the user
     * @param text the message
     * @param date the message date
     */
    public Mensagem(MensagemId id, Conversa conversa, Jogador jogador, String text, LocalDate date) {
        setId(id);
        setChat(conversa);
        setPlayer(jogador);
        setText(text);
        setDate(date);
    }

    /**
     * Constructor with all attributes except the id
     *
     * @param conversa the chat
     * @param jogador the user
     * @param text the message
     * @param date the message date
     */
    public Mensagem(Conversa conversa, Jogador jogador, String text, LocalDate date) {
        setChat(conversa);
        setPlayer(jogador);
        setText(text);
        setDate(date);
    }
}