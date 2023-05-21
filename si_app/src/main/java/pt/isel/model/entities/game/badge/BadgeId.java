package pt.isel.model.entities.game.badge;


import pt.isel.model.types.Alphanumeric;
import pt.isel.utils.Pair;

public interface BadgeId {
    /**
     * Getter function for the badge id
     * @return the badge id
     */
    Pair<Alphanumeric, String> getBadgeId();

    /**
     * Getter function for the game id
     * @return the game id
     */
    Alphanumeric getGameId();

    /**
     * Getter function for the badge name
     * @return the badge name
     */
    String getBadgeName();

    /**
     * Setter function for the badge id
     * @param badgeId the badge id
     */
    void setBadgeId(Pair<Alphanumeric, String> badgeId);

    /**
     * Setter function for the game id
     * @param gameId the game id
     */
    void setGameId(Alphanumeric gameId);

    /**
     * Setter function for the badge name
     * @param badgeName the badge name
     */
    void setBadgeName(String badgeName);
}
