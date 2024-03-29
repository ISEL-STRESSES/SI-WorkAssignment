package model.entities.game.badge;

import model.entities.game.Game;
import model.types.Alphanumeric;
import model.types.URL;

/**
 * Represents a badge
 */
public interface Badge {
    /**
     * Getter function for the badge id
     *
     * @return the badge id
     */
    CrachaId getId();

    /**
     * Setter function for the badge id
     *
     * @param id the badge id
     */
    void setId(CrachaId id);

    /**
     * Getter function for the badge name
     *
     * @return the badge name
     */
    String getName();

    /**
     * Setter function for the badge name
     *
     * @param name the badge name
     */
    void setName(String name);

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    Alphanumeric getGameId();

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    void setGameId(Alphanumeric gameId);

    /**
     * Getter function for the badge image
     *
     * @return the badge url image
     */
    URL getImage();

    /**
     * Setter function for the badge image
     *
     * @param image the badge url image
     */
    void setImage(URL image);

    /**
     * Getter function for the badge points
     *
     * @return the badge points
     */
    Integer getPoints();

    /**
     * Setter function for the badge points
     *
     * @param points the badge points
     */
    void setPoints(Integer points);

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
}
