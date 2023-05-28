package pt.isel.model.associacions.participates;

import jakarta.persistence.*;
import pt.isel.model.entities.chat.Conversa;
import pt.isel.model.entities.player.Jogador;

@Entity
@Table(name = "participa", schema = "public")
public class Participa {
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

    public ParticipaId getId() {
        return id;
    }

    public void setId(ParticipaId id) {
        this.id = id;
    }

    public Jogador getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Jogador idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Conversa getIdChat() {
        return idChat;
    }

    public void setIdChat(Conversa idChat) {
        this.idChat = idChat;
    }

}