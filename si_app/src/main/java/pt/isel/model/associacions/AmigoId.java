package pt.isel.model.associacions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AmigoId implements Serializable {
    @Column(name = "id_jogador1", nullable = false)
    private Integer idJogador1;

    @Column(name = "id_jogador2", nullable = false)
    private Integer idJogador2;

    public Integer getIdJogador1() {
        return idJogador1;
    }

    public void setIdJogador1(Integer idJogador1) {
        this.idJogador1 = idJogador1;
    }

    public Integer getIdJogador2() {
        return idJogador2;
    }

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