package pt.isel.model.entities.game.matches;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PartidaId implements Serializable {
    @Column(name = "nr", nullable = false)
    private Integer nr;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idJogo;

    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public Alphanumeric getIdJogo() {
        return new Alphanumeric(idJogo);
    }

    public void setIdJogo(Alphanumeric idJogo) {
        this.idJogo = idJogo.toString();
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