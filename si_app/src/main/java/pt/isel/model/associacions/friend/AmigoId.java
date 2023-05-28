package pt.isel.model.associacions.friend;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Amigo} entity.
 */
@Embeddable
public class AmigoId implements Serializable {
    @Column(name = "id_jogador1", nullable = false)
    private Integer player1Id;

    @Column(name = "id_jogador2", nullable = false)
    private Integer player2Id;

    /**
     * Getter function for the first player id
     *
     * @return the first player id
     */
    public Integer getPlayer1Id() {
        return player1Id;
    }

    /**
     * Setter function for the first player id
     *
     * @param player1Id the first player id
     */
    public void setPlayer1Id(Integer player1Id) {
        this.player1Id = player1Id;
    }

    /**
     * Getter function for the second player id
     *
     * @return the second player id
     */
    public Integer getPlayer2Id() {
        return player2Id;
    }

    /**
     * Setter function for the second player id
     *
     * @param player2Id the second player id
     */
    public void setPlayer2Id(Integer player2Id) {
        this.player2Id = player2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmigoId entity = (AmigoId) o;
        return Objects.equals(this.player1Id, entity.player1Id) &&
                Objects.equals(this.player2Id, entity.player2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1Id, player2Id);
    }

}