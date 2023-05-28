package pt.isel.model.associacions.participates;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ParticipaId implements Serializable {
    @Column(name = "id_jogador", nullable = false)
    private Integer idPlayer;

    @Column(name = "id_conversa", nullable = false)
    private Integer idChat;

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Integer getIdChat() {
        return idChat;
    }

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