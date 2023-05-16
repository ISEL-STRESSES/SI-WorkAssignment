package pt.isel.model.entities.game.matches;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartidaMultijogadorId implements Serializable {
    private static final long serialVersionUID = 1411821698711291498L;
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private Object idJogo;

    @Column(name = "nr_partida", nullable = false)
    private Integer nrPartida;

    public Object getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Object idJogo) {
        this.idJogo = idJogo;
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
        PartidaMultijogadorId entity = (PartidaMultijogadorId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.nrPartida, entity.nrPartida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, nrPartida);
    }

}