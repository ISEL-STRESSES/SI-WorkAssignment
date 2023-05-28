package pt.isel.model.associacions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import pt.isel.model.types.Alphanumeric;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Ganha} entity.
 */
@Embeddable
public class GanhaId implements Serializable {
    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String idJogo;

    @Column(name = "nome_cracha", nullable = false, length = 50)
    private String nomeCracha;

    /**
     * Getter function for the player id
     * @return the player id
     */
    public Integer getIdJogador() {
        return idJogador;
    }

    /**
     * Setter function for the player id
     * @param idJogador the player id
     */
    public void setIdJogador(Integer idJogador) {
        this.idJogador = idJogador;
    }

    /**
     * Getter function for the game id
     * @return the game id
     */
    public Alphanumeric getIdJogo() {
        return new Alphanumeric(idJogo);
    }

    /**
     * Setter function for the game id
     * @param idJogo the game id
     */
    public void setIdJogo(Alphanumeric idJogo) {
        this.idJogo = idJogo.toString();
    }

    /**
     * Getter function for the badge name
     * @return the badge name
     */
    public String getNomeCracha() {
        return nomeCracha;
    }

    /**
     * Setter function for the badge name
     * @param nomeCracha the badge name
     */
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