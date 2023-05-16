package pt.isel.model.associacions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JogaId implements Serializable {
    private static final long serialVersionUID = -6328505084267612261L;
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private Object idJogo;

    @Column(name = "nr_partida", nullable = false)
    private Integer nrPartida;

    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

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

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JogaId entity = (JogaId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.idJogador, entity.idJogador) &&
                Objects.equals(this.nrPartida, entity.nrPartida);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, idJogador, nrPartida);
    }

}