package pt.isel.model.classImp;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartidaId implements Serializable {
    private static final long serialVersionUID = 355099648053410683L;
    @Column(name = "nr", nullable = false)
    private Integer nr;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private Object idJogo;

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public Object getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Object idJogo) {
        this.idJogo = idJogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaId entity = (PartidaId) o;
        return Objects.equals(this.nr, entity.nr) &&
                Objects.equals(this.idJogo, entity.idJogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nr, idJogo);
    }

}