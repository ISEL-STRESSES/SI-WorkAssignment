package pt.isel.model.entities.game;

import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

import java.util.Set;

public interface Game {
    Alphanumeric getId();
    String getName();
    URL getUrl();
    GameStats getStats();
    Set<Badge> getBadges();
    Set<Match> getMatches();

    void setId(Alphanumeric id);
    void setName(String name);
    void setUrl(URL url);
    void setStats(GameStats stats);
    void setBadges(Set<Badge> badges);
    void setMatches(Set<Match> matches);
}
