package pt.isel.model.entities.game.matches.multiplayer;

import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.MatchId;
import pt.isel.model.types.Alphanumeric;

/**
 * Represents a multiplayer match
 * <p>
 * See {@link pt.isel.model.entities.game.matches.Match}
 */
public interface MultiPlayerMatch {
    /**
     * Getter function for the match id
     * @return the match id
     */
    MatchId getId();

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
     * Getter function for the game status
     * @return the game status
     */
    String getMatchStatus();

    /**
     * Getter function for the match
     * @return the match
     */
    Match getMatch();

    /**
     * Setter function for the match id
     * @param matchId the match id
     */
    void setMatchId(MatchId matchId);

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
     * @param machStatus the game status
     */
    void setMatchStatus(String machStatus);

    /**
     * Setter function for the match
     * @param match the match
     */
    void setMatch(Match match);
}
