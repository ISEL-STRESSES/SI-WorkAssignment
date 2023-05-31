package model.entities.player;

import jakarta.persistence.*;

/**
 * Represents a player stats
 */
@Entity
@NamedQuery(name = "JogadorEstatistica.findAll", query = "SELECT je FROM JogadorEstatistica je")
@NamedQuery(name = "JogadorEstatistica.findByKey", query = "SELECT je FROM JogadorEstatistica je where je.id = :id")
@Table(name = "jogador_estatistica", schema = "public")
public class JogadorEstatistica implements PlayerStats {
    @Id
    @Column(name = "id_jogador", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_jogador", nullable = false)
    private Jogador player;
    @Column(name = "nr_partidas")
    private Integer nrOfMatches;

    @Column(name = "nr_jogos")
    private Integer nrOfGames;

    @Column(name = "total_pontos")
    private Integer totalOfPoints;

    /**
     * Getter function for the player id
     *
     * @return the player id
     */
    @Override
    public Integer getId() {
        return this.id;
    }

    /**
     * Setter function for the player id
     *
     * @param playerId the player id
     */
    @Override
    public void setId(Integer playerId) {
        this.id = playerId;
    }

    /**
     * Getter function for the player
     *
     * @return the player
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Setter function for the player
     *
     * @param player the player
     */
    @Override
    public void setPlayer(Player player) {
        this.player = (Jogador) player;
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
     * Getter function for the number of games
     *
     * @return the number of games
     */
    @Override
    public Integer getNrOfGames() {
        return this.nrOfGames;
    }

    /**
     * Setter function for the number of games
     *
     * @param nrOfGames the number of games
     */
    @Override
    public void setNrOfGames(Integer nrOfGames) {
        this.nrOfGames = nrOfGames;
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