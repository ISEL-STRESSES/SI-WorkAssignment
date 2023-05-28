package pt.isel.model.entities.game.matches;

import pt.isel.model.associacions.plays.Joga;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.matches.multiplayer.MultiPlayerMatch;
import pt.isel.model.entities.game.matches.normal.NormalMatch;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Alphanumeric;

import java.time.LocalDate;
import java.util.Set;

public interface Match {
    PartidaId getId();

    Game getGame();

    void setGame(Game game);

    Alphanumeric getGameId();

    void setGameId(Alphanumeric gameId);

    Integer getMatchNumber();

    void setMatchNumber(Integer matchNumber);

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    Region getRegion();

    void setRegion(Region region);

    NormalMatch getNormalMatch();

    void setNormalMatch(NormalMatch normalMatch);

    MultiPlayerMatch getMultiPlayerMatch();

    void setMultiPlayerMatch(MultiPlayerMatch multiPlayerMatch);

    Set<Joga> getPlays();

    void setPlays(Set<Joga> plays);

    void setMatchId(Integer matchId);
}
