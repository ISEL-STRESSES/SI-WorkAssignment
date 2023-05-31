package model.associacions.participates;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Participa} entity.
 */
@Embeddable
public class ParticipaId implements Serializable {
    @Column(name = "id_jogador", nullable = false)
    private Integer idPlayer;

    @Column(name = "id_conversa", nullable = false)
    private Integer idChat;

    /**
     * Getter function for the player id
     *
     * @return the player id
     */
    public Integer getIdPlayer() {
        return idPlayer;
    }

    /**
     * Setter function for the player id
     *
     * @param idPlayer the player id
     */
    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    /**
     * Getter function for the conversation id
     *
     * @return the conversation id
     */
    public Integer getIdChat() {
        return idChat;
    }

    /**
     * Setter function for the conversation id
     *
     * @param idChat the conversation id
     */
    public void setIdChat(Integer idChat) {
        this.idChat = idChat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipaId entity = (ParticipaId) o;
        return Objects.equals(this.idPlayer, entity.idPlayer) &&
                Objects.equals(this.idChat, entity.idChat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPlayer, idChat);
    }

}