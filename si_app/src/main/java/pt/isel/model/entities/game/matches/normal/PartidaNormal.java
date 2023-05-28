package pt.isel.model.entities.game.matches.normal;

import jakarta.persistence.*;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.Partida;
import pt.isel.model.types.Alphanumeric;

@Entity
@NamedQuery(name = "PartidaNormal.findByKey", query = "SELECT p FROM PartidaNormal p WHERE p.id = :key")
@NamedQuery(name = "PartidaNormal.findAll", query = "SELECT p FROM PartidaNormal p")
@Table(name = "partida_normal", schema = "public")
public class PartidaNormal implements NormalMatch {
    @EmbeddedId
    private PartidaNormalId id;

    @MapsId("matchNr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr_partida", referencedColumnName = "nr", nullable = false)
    })
    private Partida partida;

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
     * @param normalMatchId the match id
     */
    @Override
    public void setId(PartidaNormalId normalMatchId) {
        this.id = normalMatchId;
    }

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    @Override
    public Integer getMatchNumber() {
        return this.id.getMatchNumber();
    }

    /**
     * Setter function for the match number
     *
     * @param matchNumber the match number
     */
    @Override
    public void setMatchNumber(Integer matchNumber) {
        this.id.setMatchNumber(matchNumber);
    }

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return this.id.getGameId();
    }

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    @Override
    public void setGameId(Alphanumeric gameId) {
        this.id.setGameId(gameId);
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
        return this.partida;
    }

    /**
     * Setter function for the match
     *
     * @param match the match
     */
    @Override
    public void setMatch(Match match) {
        this.partida = (Partida) match;
    }
}