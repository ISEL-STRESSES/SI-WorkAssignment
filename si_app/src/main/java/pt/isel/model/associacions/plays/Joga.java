package pt.isel.model.associacions.plays;

import jakarta.persistence.*;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.Partida;
import pt.isel.model.entities.player.Jogador;
import pt.isel.model.entities.player.Player;

/**
 * This class represents a player that has played a game.
 */
@Entity
@Table(name = "joga", schema = "public")
public class Joga implements Plays {
    @EmbeddedId
    private JogaId id;

    @MapsId("matchNr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr_partida", referencedColumnName = "nr", nullable = false)
    })
    private Partida match;

    @MapsId("idPlayer")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador idPlayer;

    @Column(name = "pontuacao")
    private Integer points;

    /**
     * Getter function for the player that played the game
     *
     * @return the player that played the game
     */
    @Override
    public JogaId getId() {
        return id;
    }

    /**
     * Setter function for the player that played the game
     *
     * @param id the player that played the game
     */
    @Override
    public void setId(JogaId id) {
        this.id = id;
    }

    /**
     * Getter function for the player that played the game
     *
     * @return the player that played the game
     */
    @Override
    public Match getMatch() {
        return match;
    }

    /**
     * Setter function for the player that played the game
     *
     * @param match the player that played the game
     */
    @Override
    public void setMatch(Match match) {
        this.match = (Partida) match;
    }

    /**
     * Getter function for the player that played the game
     *
     * @return the player that played the game
     */
    @Override
    public Player getIdPlayer() {
        return idPlayer;
    }

    /**
     * Setter function for the player that played the game
     *
     * @param idPlayer the player that played the game
     */
    @Override
    public void setIdPlayer(Player idPlayer) {
        this.idPlayer = (Jogador) idPlayer;
    }

    /**
     * Getter function for the player's score
     *
     * @return the player's score
     */
    @Override
    public Integer getPoints() {
        return points;
    }

    /**
     * Setter function for the player's score
     *
     * @param points the player's score
     */
    @Override
    public void setPoints(Integer points) {
        this.points = points;
    }

}