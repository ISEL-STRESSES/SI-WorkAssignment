package pt.isel.model.entities.region;

import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.player.Player;

import java.util.Set;

public interface Region {

    String getId();
    Set<Player> getPlayers();
    Set<Match> getMatches();

    void setId(String name);
    void setPlayers(Set<Player> players);
    void setMatches(Set<Match> matches);
}
