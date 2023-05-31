package model.entities.game.matches.multiplayer;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import model.types.Alphanumeric;
import utils.Pair;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link PartidaMultijogador} entity.
 */
@Embeddable
public class PartidaMultijogadorId implements Serializable {
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null", insertable = false, updatable = false)
    private String idGame;

    @Column(name = "nr_partida", nullable = false, insertable = false, updatable = false)
    private Integer nrMatch;

    public String getIdGame() {
        return idGame;
    }

    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    public Pair<Alphanumeric, Integer> getMatchId() {
        return new Pair<>(new Alphanumeric(idGame), nrMatch);
    }

    /**
     * Setter function for the match id
     *
     * @param matchId the match id
     */
    public void setMatchId(Pair<Alphanumeric, Integer> matchId) {
        this.idGame = matchId.first().toString();
        this.nrMatch = matchId.second();
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    public Alphanumeric getGameId() {
        return new Alphanumeric(idGame);
    }

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    public void setGameId(Alphanumeric gameId) {
        this.idGame = gameId.toString();
    }

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    public Integer getMatchNumber() {
        return nrMatch;
    }

    /**
     * Setter function for the match number
     *
     * @param matchNumber the match number
     */
    public void setMatchNumber(Integer matchNumber) {
        this.nrMatch = matchNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaMultijogadorId entity = (PartidaMultijogadorId) o;
        return Objects.equals(this.idGame, entity.idGame) &&
                Objects.equals(this.nrMatch, entity.nrMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGame, nrMatch);
    }
}