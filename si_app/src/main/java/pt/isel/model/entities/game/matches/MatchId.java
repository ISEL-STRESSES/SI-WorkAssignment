package pt.isel.model.entities.game.matches;

import pt.isel.model.types.Alphanumeric;
import pt.isel.utils.Pair;

/**
 * Represents the primary key of a match
 */
public interface MatchId {
    /**
     * Getter function for the match id
     * @return the match id
     */
    Pair<Alphanumeric, Integer> getMatchId();

    /**
     * Getter function for the game id
     * @return the game id
     */
    Alphanumeric getGameId();

    /**
     * Getter function for the match number
     * @return the match number
     */
    Integer getMatchNumber();

    /**
     * Setter function for the match id
     * @param matchId the match id
     */
    void setMatchId(Pair<Alphanumeric, Integer> matchId);

    /**
     * Setter function for the game id
     * @param gameId the game id
     */
    void setGameId(Alphanumeric gameId);

    /**
     * Setter function for the match number
     * @param matchNumber the match number
     */
    void setMatchNumber(Integer matchNumber);
}
