package pt.isel.model.associacions;

import jakarta.persistence.*;
import pt.isel.model.entities.game.badge.Cracha;
import pt.isel.model.entities.player.Jogador;

/**
 * This class represents a player that has won a badge.
 */
@Entity
@Table(name = "ganha", schema = "public")
public class Ganha {
    @EmbeddedId
    private GanhaId id;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nome_cracha", referencedColumnName = "nome", nullable = false)
    })
    private Cracha cracha;

    /**
     * Getter function for the player that won the badge
     * @return the player that won the badge
     */
    public GanhaId getId() {
        return id;
    }

    /**
     * Setter function for the player that won the badge
     * @param id the player that won the badge
     */
    public void setId(GanhaId id) {
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
    public Cracha getCracha() {
        return cracha;
    }

    /**
     * Setter function for the badge that was won
     * @param cracha the badge that was won
     */
    public void setCracha(Cracha cracha) {
        this.cracha = cracha;
    }

}