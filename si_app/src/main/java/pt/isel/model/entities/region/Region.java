package pt.isel.model.entities.region;

import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.player.Player;

import java.util.Set;

/**
 * Represents a region
 */
public interface Region {

    /**
     * Getter function for the region id
     * @return the region id
     */
    String getId();

    /**
     * Getter function for the region players
     * @return the region players
     */
    Set<Player> getPlayers();

    /**
     * Getter function for the region matches
     * @return the region matches
     */
    Set<Match> getMatches();

    /**
     * Setter function for the region id
     * @param name the region id
     */
    void setId(String name);

    /**
     * Setter function for the region players
     * @param players the region players
     */
    void setPlayers(Set<Player> players);

    /**
     * Setter function for the region matches
     * @param matches the region matches
     */
    void setMatches(Set<Match> matches);
}
