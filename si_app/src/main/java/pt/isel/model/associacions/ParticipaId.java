package pt.isel.model.associacions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ParticipaId implements Serializable {
    private static final long serialVersionUID = 5973249867039919296L;
    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    @Column(name = "id_conversa", nullable = false)
    private Integer idConversa;

    public Integer getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    public Integer getIdConversa() {
        return idConversa;
    }

    public void setIdConversa(Integer idConversa) {
        this.idConversa = idConversa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipaId entity = (ParticipaId) o;
        return Objects.equals(this.idJogador, entity.idJogador) &&
                Objects.equals(this.idConversa, entity.idConversa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogador, idConversa);
    }

}