package pt.isel.model.entities.region;

import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.player.Player;

import java.util.Set;

public interface Region {
    String getId();

    void setId(String name);

    Set<Player> getPlayers();

    void setPlayers(Set<Player> players);

    Set<Match> getMatches();

    void setMatches(Set<Match> matches);
}
