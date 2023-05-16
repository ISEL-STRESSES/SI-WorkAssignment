package pt.isel.model.entities.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MensagemId implements Serializable {
    private static final long serialVersionUID = 7625716103423031047L;
    @Column(name = "nr_ordem", nullable = false)
    private Integer nrOrdem;

    @Column(name = "id_conversa", nullable = false)
    private Integer idConversa;

    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    public Integer getNrOrdem() {
        return nrOrdem;
    }

    public void setNrOrdem(Integer nrOrdem) {
        this.nrOrdem = nrOrdem;
    }

    public Integer getIdConversa() {
        return idConversa;
    }

    public void setIdConversa(Integer idConversa) {
        this.idConversa = idConversa;
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
        MensagemId entity = (MensagemId) o;
        return Objects.equals(this.nrOrdem, entity.nrOrdem) &&
                Objects.equals(this.idConversa, entity.idConversa) &&
                Objects.equals(this.idJogador, entity.idJogador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nrOrdem, idConversa, idJogador);
    }

}