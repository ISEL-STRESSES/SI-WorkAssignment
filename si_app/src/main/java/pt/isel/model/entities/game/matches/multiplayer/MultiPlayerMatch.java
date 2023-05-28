package pt.isel.model.entities.game.matches.multiplayer;

import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Alphanumeric;

import java.time.LocalDate;

/**
 * Represents a multiplayer match
 * See {@link pt.isel.model.entities.game.matches.Match}
 */
public interface MultiPlayerMatch {
    /**
     * Getter function for the match id
     * @return the match id
     */
    PartidaMultijogadorId getId();

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
    String getState();

    /**
     * Getter function for the match
     * @return the match
     */
    Match getMatch();

    /**
     * Setter function for the match id
     * @param id the match id
     */
    void setId(PartidaMultijogadorId id);


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
     * @param state the game status
     */
    void setState(String state);

    /**
     * Setter function for the match
     * @param match the match
     */
    void setMatch(Match match);
}
