package pt.isel.model.entities.game.matches;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartidaNormalId implements Serializable {
    private static final long serialVersionUID = -7360907435864349620L;
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idJogo;

    @Column(name = "nr_partida", nullable = false)
    private Integer nrPartida;

    public Alphanumeric getIdJogo() {
        return new Alphanumeric(idJogo);
    }

    public void setIdJogo(Alphanumeric idJogo) {
        this.idJogo = idJogo.toString();
    }

    public Integer getNrPartida() {
        return nrPartida;
    }

    public void setNrPartida(Integer nrPartida) {
        this.nrPartida = nrPartida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaNormalId entity = (PartidaNormalId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.nrPartida, entity.nrPartida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, nrPartida);
    }

}