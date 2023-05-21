package pt.isel.model.entities.game.badge;

import pt.isel.model.entities.game.Game;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

/**
 * Represents a badge
 */
public interface Badge {
    /**
     * Getter function for the badge id
     * @return the badge id
     */
    BadgeId getId();

    /**
     * Getter function for the badge name
     * @return the badge name
     */
    String getName();

    /**
     * Getter function for the game id
     * @return the game id
     */
    Alphanumeric getGameId();

    /**
     * Getter function for the badge image
     * @return the badge url image
     */
    URL getImage();

    /**
     * Getter function for the badge points
     * @return the badge points
     */
    Integer getPoints();

    /**
     * Getter function for the game
     * @return the game
     */
    Game getGame();

    /**
     * Setter function for the badge id
     * @param id the badge id
     */
    void setId(BadgeId id);

    /**
     * Setter function for the badge name
     * @param name the badge name
     */
    void setName(String name);

    /**
     * Setter function for the game id
     * @param gameId the game id
     */
    void setGameId(Alphanumeric gameId);

    /**
     * Setter function for the badge image
     * @param image the badge url image
     */
    void setImage(URL image);

    /**
     * Setter function for the badge points
     * @param points the badge points
     */
    void setPoints(Integer points);

    /**
     * Setter function for the game
     * @param game the game
     */
    void setGame(Game game);
}
