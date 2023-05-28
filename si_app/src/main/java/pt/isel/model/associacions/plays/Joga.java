package pt.isel.model.associacions;

import jakarta.persistence.*;
import pt.isel.model.entities.game.matches.Partida;
import pt.isel.model.entities.player.Jogador;

/**
 * This class represents a player that has played a game.
 */
@Entity
@Table(name = "joga", schema = "public")
public class Joga {
    @EmbeddedId
    private JogaId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr_partida", referencedColumnName = "nr", nullable = false)
    })
    private Partida partida;

    @MapsId("idJogador")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idJogador;

    @Column(name = "pontuacao")
    private Integer pontuacao;

    /**
     * Getter function for the player that played the game
     * @return the player that played the game
     */
    public JogaId getId() {
        return id;
    }

    /**
     * Setter function for the player that played the game
     * @param id the player that played the game
     */
    public void setId(JogaId id) {
        this.id = id;
    }

    /**
     * Getter function for the player that played the game
     * @return the player that played the game
     */
    public Partida getPartida() {
        return partida;
    }

    /**
     * Setter function for the player that played the game
     * @param partida the player that played the game
     */
    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    /**
     * Getter function for the player that played the game
     * @return the player that played the game
     */
    public Jogador getIdJogador() {
        return idJogador;
    }

    /**
     * Setter function for the player that played the game
     * @param idJogador the player that played the game
     */
    public void setIdJogador(Jogador idJogador) {
        this.idJogador = idJogador;
    }

    /**
     * Getter function for the player's score
     * @return the player's score
     */
    public Integer getPontuacao() {
        return pontuacao;
    }

    /**
     * Setter function for the player's score
     * @param pontuacao the player's score
     */
    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }
}