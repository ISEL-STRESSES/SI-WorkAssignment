package pt.isel.model.entities.game;

import pt.isel.model.associacions.purchase.Compra;
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.URL;

import java.util.Set;

public interface Game {
    Alphanumeric getId();

    void setId(Alphanumeric id);

    String getName();

    void setName(String name);

    URL getUrl();

    void setUrl(URL url);

    GameStats getStats();

    void setStats(GameStats stats);

    Set<Badge> getBadges();

    void setBadges(Set<Badge> badges);

    Set<Match> getMatches();

    void setMatches(Set<Match> matches);

    Set<Compra> getPurchases();

    void setPurchases(Set<Compra> purchases);
}
