package pt.isel.model.entities.game.matches;

import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.matches.multiplayer.MultiPlayerMatch;
import pt.isel.model.entities.game.matches.normal.NormalMatch;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Alphanumeric;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a match
 */
public interface Match {
    /**
     * Getter function for the match id
     * @return the match id
     */
    MatchId getId();

    /**
     * Getter function for the game
     * @return the game
     */
    Game getGame();

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
     * Getter function for the match start date
     * @return the match start date
     */
    LocalDate getDataInicio();

    /**
     * Getter function for the match end date
     * @return the match end date
     */
    LocalDate getDataFim();

    /**
     * Getter function for the match region
     * @return the match region
     */
    Region getRegiao();

    /**
     * Getter function for the normal match
     * @return the match
     */
    NormalMatch getNormalMatch();

    /**
     * Getter function for the multiplayer match
     * @return the match
     */
    MultiPlayerMatch getMultiPlayerMatch();

    /**
     * Setter function for the match id
     * @param matchId the match id
     */
    void setMatchId(Integer matchId);

    /**
     * Setter function for the game
     * @param game the game
     */
    void setGame(Game game);

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

    /**
     * Setter function for the match start date
     * @param dataInicio the match start date
     */
    void setDataInicio(LocalDate dataInicio);

    /**
     * Setter function for the match end date
     * @param dataFim the match end date
     */
    void setDataFim(LocalDate dataFim);

    /**
     * Setter function for the match region
     * @param regiao the match region
     */
    void setNomeRegiao(Region regiao);

    /**
     * Setter function for the normal match
     * @param normalMatch the match
     */
    void setNormalMatch(NormalMatch normalMatch);

    /**
     * Setter function for the multiplayer match
     * @param multiPlayerMatch the match
     */
    void setMultiPlayerMatch(MultiPlayerMatch multiPlayerMatch);
}
