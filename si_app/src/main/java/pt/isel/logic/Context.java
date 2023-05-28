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
     * Rolls back a transaction
     */
    void rollback();

    /**
     * Flushes the context
     */
    void flush();

    /**
     * Connects to the database
     */
    void connect();

    /**
     * getter for the {@link RegionRepository}
     * @return the {@link RegionRepository}
     */
    RegionRepository getRegions();

    /**
     * getter for the {@link PlayerRepository}
     * @return the {@link PlayerRepository}
     */
    PlayerRepository getPlayers();

    /**
     * getter for the {@link PlayerStatsRepository}
     * @return the {@link PlayerStatsRepository}
     */
    PlayerStatsRepository getPlayersStats();

    /**
     * getter for the {@link GameRepository}
     * @return the {@link GameRepository}
     */
    GameRepository getGames();

    /**
     * getter for the {@link MatchRepository}
     * @return the {@link MatchRepository}
     */
    MatchRepository getMatches();

    /**
     * getter for the {@link NormalMatchRepository}
     * @return the {@link NormalMatchRepository}
     */
    NormalMatchRepository getNormalMatches();

    /**
     * getter for the {@link MultiPlayerMatchRepository}
     * @return the {@link MultiPlayerMatchRepository}
     */
    MultiPlayerMatchRepository getMultiPlayerMatches();

    /**
     * getter for the {@link BadgeRepository}
     * @return the {@link BadgeRepository}
     */
    BadgeRepository getBadges();

    /**
     * getter for the {@link ChatRepository}
     * @return the {@link ChatRepository}
     */
    ChatRepository getChats();

    /**
     * getter for the {@link MessageRepository}
     * @return the {@link MessageRepository}
     */
    MessageRepository getMessages();

}
