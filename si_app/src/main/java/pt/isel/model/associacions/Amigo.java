package pt.isel.model.associacions;

import jakarta.persistence.*;
import pt.isel.model.entities.player.Jogador;

@Entity
@Table(name = "amigo", schema = "public")
public class Amigo {
    @EmbeddedId
    private AmigoId id;

    @MapsId("idJogador1")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador1", nullable = false)
    private Jogador idJogador1;

    @MapsId("idJogador2")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador2", nullable = false)
    private Jogador idJogador2;

    public AmigoId getId() {
        return id;
    }

    public void setId(AmigoId id) {
        this.id = id;
    }

    public Jogador getIdJogador1() {
        return idJogador1;
    }

    public void setIdJogador1(Jogador idJogador1) {
        this.idJogador1 = idJogador1;
    }

    public Jogador getIdJogador2() {
        return idJogador2;
    }

    public void setIdJogador2(Jogador idJogador2) {
        this.idJogador2 = idJogador2;
    }

}