package pt.isel.logic;

import pt.isel.logic.repositories.chat.ChatRepository;
import pt.isel.logic.repositories.chat.MessageRepository;
import pt.isel.logic.repositories.game.GameRepository;
import pt.isel.logic.repositories.game.badge.BadgeRepository;
import pt.isel.logic.repositories.game.match.MatchRepository;
import pt.isel.logic.repositories.game.match.MultiPlayerMatchRepository;
import pt.isel.logic.repositories.game.match.NormalMatchRepository;
import pt.isel.logic.repositories.player.PlayerRepository;
import pt.isel.logic.repositories.player.PlayerStatsRepository;
import pt.isel.logic.repositories.region.RegionRepository;
import pt.isel.model.entities.game.badge.Badge;

public interface Context extends AutoCloseable{

    void beginTransaction();
    void commit();
    void rollback();
    void flush();
    void connect();

    RegionRepository getRegions();
    PlayerRepository getPlayers();
    PlayerStatsRepository getPlayersStats();
    GameRepository getGames();
    MatchRepository getMatches();
    NormalMatchRepository getNormalMatches();
    MultiPlayerMatchRepository getMultiPlayerMatches();
    BadgeRepository getBadges();
    ChatRepository getChats();
    MessageRepository getMessages();

}
