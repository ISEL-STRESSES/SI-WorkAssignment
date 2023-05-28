package pt.isel.model.entities.game;

import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

import java.util.Set;

/**
 * Represents a game
 */
public interface Game {

    /**
     * Getter function for the game id
     * @return the game id
     */
    Alphanumeric getId();

    /**
     * Getter function for the game name
     * @return the game name
     */
    String getName();

    /**
     * Getter function for the game url
     * @return the game url
     */
    URL getUrl();

    /**
     * Getter function for the game stats
     * @return the game stats
     */
    GameStats getStats();

    /**
     * Getter function for the game badges
     * @return the game badges
     */
    Set<Badge> getBadges();

    /**
     * Getter function for the game matches
     * @return the game matches
     */
    Set<Match> getMatches();

    /**
     * Setter function for the game id
     * @param id the game id
     */
    void setId(Alphanumeric id);

    /**
     * Setter function for the game name
     * @param name the game name
     */
    void setName(String name);

    /**
     * Setter function for the game url
     * @param url the game url
     */
    void setUrl(URL url);

    /**
     * Setter function for the game stats
     * @param stats the game stats
     */
    void setStats(GameStats stats);

    /**
     * Setter function for the game badges
     * @param badges the game badges
     */
    void setBadges(Set<Badge> badges);

    /**
     * Setter function for the game matches
     * @param matches the game matches
     */
    void setMatches(Set<Match> matches);
}
