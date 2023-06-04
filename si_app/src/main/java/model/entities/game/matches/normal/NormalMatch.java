package model.entities.game.matches.normal;

import model.entities.game.matches.Match;
import model.types.Alphanumeric;

/**
 * Represents a normal match
 * <p>
 * See {@link Match}
 */
public interface NormalMatch {
    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    PartidaNormalId getId();

    /**
     * Setter function for the match id
     *
     * @param id the match id
     */
    void setId(PartidaNormalId id);

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    Integer getMatchNumber();


    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    Alphanumeric getGameId();


    /**
     * Getter function for the match difficulty
     *
     * @return the game status
     */
    Integer getMatchDifficulty();

    /**
     * Setter function for the game status
     *
     * @param difficulty the game status
     */
    void setMatchDifficulty(Integer difficulty);

    /**
     * Getter function for the match
     *
     * @return the match
     */
    Match getMatch();

    /**
     * Setter function for the match
     *
     * @param match the match
     */
    void setMatch(Match match);
}
