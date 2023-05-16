package pt.isel.model.associacions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class GanhaId implements Serializable {
    private static final long serialVersionUID = -5131611758607584683L;
    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private Object idJogo;

    @Column(name = "nome_cracha", nullable = false, length = 50)
    private String nomeCracha;

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

    public String getNomeCracha() {
        return nomeCracha;
    }

    public void setNomeCracha(String nomeCracha) {
        this.nomeCracha = nomeCracha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GanhaId entity = (GanhaId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.idJogador, entity.idJogador) &&
                Objects.equals(this.nomeCracha, entity.nomeCracha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, idJogador, nomeCracha);
    }

}