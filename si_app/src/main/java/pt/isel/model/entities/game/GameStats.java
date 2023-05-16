package pt.isel.model.entities.game;

import pt.isel.model.types.Alphanumeric;

public interface GameStats {
    // Methods Getters
    Game getGame();
    Alphanumeric getGameId();
    Integer getNrPartidas();
    Integer getNrJogadores();
    Integer getTotalPontos();

    // Methods Setters
    void setGame(Game game);
    void setId(Alphanumeric id);
    void setNrPartidas(Integer nrPartidas);
    void setNrJogadores(Integer nrJogadores);
    void setTotalPontos(Integer totalPontos);
}
