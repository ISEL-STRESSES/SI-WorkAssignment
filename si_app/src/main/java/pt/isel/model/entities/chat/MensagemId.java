package pt.isel.model.entities.chat;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the primary key of the {@link Mensagem} entity.
 */
@Embeddable
public class MensagemId implements Serializable {
    @Column(name = "nr_ordem", nullable = false)
    private Integer nrOrdem;

    @Column(name = "id_conversa", nullable = false)
    private Integer idConversa;

    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    /**
     * Getter function for the message number
     *
     * @return the message number
     */
    public Integer getNrOrdem() {
        return nrOrdem;
    }

    /**
     * Setter function for the message number
     *
     * @param nrOrdem the message number
     */
    public void setNrOrdem(Integer nrOrdem) {
        this.nrOrdem = nrOrdem;
    }

    /**
     * Getter function for the chat id
     *
     * @return the chat id
     */
    public Integer getIdConversa() {
        return idConversa;
    }

    /**
     * Setter function for the chat id
     *
     * @param idConversa the chat id
     */
    public void setIdConversa(Integer idConversa) {
        this.idConversa = idConversa;
    }

    /**
     * Getter function for the player id
     *
     * @return the player id
     */
    public Integer getIdJogador() {
        return this.idJogador;
    }

    /**
     * Setter function for the player id
     *
     * @param idJogador the player id
     */
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