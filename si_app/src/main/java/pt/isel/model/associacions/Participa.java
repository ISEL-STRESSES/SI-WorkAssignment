package pt.isel.model.associacions;

import jakarta.persistence.*;
import pt.isel.model.entities.chat.Conversa;
import pt.isel.model.entities.player.Jogador;

/**
 * This class represents a player that has won a badge.
 */
@Entity
@Table(name = "participa", schema = "public")
public class Participa {
    @EmbeddedId
    private ParticipaId id;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @MapsId("idConversa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_conversa", nullable = false)
    private Conversa idConversa;

    /**
     * Getter function for the player that won the badge
     * @return the player that won the badge
     */
    public ParticipaId getId() {
        return id;
    }

    /**
     * Setter function for the player that won the badge
     * @param id the player that won the badge
     */
    public void setId(ParticipaId id) {
        this.id = id;
    }

    /**
     * Getter function for the player that won the badge
     * @return the player that won the badge
     */
    public Jogador getIdJogador() {
        return idJogador;
    }

    /**
     * Setter function for the player that won the badge
     * @param idJogador the player that won the badge
     */
    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    /**
     * Getter function for the badge that was won
     * @return the badge that was won
     */
    public Conversa getIdConversa() {
        return idConversa;
    }

    /**
     * Setter function for the badge that was won
     * @param idConversa the badge that was won
     */
    public void setIdConversa(Conversa idConversa) {
        this.idConversa = idConversa;
    }

}