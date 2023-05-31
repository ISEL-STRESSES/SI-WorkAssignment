package model.associacions.purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Compra} entity.
 */
@Embeddable
public class CompraId implements Serializable {
    @Column(name = "id_jogador", nullable = false)
    private Integer idPlayer;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idGame;

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