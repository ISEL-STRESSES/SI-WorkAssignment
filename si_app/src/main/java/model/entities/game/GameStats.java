package model.entities.game;

import model.types.Alphanumeric;

/**
 * Represents a game stats
 */
public interface GameStats {
    /**
     * Getter function for the game stats id
     *
     * @return the game stats id
     */
    Alphanumeric getId();

    /**
     * Setter function for the game stats id
     *
     * @param id the game stats id
     */
    void setId(Alphanumeric id);

    /**
     * Getter function for the game
     *
     * @return the game
     */
    Game getGame();

    /**
     * Setter function for the game
     *
     * @param game the game
     */
    void setGame(Game game);

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
     * Getter function for the number of players
     *
     * @return the number of players
     */
    Integer getNrOfPlayers();

    /**
     * Setter function for the number of players
     *
     * @param nrOfPlayers the number of players
     */
    void setNrOfPlayers(Integer nrOfPlayers);

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
