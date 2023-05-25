package pt.isel.model.entities.player;

public interface PlayerStats {

    Integer getId();
    Player getPlayer();
    Integer getNrOfMatches();
    Integer getNrOfGames();
    Integer getTotalOfPoints();

    void setId(Integer playerId);
    void setPlayer(Player player);
    void setNrOfMatches(Integer nrOfMatches);
    void setNrOfGames(Integer nrOfGames);
    void setTotalOfPoints(Integer totalOfPoints);
}
