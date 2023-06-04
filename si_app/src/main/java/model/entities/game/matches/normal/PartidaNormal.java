package model.entities.game.matches.normal;

import jakarta.persistence.*;
import model.entities.game.matches.Match;
import model.entities.game.matches.Partida;
import model.types.Alphanumeric;

/**
 * Class that represents a normal match
 * See {@link Match}
 */
@Entity
@NamedQuery(name = "Partida_Normal.findByKey", query = "SELECT p FROM PartidaNormal p WHERE p.id = :key")
@NamedQuery(name = "Partida_Normal.findAll", query = "SELECT p FROM PartidaNormal p")
@Table(name = "partida_normal", schema = "public")
public class PartidaNormal implements NormalMatch {
    @EmbeddedId
    private PartidaNormalId id;

    @MapsId("matchId")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr", referencedColumnName = "nr", nullable = false)
    })
    private Partida match;

    @Column(name = "dificuldade", nullable = false)
    private Integer difficulty;


    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    @Override
    public PartidaNormalId getId() {
        return this.id;
    }

    /**
     * Setter function for the match id
     *
     * @param id the match id
     */
    @Override
    public void setId(PartidaNormalId id) {
        this.id = id;
    }

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    @Override
    public Integer getMatchNumber() {
        return this.id.getMatchId().getMatchNumber();
    }


    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return this.id.getMatchId().getGameId();
    }

    /**
     * Getter function for the match difficulty
     *
     * @return the game status
     */
    @Override
    public Integer getMatchDifficulty() {
        return this.difficulty;
    }

    /**
     * Setter function for the game status
     *
     * @param difficulty the game status
     */
    @Override
    public void setMatchDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Getter function for the match
     *
     * @return the match
     */
    @Override
    public Match getMatch() {
        return this.match;
    }

    /**
     * Setter function for the match
     *
     * @param match the match
     */
    @Override
    public void setMatch(Match match) {
        this.match = (Partida) match;
    }
}