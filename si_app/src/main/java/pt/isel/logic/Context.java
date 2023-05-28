package pt.isel.logic;

import pt.isel.logic.repositories.chat.ChatRepository;
import pt.isel.logic.repositories.chat.MessageRepository;
import pt.isel.logic.repositories.game.GameRepository;
import pt.isel.logic.repositories.game.GameStatsRepository;
import pt.isel.logic.repositories.game.badge.BadgeRepository;
import pt.isel.logic.repositories.game.match.MatchRepository;
import pt.isel.logic.repositories.game.match.MultiPlayerMatchRepository;
import pt.isel.logic.repositories.game.match.NormalMatchRepository;
import pt.isel.logic.repositories.player.PlayerRepository;
import pt.isel.logic.repositories.player.PlayerStatsRepository;
import pt.isel.logic.repositories.region.RegionRepository;

/**
 * Interface that represents a context
 *
 * @see AutoCloseable
 */
public interface Context extends AutoCloseable {

    /**
     * Begins a transaction
     */
    void beginTransaction();

    /**
     * Commits a transaction
     */
    void commit();

    /**
     * Rollbacks a transaction
     */
    void rollback();

    /**
     * Flushes the context
     */
    void flush();

    /**
     * Connects to the current transaction context
     */
    void connect();

    RegionRepository getRegions();

    PlayerRepository getPlayers();

    PlayerStatsRepository getPlayersStats();

    GameRepository getGames();

    GameStatsRepository getGamesStats();

    MatchRepository getMatches();

    NormalMatchRepository getNormalMatches();

    MultiPlayerMatchRepository getMultiPlayerMatches();

    BadgeRepository getBadges();

    ChatRepository getChats();

    MessageRepository getMessages();

}
