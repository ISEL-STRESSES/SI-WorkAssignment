package pt.isel.model.associacions.plays;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JogaId implements Serializable {
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idGame;

    @Column(name = "nr_partida", nullable = false)
    private Integer matchNr;

    @Column(name = "id_jogador", nullable = false)
    private Integer idPlayer;

    public Alphanumeric getIdGame() {
        return new Alphanumeric(idGame);
    }

    public void setIdGame(Alphanumeric idGame) {
        this.idGame = idGame.toString();
    }

    public Integer getMatchNr() {
        return matchNr;
    }

    public void setMatchNr(Integer matchNr) {
        this.matchNr = matchNr;
    }

    public Integer getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Integer idPlayer) {
        this.idPlayer = idPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JogaId entity = (JogaId) o;
        return Objects.equals(this.idGame, entity.idGame) &&
                Objects.equals(this.idPlayer, entity.idPlayer) &&
                Objects.equals(this.matchNr, entity.matchNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGame, idPlayer, matchNr);
    }

}