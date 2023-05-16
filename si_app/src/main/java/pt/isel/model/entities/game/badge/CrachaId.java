package pt.isel.model.entities.game.badge;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CrachaId implements Serializable {
    private static final long serialVersionUID = 7787360520380474589L;
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private Object idJogo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
        CrachaId entity = (CrachaId) o;
        return Objects.equals(this.idJogo, entity.idJogo) &&
                Objects.equals(this.nome, entity.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idJogo, nome);
    }

}