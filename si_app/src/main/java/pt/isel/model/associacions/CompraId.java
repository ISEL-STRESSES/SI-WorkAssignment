package pt.isel.model.associacions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompraId implements Serializable {
    private static final long serialVersionUID = 4105554825148766269L;
    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private Object idJogo;

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
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
        CompraId entity = (CompraId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.idJogador, entity.idJogador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, idJogador);
    }

}