package pt.isel.model.associacions;

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
    private Integer idJogador1;

    @Column(name = "id_jogador2", nullable = false)
    private Integer idJogador2;

    /**
     * Getter function for the first player id
     * @return the first player id
     */
    public Integer getIdJogador1() {
        return idJogador1;
    }

    /**
     * Setter function for the first player id
     * @param idJogador1 the first player id
     */
    public void setIdJogador1(Integer idJogador1) {
        this.idJogador1 = idJogador1;
    }

    /**
     * Getter function for the second player id
     * @return the second player id
     */
    public Integer getIdJogador2() {
        return idJogador2;
    }

    /**
     * Setter function for the second player id
     * @param idJogador2 the second player id
     */
    public void setIdJogador2(Integer idJogador2) {
        this.idJogador2 = idJogador2;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmigoId entity = (AmigoId) o;
        return Objects.equals(this.idJogador1, entity.idJogador1) &&
                Objects.equals(this.idJogador2, entity.idJogador2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogador1, idJogador2);
    }

}