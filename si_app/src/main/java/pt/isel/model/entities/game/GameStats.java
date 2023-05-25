package pt.isel.model.entities.game;

import pt.isel.model.types.Alphanumeric;

public interface GameStats {
    Alphanumeric getId();
    Game getGame();
    Integer getNrOfMatches();
    Integer getNrOfPlayers();
    Integer getTotalOfPoints();

    void setId(Alphanumeric id);
    void setGame(Game game);
    void setNrOfMatches(Integer nrOfMatches);
    void setNrOfPlayers(Integer nrOfPlayers);
    void setTotalOfPoints(Integer totalOfPoints);
}
