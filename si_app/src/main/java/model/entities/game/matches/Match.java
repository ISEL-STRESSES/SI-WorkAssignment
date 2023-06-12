package model.entities.game.matches;

import model.associacions.plays.Joga;
import model.entities.game.Game;
import model.entities.game.matches.multiplayer.MultiPlayerMatch;
import model.entities.game.matches.normal.NormalMatch;
import model.entities.region.Region;
import model.types.Alphanumeric;

import java.time.LocalTime;
import java.util.Set;

/**
 * Represents a match
 */
public interface Match {
    /**
     * Getter function for the match id
     *
     * @return the match id
     */
    PartidaId getId();

    /**
     * Getter function for the game
     *
     * @return the game
     */
    Game getGame();

    /**
     * Setter function for the game
     *
     * @param game the game
     */
    void setGame(Game game);

    /**
     * Getter function for the game id
     *
     * @return the game id
     */
    Alphanumeric getGameId();

    /**
     * Setter function for the game id
     *
     * @param gameId the game id
     */
    void setGameId(Alphanumeric gameId);

    /**
     * Getter function for the match number
     *
     * @return the match number
     */
    Integer getMatchNumber();

    /**
     * Setter function for the match number
     *
     * @param matchNumber the match number
     */
    void setMatchNumber(Integer matchNumber);

    /**
     * Getter function for the match start date
     *
     * @return the match start date
     */
    LocalTime getStartDate();

    /**
     * Setter function for the match start date
     *
     * @param startDate the match start date
     */
    void setStartDate(LocalTime startDate);

    /**
     * Getter function for the match end date
     *
     * @return the match end date
     */
    LocalTime getEndDate();

    /**
     * Setter function for the match end date
     *
     * @param endDate the match end date
     */
    void setEndDate(LocalTime endDate);

    /**
     * Getter function for the match region
     *
     * @return the match region
     */
    Region getRegion();

    /**
     * Setter function for the match region
     *
     * @param region the match region
     */
    void setRegion(Region region);

    /**
     * Getter function for the normal match
     *
     * @return the match
     */
    NormalMatch getNormalMatch();

    /**
     * Setter function for the normal match
     *
     * @param normalMatch the match
     */
    void setNormalMatch(NormalMatch normalMatch);

    /**
     * Getter function for the multiplayer match
     *
     * @return the match
     */
    MultiPlayerMatch getMultiPlayerMatch();

    /**
     * Setter function for the multiplayer match
     *
     * @param multiPlayerMatch the match
     */
    void setMultiPlayerMatch(MultiPlayerMatch multiPlayerMatch);

    /**
     * Getter function for the plays
     *
     * @return the plays
     */
    Set<Joga> getPlays();

    /**
     * Setter function for the plays
     *
     * @param plays the plays
     */
    void setPlays(Set<Joga> plays);

    /**
     * Setter function for the match id
     *
     * @param matchId the match id
     */
    void setMatchId(Integer matchId);
}
