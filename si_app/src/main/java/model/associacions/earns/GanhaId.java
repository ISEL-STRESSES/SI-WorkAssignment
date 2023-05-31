package model.associacions.earns;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Ganha} entity.
 */
@Embeddable
public class GanhaId implements Serializable {
    @Column(name = "id_jogador", nullable = false, insertable = false, updatable = false)
    private Integer idPlayer;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null", insertable = false, updatable = false)
    private String idGame;

    @Column(name = "nome_cracha", nullable = false, length = 50, insertable = false, updatable = false)
    private String badgeName;

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

    /**
     * Getter function for the badge name
     *
     * @return the badge name
     */
    public String getBadgeName() {
        return badgeName;
    }

    /**
     * Setter function for the badge name
     *
     * @param badgeName the badge name
     */
    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GanhaId entity = (GanhaId) o;
        return Objects.equals(this.idGame, entity.idGame) &&
                Objects.equals(this.idPlayer, entity.idPlayer) &&
                Objects.equals(this.badgeName, entity.badgeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idGame, idPlayer, badgeName);
    }

}