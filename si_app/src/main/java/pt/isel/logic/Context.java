package pt.isel.logic;

import pt.isel.dataAccess.Repositories;
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
import pt.isel.logic.repositories.view.PlayerTotalInfoRepository;

/**
 * Interface that represents a context
 * Extends {@link AutoCloseable}
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

    /**
     * Getter for the {@link RegionRepository}
     *
     * @return the {@link RegionRepository}
     */
    RegionRepository getRegions();

    /**
     * Getter for the {@link PlayerRepository}
     *
     * @return the {@link PlayerRepository}
     */
    PlayerRepository getPlayers();

    /**
     * Getter for the {@link PlayerStatsRepository}
     *
     * @return the {@link PlayerStatsRepository}
     */
    PlayerStatsRepository getPlayersStats();

    /**
     * Getter for the {@link GameRepository}
     *
     * @return the {@link GameRepository}
     */
    GameRepository getGames();

    /**
     * Getter for the {@link GameStatsRepository}
     *
     * @return the {@link GameStatsRepository}
     */
    GameStatsRepository getGamesStats();

    /**
     * Getter for the {@link MatchRepository}
     *
     * @return the {@link MatchRepository}
     */
    MatchRepository getMatches();

    /**
     * Getter for the {@link NormalMatchRepository}
     *
     * @return the {@link NormalMatchRepository}
     */
    NormalMatchRepository getNormalMatches();

    /**
     * Getter for the {@link MultiPlayerMatchRepository}
     *
     * @return the {@link MultiPlayerMatchRepository}
     */
    MultiPlayerMatchRepository getMultiPlayerMatches();

    /**
     * Getter for the {@link BadgeRepository}
     *
     * @return the {@link BadgeRepository}
     */
    BadgeRepository getBadges();

    /**
     * Getter for the {@link ChatRepository}
     *
     * @return the {@link ChatRepository}
     */
    ChatRepository getChats();

    /**
     * Getter for the {@link MessageRepository}
     *
     * @return the {@link MessageRepository}
     */
    MessageRepository getMessages();

    /**
     * Getter for the {@link Repositories.PlayerTotalInfoRepository}
     *
     * @return the {@link Repositories.PlayerTotalInfoRepository}
     */
    PlayerTotalInfoRepository getPlayerTotalInfo();
}
