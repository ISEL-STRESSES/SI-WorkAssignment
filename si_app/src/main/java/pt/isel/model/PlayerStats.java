package pt.isel.model;

import pt.isel.model.classImp.Jogador;

public interface PlayerStats {
    // Methods Getters
    Player getPlayer();
    Integer getPlayerId();
    Integer getNrPartidas();
    Integer getNrJogos();
    Integer getTotalPontos();

    // Methods Setters
    void setJogador(Player player);
    void setPlayerId(Integer playerId);
    void setNrPartidas(Integer nrPartidas);
    void setNrJogos(Integer nrJogos);
    void setTotalPontos(Integer totalPontos);
}
