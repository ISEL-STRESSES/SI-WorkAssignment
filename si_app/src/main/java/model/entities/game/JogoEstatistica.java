package model.entities.game;

import jakarta.persistence.*;
import model.types.Alphanumeric;

/**
 * Represents a game stats
 */
@Entity
@NamedQuery(name = "Jogo_Estatistica.findAll", query = "SELECT j FROM JogoEstatistica j")
@NamedQuery(name = "Jogo_Estatistica.findByKey", query = "SELECT j FROM JogoEstatistica j where j.id = :id")
@Table(name = "jogo_estatistica", schema = "public")
public class JogoEstatistica implements GameStats {
    @Id
    @Column(name = "id_jogo", columnDefinition = "alphanumeric(0, 0) not null")
    private String id;

    @Column(name = "nr_partidas", nullable = false)
    private Integer nrOfMatches;

    @Column(name = "nr_jogadores", nullable = false)
    private Integer nrOfPlayers;

    @Column(name = "total_pontos", nullable = false)
    private Integer totalOfPoints;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo game;

    /**
     * Empty Constructor
     */
    public JogoEstatistica() {
        // for ORM only
    }

    /**
     * Constructor
     *
     * @param game          the game
     * @param nrOfMatches   the number of matches
     * @param nrOfPlayers   the number of players
     * @param totalOfPoints the total of points
     */
    public JogoEstatistica(Game game, Integer nrOfMatches, Integer nrOfPlayers, Integer totalOfPoints) {
        setGame(game);
        setId(game.getId());
        setNrOfMatches(nrOfMatches);
        setNrOfPlayers(nrOfPlayers);
        setTotalOfPoints(totalOfPoints);
    }

    /**
     * Getter function for the game stats id
     *
     * @return the game stats id
     */
    @Override
    public Alphanumeric getId() {
        return new Alphanumeric(this.id);
    }

    /**
     * Setter function for the game stats id
     *
     * @param id the game stats id
     */
    @Override
    public void setId(Alphanumeric id) {
        this.id = id.toString();
    }

    /**
     * Getter function for the game
     *
     * @return the game
     */
    @Override
    public Game getGame() {
        return this.game;
    }

    /**
     * Setter function for the game
     *
     * @param game the game
     */
    @Override
    public void setGame(Game game) {
        this.game = (Jogo) game;
    }

    /**
     * Getter function for the number of matches
     *
     * @return the number of matches
     */
    @Override
    public Integer getNrOfMatches() {
        return this.nrOfMatches;
    }

    /**
     * Setter function for the number of matches
     *
     * @param nrOfMatches the number of matches
     */
    @Override
    public void setNrOfMatches(Integer nrOfMatches) {
        this.nrOfMatches = nrOfMatches;
    }

    /**
     * Getter function for the number of players
     *
     * @return the number of players
     */
    @Override
    public Integer getNrOfPlayers() {
        return this.nrOfPlayers;
    }

    /**
     * Setter function for the number of players
     *
     * @param nrOfPlayers the number of players
     */
    @Override
    public void setNrOfPlayers(Integer nrOfPlayers) {
        this.nrOfPlayers = nrOfPlayers;
    }

    /**
     * Getter function for the total of points
     *
     * @return the total of points
     */
    @Override
    public Integer getTotalOfPoints() {
        return this.totalOfPoints;
    }

    /**
     * Setter function for the total of points
     *
     * @param totalOfPoints the total of points
     */
    @Override
    public void setTotalOfPoints(Integer totalOfPoints) {
        this.totalOfPoints = totalOfPoints;
    }
}