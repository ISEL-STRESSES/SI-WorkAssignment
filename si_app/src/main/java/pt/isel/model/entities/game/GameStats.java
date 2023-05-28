package pt.isel.model.entities.game;

import pt.isel.model.types.Alphanumeric;

public interface GameStats {
    Alphanumeric getId();

    void setId(Alphanumeric id);

    Game getGame();

    void setGame(Game game);

    Integer getNrOfMatches();

    void setNrOfMatches(Integer nrOfMatches);

    Integer getNrOfPlayers();

    void setNrOfPlayers(Integer nrOfPlayers);

    Integer getTotalOfPoints();

    void setTotalOfPoints(Integer totalOfPoints);
}
