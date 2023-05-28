package pt.isel.model.associacions.purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompraId implements Serializable {
    @Column(name = "id_jogador", nullable = false)
    private Integer idPlayer;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idGame;

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Alphanumeric getIdGame() {
        return new Alphanumeric(idGame);
    }

    public void setIdGame(Alphanumeric idGame) {
        this.idGame = idGame.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompraId entity = (CompraId) o;
        return Objects.equals(this.idGame, entity.idGame) &&
                Objects.equals(this.idPlayer, entity.idPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGame, idPlayer);
    }
}