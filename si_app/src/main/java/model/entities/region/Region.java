package model.entities.region;

import model.entities.game.matches.Match;
import model.entities.player.Player;

import java.util.Set;

/**
 * Represents a region
 */
public interface Region {

    /**
     * Getter function for the region id
     *
     * @return the region id
     */
    String getId();

    /**
     * Setter function for the region id
     *
     * @param name the region id
     */
    void setId(String name);

    /**
     * Getter function for the region players
     *
     * @return the region players
     */
    Set<Player> getPlayers();

    /**
     * Setter function for the region players
     *
     * @param players the region players
     */
    void setPlayers(Set<Player> players);

    /**
     * Getter function for the region matches
     *
     * @return the region matches
     */
    Set<Match> getMatches();

    /**
     * Setter function for the region matches
     *
     * @param matches the region matches
     */
    void setMatches(Set<Match> matches);
}
