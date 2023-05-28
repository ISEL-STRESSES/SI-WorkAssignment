package pt.isel.model.entities.player;

public interface PlayerStats {
    Integer getId();

    void setId(Integer playerId);

    Player getPlayer();

    void setPlayer(Player player);

    Integer getNrOfMatches();

    void setNrOfMatches(Integer nrOfMatches);

    Integer getNrOfGames();

    void setNrOfGames(Integer nrOfGames);

    Integer getTotalOfPoints();

    void setTotalOfPoints(Integer totalOfPoints);
}
