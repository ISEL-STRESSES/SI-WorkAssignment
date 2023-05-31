package model.associacions.plays;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Joga} entity.
 */
@Embeddable
public class JogaId implements Serializable {
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null", insertable = false, updatable = false)
    private String idGame;

    @Column(name = "nr_partida", nullable = false, insertable = false, updatable = false)
    private Integer matchNr;

    @Column(name = "id_jogador", nullable = false, insertable = false, updatable = false)
    private Integer idPlayer;

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    public Alphanumeric getIdGame() {
        return new Alphanumeric(idGame);
    }

    /**
     * Setter function for the game id
     *
     * @param idGame the game id
     */
    public void setIdGame(Alphanumeric idGame) {
        this.idGame = idGame.toString();
    }

    /**
     * Getter function for the game number
     *
     * @return the game number
     */
    public Integer getMatchNr() {
        return matchNr;
    }

    /**
     * Setter function for the game number
     *
     * @param matchNr the game number
     */
    public void setMatchNr(Integer matchNr) {
        this.matchNr = matchNr;
    }

    /**
     * Getter function for the player id
     *
     * @return the player id
     */
    public Integer getIdPlayer() {
        return idPlayer;
    }

    /**
     * Setter function for the player id
     *
     * @param idPlayer the player id
     */
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