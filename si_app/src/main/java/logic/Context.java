package logic;

import logic.repositories.associations.earns.EarnsRepository;
import logic.repositories.associations.friend.FriendRepository;
import logic.repositories.associations.participates.ParticipatesRepository;
import logic.repositories.associations.plays.PlaysRepository;
import logic.repositories.associations.purchase.PurchaseRepository;
import logic.repositories.entities.chat.ChatRepository;
import logic.repositories.entities.chat.MessageRepository;
import logic.repositories.entities.game.GameRepository;
import logic.repositories.entities.game.GameStatsRepository;
import logic.repositories.entities.game.badge.BadgeRepository;
import logic.repositories.entities.game.match.MatchRepository;
import logic.repositories.entities.game.match.MultiPlayerMatchRepository;
import logic.repositories.entities.game.match.NormalMatchRepository;
import logic.repositories.entities.player.PlayerRepository;
import logic.repositories.entities.player.PlayerStatsRepository;
import logic.repositories.entities.region.RegionRepository;
import logic.repositories.entities.view.PlayerTotalInfoRepository;

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
     * Getter for the {@link PlayerTotalInfoRepository}
     *
     * @return the {@link PlayerTotalInfoRepository}
     */
    PlayerTotalInfoRepository getPlayerTotalInfo();

    /**
     * Getter for the {@link EarnsRepository}
     *
     * @return the {@link EarnsRepository}
     */
    EarnsRepository getEarns();

    /**
     * Getter for the {@link FriendRepository}
     *
     * @return the {@link FriendRepository}
     */
    FriendRepository getFriends();

    /**
     * Getter for the {@link ParticipatesRepository}
     *
     * @return the {@link ParticipatesRepository}
     */
    ParticipatesRepository getParticipates();

    /**
     * Getter for the {@link PlaysRepository}
     *
     * @return the {@link PlaysRepository}
     */
    PlaysRepository getPlays();

    /**
     * Getter for the {@link PurchaseRepository}
     *
     * @return the {@link PurchaseRepository}
     */
    PurchaseRepository getPurchases();
}
