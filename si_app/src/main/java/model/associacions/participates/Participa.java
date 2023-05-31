package model.associacions.participates;

import jakarta.persistence.*;
import model.entities.chat.Chat;
import model.entities.chat.Conversa;
import model.entities.player.Jogador;
import model.entities.player.Player;

/**
 * This class represents a player that has won a badge.
 */
@Entity
@Table(name = "participa", schema = "public")
public class Participa implements Participates {
    @EmbeddedId
    private ParticipaId id;

    //@MapsId("idPlayer")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false, insertable = false, updatable = false)
    private Jogador idPlayer;

    //@MapsId("idChat")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_conversa", nullable = false, insertable = false, updatable = false)
    private Conversa idChat;

    /**
     * Getter function for the player that won the badge
     *
     * @return the player that won the badge
     */
    @Override
    public ParticipaId getId() {
        return id;
    }

    /**
     * Setter function for the player that won the badge
     *
     * @param id the player that won the badge
     */
    @Override
    public void setId(ParticipaId id) {
        this.id = id;
    }

    /**
     * Getter function for the player that won the badge
     *
     * @return the player that won the badge
     */
    @Override
    public Player getIdPlayer() {
        return idPlayer;
    }

    /**
     * Setter function for the player that won the badge
     *
     * @param idPlayer the player that won the badge
     */
    @Override
    public void setIdPlayer(Player idPlayer) {
        this.idPlayer = (Jogador) idPlayer;
    }

    /**
     * Getter function for the badge that was won
     *
     * @return the badge that was won
     */
    @Override
    public Chat getIdChat() {
        return idChat;
    }

    /**
     * Setter function for the badge that was won
     *
     * @param idChat the badge that was won
     */
    @Override
    public void setIdChat(Chat idChat) {
        this.idChat = (Conversa) idChat;
    }

}