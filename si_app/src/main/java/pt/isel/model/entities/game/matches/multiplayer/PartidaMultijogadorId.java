package pt.isel.model.entities.game.matches.multiplayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;
import pt.isel.utils.Pair;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link PartidaMultijogador} entity.
 */
@Embeddable
public class PartidaMultijogadorId implements Serializable {
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idJogo;

    @Column(name = "nr_partida", nullable = false)
    private Integer nrPartida;

    /**
     * Getter function for the match id
     * @return the match id
     */
    public Pair<Alphanumeric, Integer> getMatchId() {
        return new Pair<>(new Alphanumeric(idJogo), nrPartida);
    }

    /**
     * Getter function for the game id
     * @return the game id
     */
    public Alphanumeric getGameId() {
        return new Alphanumeric(idJogo);
    }

    /**
     * Getter function for the match number
     * @return the match number
     */
    public Integer getMatchNumber() {
        return nrPartida;
    }

    /**
     * Setter function for the match id
     * @param matchId the match id
     */
    public void setMatchId(Pair<Alphanumeric, Integer> matchId) {
        this.idJogo = matchId.first().toString();
        this.nrPartida = matchId.second();
    }

    /**
     * Setter function for the game id
     * @param gameId the game id
     */
    public void setGameId(Alphanumeric gameId) {
        this.idJogo = gameId.toString();
    }

    /**
     * Setter function for the match number
     * @param matchNumber the match number
     */
    public void setMatchNumber(Integer matchNumber) {
        this.nrPartida = matchNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaMultijogadorId entity = (PartidaMultijogadorId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.nrPartida, entity.nrPartida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, nrPartida);
    }
}