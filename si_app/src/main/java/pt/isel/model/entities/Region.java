package pt.isel.model.entities;

import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.player.Player;

import java.util.Set;

public interface Region {

    String getNome();

    void setNome(String name);

    // Player Related
    Set<Player> getJogadores();

    void setJogadores(Set<Player> players);

    void addJogador(Player players);

    // Matches Related

    Set<Match> getPartidas();

    void setPartidas(Set<Match> matches);

    void addPartida(Match match);

}
