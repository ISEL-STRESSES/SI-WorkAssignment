package pt.isel.model.entities.game.matches.multiplayer;

import jakarta.persistence.*;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.Partida;
import pt.isel.model.types.Alphanumeric;

/**
 * Class that represents a multiplayer match
 */
@Entity
@NamedQuery(name= "PartidaMultijogador.findByKey", query = "SELECT p FROM PartidaMultijogador p WHERE p.id = :key")
@NamedQuery(name= "PartidaMultijogador.findAll", query = "SELECT p FROM PartidaMultijogador p")
@Table(name = "partida_multijogador", schema = "public")
public class PartidaMultijogador implements MultiPlayerMatch{
    @EmbeddedId
    private PartidaMultijogadorId id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "id_jogo", referencedColumnName = "id_jogo", nullable = false),
            @JoinColumn(name = "nr_partida", referencedColumnName = "nr", nullable = false)
    })
    private Partida partida;

    @Column(name = "estado", length = 20)
    private String state;

    /**
     * Getter function for the match id
     * @return the match id
     */
    @Override
    public PartidaMultijogadorId getId() {
        return id;
    }

    /**
     * Getter function for the match number
     * @return the match number
     */
    @Override
    public Integer getMatchNumber() {
        return this.id.getMatchNumber();
    }

    /**
     * Getter function for the game id
     * @return the game id
     */
    @Override
    public Alphanumeric getGameId() {
        return this.id.getGameId();
    }

    /**
     * Getter function for the game status
     * @return the game status
     */
    @Override
    public String getState() {
        return this.state;
    }

    /**
     * Getter function for the match
     * @return the match
     */
    @Override
    public Match getMatch() {
        return this.partida;
    }

    /**
     * Setter function for the match id
     * @param id the match id
     */
    @Override
    public void setId(PartidaMultijogadorId id) {
        this.id = id;
    }

    /**
     * Setter function for the match number
     * @param matchNumber the match number
     */
    @Override
    public void setMatchNumber(Integer matchNumber) {
        this.id.setMatchNumber(matchNumber);
    }

    /**
     * Setter function for the game id
     * @param gameId the game id
     */
    @Override
    public void setGameId(Alphanumeric gameId) {
        this.id.setGameId(gameId);
    }

    /**
     * Setter function for the game status
     * @param state the game status
     */
    @Override
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Setter function for the match
     * @param match the match
     */
    @Override
    public void setMatch(Match match) {
        this.partida = (Partida) match;
    }
}