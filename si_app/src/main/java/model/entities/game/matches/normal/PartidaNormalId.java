package model.entities.game.matches.normal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import model.types.Alphanumeric;
import utils.Pair;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link PartidaNormal} entity.
 */
@Embeddable
public class PartidaNormalId implements Serializable {
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null", insertable = false, updatable = false)
    private String gameId;

    @Column(name = "nr_partida", nullable = false, insertable = false, updatable = false)
    private Integer matchNr;

    public Alphanumeric getGameId() {
        return new Alphanumeric(gameId);
    }

    public void setGameId(Alphanumeric gameId) {
        this.gameId = gameId.toString();
    }

    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    public Pair<Alphanumeric, Integer> getMatchId() {
        return new Pair<>(new Alphanumeric(gameId), matchNr);
    }

    /**
     * Setter function for the match id
     *
     * @param matchId the match id
     */
    public void setMatchId(Pair<Alphanumeric, Integer> matchId) {
        this.gameId = matchId.first().toString();
        this.matchNr = matchId.second();
    }

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    public Integer getMatchNumber() {
        return matchNr;
    }

    /**
     * Setter function for the match number
     *
     * @param matchNumber the match number
     */
    public void setMatchNumber(Integer matchNumber) {
        this.matchNr = matchNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaNormalId entity = (PartidaNormalId) o;
        return Objects.equals(this.gameId, entity.gameId) &&
                Objects.equals(this.matchNr, entity.matchNr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, matchNr);
    }
}