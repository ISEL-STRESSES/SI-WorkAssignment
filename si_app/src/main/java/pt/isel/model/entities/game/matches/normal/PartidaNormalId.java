package pt.isel.model.entities.game.matches.normal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.entities.game.matches.MatchId;
import pt.isel.model.types.Alphanumeric;
import pt.isel.utils.Pair;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartidaNormalId implements Serializable, MatchId {
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idJogo;

    @Column(name = "nr_partida", nullable = false)
    private Integer nrPartida;

    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    @Override
    public Pair<Alphanumeric, Integer> getMatchId() {
        return new Pair<>(new Alphanumeric(idJogo), nrPartida);
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return new Alphanumeric(idJogo);
    }

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    @Override
    public Integer getMatchNumber() {
        return nrPartida;
    }

    /**
     * Setter function for the match id
     *
     * @param matchId the match id
     */
    @Override
    public void setMatchId(Pair<Alphanumeric, Integer> matchId) {
        this.idJogo = matchId.first().toString();
        this.nrPartida = matchId.second();
    }

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    @Override
    public void setGameId(Alphanumeric gameId) {
        this.idJogo = gameId.toString();
    }

    /**
     * Setter function for the match number
     *
     * @param matchNumber the match number
     */
    @Override
    public void setMatchNumber(Integer matchNumber) {
        this.nrPartida = matchNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaNormalId entity = (PartidaNormalId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.nrPartida, entity.nrPartida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, nrPartida);
    }
}