package pt.isel.model.entities.game.matches;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;
import pt.isel.utils.Pair;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartidaId implements Serializable, MatchId {
    @Column(name = "nr", nullable = false)
    private Integer nr;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idJogo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaId entity = (PartidaId) o;
        return Objects.equals(this.nr, entity.nr) &&
                Objects.equals(this.idJogo, entity.idJogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nr, idJogo);
    }

    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    @Override
    public Pair<Alphanumeric, Integer> getMatchId() {
        return new Pair<>(new Alphanumeric(idJogo), nr);
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
        return nr;
    }

    /**
     * Setter function for the match id
     *
     * @param matchId the match id
     */
    @Override
    public void setMatchId(Pair<Alphanumeric, Integer> matchId) {
        this.idJogo = matchId.first().toString();
        this.nr = matchId.second();
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
        this.nr = matchNumber;
    }
}