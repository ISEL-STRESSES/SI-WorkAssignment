package pt.isel.model.entities.player;

/**
 * Represents a player stats
 */
public interface PlayerStats {

    /**
     * Getter function for the player stats id
     *
     * @return the player stats id
     */
    Integer getId();

    /**
     * Setter function for the player stats id
     *
     * @param playerId the player stats id
     */
    void setId(Integer playerId);

    /**
     * Getter function for the player
     *
     * @return the player
     */
    Player getPlayer();

    /**
     * Setter function for the player
     *
     * @param player the player
     */
    void setPlayer(Player player);

    /**
     * Getter function for the number of matches
     *
     * @return the number of matches
     */
    Integer getNrOfMatches();

    /**
     * Setter function for the number of matches
     *
     * @param nrOfMatches the number of matches
     */
    void setNrOfMatches(Integer nrOfMatches);

    /**
     * Getter function for the number of games
     *
     * @return the number of games
     */
    Integer getNrOfGames();

    /**
     * Setter function for the number of games
     *
     * @param nrOfGames the number of games
     */
    void setNrOfGames(Integer nrOfGames);

    /**
     * Getter function for the total of points
     *
     * @return the total of points
     */
    Integer getTotalOfPoints();

    /**
     * Setter function for the total of points
     *
     * @param totalOfPoints the total of points
     */
    void setTotalOfPoints(Integer totalOfPoints);
}
