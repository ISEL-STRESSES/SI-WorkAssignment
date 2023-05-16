package pt.isel.model.associacions;

import jakarta.persistence.*;
import pt.isel.model.entities.chat.Conversa;
import pt.isel.model.entities.player.Jogador;

@Entity
@Table(name = "participa", schema = "public")
public class Participa {
    @EmbeddedId
    private ParticipaId id;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @MapsId("idConversa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_conversa", nullable = false)
    private Conversa idConversa;

    public ParticipaId getId() {
        return id;
    }

    public void setId(ParticipaId id) {
        this.id = id;
    }

    public Jogador getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    public Conversa getIdConversa() {
        return idConversa;
    }

    public void setIdConversa(Conversa idConversa) {
        this.idConversa = idConversa;
    }

}