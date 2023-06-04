package model.entities.game.matches.multiplayer;

import model.entities.game.matches.Match;
import model.types.Alphanumeric;
import model.types.MultiPlayerMatchState;

/**
 * Represents a multiplayer match
 * <p>
 * See {@link Match}
 */
public interface MultiPlayerMatch {
    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    PartidaMultijogadorId getId();

    /**
     * Setter function for the match id
     *
     * @param id the match id
     */
    void setId(PartidaMultijogadorId id);

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
     * Getter function for the game status
     *
     * @return the game status
     */
    MultiPlayerMatchState getState();

    /**
     * Setter function for the game status
     *
     * @param state the game status
     */
    void setState(MultiPlayerMatchState state);

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
