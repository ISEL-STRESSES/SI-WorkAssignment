package pt.isel.model.entities.game.matches.normal;

import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.types.Alphanumeric;

/**
 * Represents a normal match
 * <p>
 * See {@link pt.isel.model.entities.game.matches.Match}
 */
public interface NormalMatch{
    /**
     * Getter function for the match id
     * @return the match id
     */
    PartidaNormalId getId();

    /**
     * Getter function for the match number
     * @return the match number
     */
    Integer getMatchNumber();

    /**
     * Getter function for the game id
     * @return the game id
     */
    Alphanumeric getGameId();

    /**
     * Getter function for the match difficulty
     * @return the game status
     */
    Integer getMatchDifficulty();

    /**
     * Getter function for the match
     * @return the match
     */
    Match getMatch();

    /**
     * Setter function for the match id
     * @param normalMatchId the match id
     */
    void setId(PartidaNormalId normalMatchId);

    /**
     * Setter function for the match number
     * @param matchNumber the match number
     */
    void setMatchNumber(Integer matchNumber);

    /**
     * Setter function for the game id
     * @param gameId the game id
     */
    void setGameId(Alphanumeric gameId);

    /**
     * Setter function for the game status
     * @param difficulty the game status
     */
    void setMatchDifficulty(Integer difficulty);

    /**
     * Setter function for the match
     * @param match the match
     */
    void setMatch(Match match);
}
