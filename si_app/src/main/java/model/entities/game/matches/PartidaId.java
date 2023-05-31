package model.entities.game.matches;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Partida} entity.
 */
@Embeddable
public class PartidaId implements Serializable {
    @Column(name = "nr", nullable = false, insertable = false, updatable = false)
    private Integer nr;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null", insertable = false, updatable = false)
    private String gameId;

    public PartidaId() {
    }

    private PartidaId(Integer nr, Alphanumeric gameId) {
        this.nr = nr;
        this.gameId = gameId.toString();
    }

    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    PartidaId getId() {
        return new PartidaId(nr, new Alphanumeric(gameId));
    }

    /**
     * Setter function for the match id
     *
     * @param nr     the match id
     * @param gameId the game id
     */
    public void setId(Integer nr, Alphanumeric gameId) {
        this.gameId = gameId.toString();
        this.nr = nr;
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    public Alphanumeric getGameId() {
        return new Alphanumeric(gameId);
    }

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    public void setGameId(Alphanumeric gameId) {
        this.gameId = gameId.toString();
    }

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    public Integer getMatchNumber() {
        return nr;
    }

    /**
     * Setter function for the match number
     *
     * @param matchNumber the match number
     */
    public void setMatchNumber(Integer matchNumber) {
        this.nr = matchNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaId entity = (PartidaId) o;
        return Objects.equals(this.nr, entity.nr) &&
                Objects.equals(this.gameId, entity.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nr, gameId);
    }
}