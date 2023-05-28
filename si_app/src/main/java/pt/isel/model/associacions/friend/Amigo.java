package pt.isel.model.associacions.friend;

import jakarta.persistence.*;
import pt.isel.model.entities.player.Jogador;

@Entity
@Table(name = "amigo", schema = "public")
public class Amigo {
    @EmbeddedId
    private AmigoId id;

    @MapsId("player1Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Jogador idPlayer1;

    @MapsId("player2Id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Jogador idPlayer2;

    public AmigoId getId() {
        return id;
    }

    public void setId(AmigoId id) {
        this.id = id;
    }

    public Jogador getIdPlayer1() {
        return idPlayer1;
    }

    public void setIdPlayer1(Jogador idPlayer1) {
        this.idPlayer1 = idPlayer1;
    }

    public Jogador getIdPlayer2() {
        return idPlayer2;
    }

    public void setIdPlayer2(Jogador idPlayer2) {
        this.idPlayer2 = idPlayer2;
    }

}