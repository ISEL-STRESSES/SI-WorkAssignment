package model.entities.game;

import model.associacions.purchase.Compra;
import model.entities.game.badge.Badge;
import model.entities.game.matches.Match;
import model.types.Alphanumeric;
import model.types.URL;

import java.util.Set;

/**
 * Represents a game
 */
public interface Game {
    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    Alphanumeric getId();

    /**
     * Setter function for the game id
     *
     * @param id the game id
     */
    void setId(Alphanumeric id);

    /**
     * Getter function for the game name
     *
     * @return the game name
     */
    String getName();

    /**
     * Setter function for the game name
     *
     * @param name the game name
     */
    void setName(String name);

    /**
     * Getter function for the game url
     *
     * @return the game url
     */
    URL getUrl();

    /**
     * Setter function for the game url
     *
     * @param url the game url
     */
    void setUrl(URL url);

    /**
     * Getter function for the game stats
     *
     * @return the game stats
     */
    GameStats getStats();

    /**
     * Setter function for the game stats
     *
     * @param stats the game stats
     */
    void setStats(GameStats stats);

    /**
     * Getter function for the game badges
     *
     * @return the game badges
     */
    Set<Badge> getBadges();

    /**
     * Setter function for the game badges
     *
     * @param badges the game badges
     */
    void setBadges(Set<Badge> badges);

    /**
     * Getter function for the game matches
     *
     * @return the game matches
     */
    Set<Match> getMatches();

    /**
     * Setter function for the game matches
     *
     * @param matches the game matches
     */
    void setMatches(Set<Match> matches);

    /**
     * Getter function for the game purchases
     *
     * @return the game purchases
     */
    Set<Compra> getPurchases();

    /**
     * Setter function for the game purchases
     *
     * @param purchases the game purchases
     */
    void setPurchases(Set<Compra> purchases);
}
