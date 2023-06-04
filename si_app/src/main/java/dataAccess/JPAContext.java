package dataAccess;

import jakarta.persistence.*;
import logic.Context;
import logic.repositories.chat.ChatRepository;
import logic.repositories.chat.MessageRepository;
import logic.repositories.game.GameRepository;
import logic.repositories.game.GameStatsRepository;
import logic.repositories.game.badge.BadgeRepository;
import logic.repositories.game.match.MatchRepository;
import logic.repositories.game.match.MultiPlayerMatchRepository;
import logic.repositories.game.match.NormalMatchRepository;
import logic.repositories.player.PlayerRepository;
import logic.repositories.player.PlayerStatsRepository;
import logic.repositories.region.RegionRepository;
import model.entities.chat.Chat;
import model.entities.game.Game;
import model.entities.game.badge.Badge;
import model.entities.player.Jogador;
import model.entities.player.Player;
import model.types.PlayerState;
import model.views.Jogadortotalinfo;

import java.io.PrintStream;
import java.util.List;

/**
 * Class responsible for the connection to the database, and exercises.
 * It is also responsible for the creation of the repositories and data mappers.
 * It implements the Context interface.
 */
public class JPAContext implements Context {

    private final String persistentCtx;
    /*repos*/
    private final Repositories.RegionRepository regionRepository;
    private final Repositories.PlayerRepository playerRepository;
    private final Repositories.PlayerStatsRepository playerStatsRepository;
    private final Repositories.GameRepository gameRepository;
    private final Repositories.GameStatsRepository gameStatsRepository;
    private final Repositories.MatchRepository matchRepository;
    private final Repositories.NormalMatchRepository normalMatchRepository;
    private final Repositories.MultiPlayerMatchRepository multiPlayerMatchRepository;
    private final Repositories.BadgeRepository badgeRepository;
    private final Repositories.ChatRepository chatRepository;
    private final Repositories.MessageRepository messageRepository;
    private final Repositories.PlayerTotalInfoRepository playerTotalInfoRepository;
    /* data mappers */
    private final DataMappers.RegionDataMapper regionDataMapper;
    private final DataMappers.PlayerDataMapper playerDataMapper;
    private final DataMappers.PlayerStatsDataMapper playerStatsDataMapper;
    private final DataMappers.GameDataMapper gameDataMapper;
    private final DataMappers.GameStatsDataMapper gameStatsDataMapper;
    private final DataMappers.MatchDataMapper matchDataMapper;
    private final DataMappers.NormalMatchDataMapper normalMatchDataMapper;
    private final DataMappers.MultiPlayerMatchDataMapper multiPlayerMatchDataMapper;
    private final DataMappers.BadgeDataMapper badgeDataMapper;
    private final DataMappers.ChatDataMapper chatDataMapper;
    private final DataMappers.MessageDataMapper messageDataMapper;
    protected EntityManager em;
    private EntityManagerFactory emf;
    private EntityTransaction tx;
    private int tx_count;

    /**
     * Constructor of the JPAContext class.
     * The name of the persistence is set to "si-app.g17".
     */
    public JPAContext() {
        this("si-app.g17");
    }

    /**
     * Constructor of the JPAContext class.
     *
     * @param persistentCtx The name of the persistence.
     */
    public JPAContext(String persistentCtx) {
        super();
        this.persistentCtx = persistentCtx;
        //repos init
        Repositories repositories = new Repositories(this);
        this.regionRepository = repositories.new RegionRepository();
        this.playerRepository = repositories.new PlayerRepository();
        this.playerStatsRepository = repositories.new PlayerStatsRepository();
        this.gameRepository = repositories.new GameRepository();
        this.gameStatsRepository = repositories.new GameStatsRepository();
        this.matchRepository = repositories.new MatchRepository();
        this.normalMatchRepository = repositories.new NormalMatchRepository();
        this.multiPlayerMatchRepository = repositories.new MultiPlayerMatchRepository();
        this.badgeRepository = repositories.new BadgeRepository();
        this.chatRepository = repositories.new ChatRepository();
        this.messageRepository = repositories.new MessageRepository();
        this.playerTotalInfoRepository = repositories.new PlayerTotalInfoRepository();
        //data mappers init
        DataMappers dataMappers = new DataMappers(this);
        this.regionDataMapper = dataMappers.new RegionDataMapper();
        this.playerDataMapper = dataMappers.new PlayerDataMapper();
        this.playerStatsDataMapper = dataMappers.new PlayerStatsDataMapper();
        this.gameDataMapper = dataMappers.new GameDataMapper();
        this.gameStatsDataMapper = dataMappers.new GameStatsDataMapper();
        this.matchDataMapper = dataMappers.new MatchDataMapper();
        this.normalMatchDataMapper = dataMappers.new NormalMatchDataMapper();
        this.multiPlayerMatchDataMapper = dataMappers.new MultiPlayerMatchDataMapper();
        this.badgeDataMapper = dataMappers.new BadgeDataMapper();
        this.chatDataMapper = dataMappers.new ChatDataMapper();
        this.messageDataMapper = dataMappers.new MessageDataMapper();
    }

    /**
     * Begins a transaction.
     * If there is no transaction, it creates one and increments the transaction counter.
     */
    @Override
    public void beginTransaction() {
        if (tx == null || !tx.isActive()) {
            tx = em.getTransaction();
            tx.begin();
            tx_count = 0;
        }
        ++tx_count;
    }

    /**
     * Commits a transaction.
     * If the transaction counter is 0, it commits the transaction and sets it to null.
     */
    @Override
    public void commit() {
        --tx_count;
        if (tx_count == 0 && tx != null) {
            tx.commit();
            tx = null;
        }
    }

    /**
     * Flushes the entity manager.
     */
    @Override
    public void flush() {
        em.flush();
    }

    /**
     * Rolls back a transaction.
     * If the transaction counter is 0, it rolls back the transaction and sets it to null.
     */
    @Override
    public void rollback() {
        if (tx_count == 0 && tx != null) {
            tx.rollback();
            tx = null;
        }
    }

    /**
     * Closes the entity manager, entity manager factory and rolls back the transaction if it is active.
     */
    @Override
    public void close() {
        if (tx != null && tx.isActive()) tx.rollback();
        if (em != null && em.isOpen()) em.close();
        if (emf != null && emf.isOpen()) emf.close();
    }

    /**
     * Connects to the database.
     * If the entity manager factory is null or closed, it creates a new one.
     * If the entity manager is null or closed, it creates a new one.
     */
    @Override
    public void connect() {
        try {
            if (emf == null || !emf.isOpen())
                this.emf = Persistence.createEntityManagerFactory(persistentCtx);
            if (em == null || !em.isOpen())
                this.em = emf.createEntityManager();
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage() + ".");
            System.out.println("Please check the name of the persistence in the Persistence.xml file located in resources/META-INF!");
            System.out.println("The name of the persistence in there should match the name for the Factory instance to run!");
            System.out.println("If that doesn't work, delete the target folder and rebuild the project!");
            System.exit(1);
        }
    }

    /**
     * Executes a query.
     *
     * @param jpql   The query to be executed.
     * @param params The parameters of the query.
     * @return The result of the query.
     */
    protected List helperQueryImpl(String jpql, Object... params) {
        Query q = em.createQuery(jpql);

        for (int i = 0; i < params.length; ++i)
            q.setParameter(i + 1, params[i]);

        return q.getResultList();
    }

    /* repositories */

    /**
     * Getter for the {@link RegionRepository}
     *
     * @return the {@link RegionRepository}
     */
    @Override
    public RegionRepository getRegions() {
        return regionRepository;
    }

    /**
     * Getter for the {@link PlayerRepository}
     *
     * @return the {@link PlayerRepository}
     */
    @Override
    public PlayerRepository getPlayers() {
        return playerRepository;
    }

    /**
     * Getter for the {@link PlayerStatsRepository}
     *
     * @return the {@link PlayerStatsRepository}
     */
    @Override
    public PlayerStatsRepository getPlayersStats() {
        return playerStatsRepository;
    }

    /**
     * Getter for the {@link GameRepository}
     *
     * @return the {@link GameRepository}
     */
    @Override
    public GameRepository getGames() {
        return gameRepository;
    }

    /**
     * Getter for the {@link GameStatsRepository}
     *
     * @return the {@link GameStatsRepository}
     */
    @Override
    public GameStatsRepository getGamesStats() {
        return gameStatsRepository;
    }

    /**
     * Getter for the {@link MatchRepository}
     *
     * @return the {@link MatchRepository}
     */
    @Override
    public MatchRepository getMatches() {
        return matchRepository;
    }

    /**
     * Getter for the {@link NormalMatchRepository}
     *
     * @return the {@link NormalMatchRepository}
     */
    @Override
    public NormalMatchRepository getNormalMatches() {
        return normalMatchRepository;
    }

    /**
     * Getter for the {@link MultiPlayerMatchRepository}
     *
     * @return the {@link MultiPlayerMatchRepository}
     */
    @Override
    public MultiPlayerMatchRepository getMultiPlayerMatches() {
        return multiPlayerMatchRepository;
    }

    /**
     * Getter for the {@link BadgeRepository}
     *
     * @return the {@link BadgeRepository}
     */
    @Override
    public BadgeRepository getBadges() {
        return badgeRepository;
    }

    /**
     * Getter for the {@link ChatRepository}
     *
     * @return the {@link ChatRepository}
     */
    @Override
    public ChatRepository getChats() {
        return chatRepository;
    }

    /**
     * Getter for the {@link MessageRepository}
     *
     * @return the {@link MessageRepository}
     */
    @Override
    public MessageRepository getMessages() {
        return messageRepository;
    }

    /**
     * Getter for the {@link Repositories.PlayerTotalInfoRepository}
     *
     * @return the {@link Repositories.PlayerTotalInfoRepository}
     */
    @Override
    public Repositories.PlayerTotalInfoRepository getPlayerTotalInfo() {
        return playerTotalInfoRepository;
    }

    /**
     * Getter for the {@link DataMappers.RegionDataMapper}
     *
     * @return the {@link DataMappers.RegionDataMapper}
     */
    private DataMappers.RegionDataMapper regionMapper() {
        return regionDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.PlayerDataMapper}
     *
     * @return the {@link DataMappers.PlayerDataMapper}
     */
    private DataMappers.PlayerDataMapper playerMapper() {
        return playerDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.PlayerStatsDataMapper}
     *
     * @return the {@link DataMappers.PlayerStatsDataMapper}
     */
    private DataMappers.PlayerStatsDataMapper playerStatsMapper() {
        return playerStatsDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.GameDataMapper}
     *
     * @return the {@link DataMappers.GameDataMapper}
     */
    private DataMappers.GameDataMapper gameMapper() {
        return gameDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.GameStatsDataMapper}
     *
     * @return the {@link DataMappers.GameStatsDataMapper}
     */
    private DataMappers.GameStatsDataMapper gameStatsMapper() {
        return gameStatsDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.MatchDataMapper}
     *
     * @return the {@link DataMappers.MatchDataMapper}
     */
    private DataMappers.MatchDataMapper matchMapper() {
        return matchDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.NormalMatchDataMapper}
     *
     * @return the {@link DataMappers.NormalMatchDataMapper}
     */
    private DataMappers.NormalMatchDataMapper normalMatchMapper() {
        return normalMatchDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.MultiPlayerMatchDataMapper}
     *
     * @return the {@link DataMappers.MultiPlayerMatchDataMapper}
     */
    private DataMappers.MultiPlayerMatchDataMapper multiPlayerMatchMapper() {
        return multiPlayerMatchDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.BadgeDataMapper}
     *
     * @return the {@link DataMappers.BadgeDataMapper}
     */
    private DataMappers.BadgeDataMapper badgeMapper() {
        return badgeDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.ChatDataMapper}
     *
     * @return the {@link DataMappers.ChatDataMapper}
     */
    private DataMappers.ChatDataMapper chatMapper() {
        return chatDataMapper;
    }

    /**
     * Getter for the {@link DataMappers.MessageDataMapper}
     *
     * @return the {@link DataMappers.MessageDataMapper}
     */
    private DataMappers.MessageDataMapper messageMapper() {
        return messageDataMapper;
    }


    /* exercises */
    /* 2d1 */

    /**
     * Persists a new {@link Player} in the database.
     *
     * @param player The player to be persisted.
     * @Requirements: The player's region must already exist in the database.<p>
     * All the player's fields must be valid.
     */
    public void createPlayer(Player player) {
        beginTransaction();
        Query q = em.createNativeQuery("call create_jogador(?, ?, ?)")
                .setParameter(1, player.getRegion().getId())
                .setParameter(2, player.getUsername())
                .setParameter(3, player.getEmail().toString());
        q.executeUpdate();
        commit();
    }

    /* 2d2 */

    /**
     * Updates the status of a {@link Player}.
     *
     * @param player   The player to be updated.
     * @param newState The new status of the player.
     * @return The updated player.
     * @Requirements: The player must already exist in the database.<p>
     * The new status must be valid.
     */
    public Player updatePlayerStatus(Player player, PlayerState newState) {
        beginTransaction();
        Query q = em.createNativeQuery("call update_estado_jogador(?, ?)")
                .setParameter(1, player.getId())
                .setParameter(2, newState.toString());
        q.executeUpdate();
        commit();
        return playerRepository.findByKey(player.getId());
    }

    /* 2e */

    /**
     * Returns the total number of points of a {@link Player}.
     *
     * @param player The player.
     * @return The total number of points of the player.
     * @Requirements: The player must already exist in the database.
     */
    public Integer playerTotalPoints(Player player) {
        beginTransaction();
        Query q = em.createNativeQuery("call totalPontosJogador(?)")
                .setParameter(1, player.getId());
        Integer result = (Integer) q.getSingleResult();
        commit();
        return result;
    }

    /* 2f */

    /**
     * Returns the total number of games of a {@link Player}.
     *
     * @param player The player.
     * @return The total number of games of the player.
     * @Requirements: The player must already exist in the database.
     */
    public Integer playerTotalGames(Player player) {
        beginTransaction();
        Query q = em.createNativeQuery("call totalJogosJogador(?)")
                .setParameter(1, player.getId());
        Integer result = (Integer) q.getSingleResult();
        commit();
        return result;
    }

    /* 2g */

    /**
     * Returns the total number of points by {@link Player} in a {@link Game}.
     *
     * @param game The game.
     * @return The total number of points by player in the game.
     * @Requirements: The game must already exist in the database.
     */
    public List<Object[]> gamePointsByPlayer(Game game) {
        beginTransaction();
        Query q = em.createNativeQuery("call PontosJogoPorJogador(?)")
                .setParameter(1, game.getId());
        List<Object[]> result = (List<Object[]>) q.getResultList();
        commit();
        return result;
    }

    /* 2h */

    /**
     * Gives a badge to a {@link Player} in a {@link Game}.
     *
     * @param player The player.
     * @param game   The game.
     * @param badge  The badge.
     * @Requirements: The player must already exist in the database.<p>
     * The game must already exist in the database.<p>
     * The badge must already exist in the database.
     */
    public void giveBadgeToPlayer(Player player, Game game, Badge badge) {
        beginTransaction();
        Query q = em.createNativeQuery("call associarCracha(?, ?, ?)")
                .setParameter(1, player.getId())
                .setParameter(2, game.getId())
                .setParameter(3, badge.getId());
        q.executeUpdate();
        commit();
    }

    /* 2i */

    /**
     * Initiates a {@link Chat} by adding a {@link Player} to it.
     *
     * @param player The player.
     * @param chat   The chat.
     * @return The chat id.
     * @Requirements: The player must already exist in the database.<p>
     */
    public Integer initiateChat(Player player, Chat chat) {
        beginTransaction();
        Integer chatId;
        Query q = em.createNativeQuery("call iniciarConversa(?, ?, ?)")
                .setParameter(1, player.getId())
                .setParameter(2, chat.getId());
        //.setParameter(3, chatId);
        q.executeUpdate();
        commit();
        return null;
    }

    /* 2j */

    /**
     * Adds a player to an already existent {@link Chat}.
     *
     * @param player The player to add to the chat.
     * @param chat   The chat.
     * @Requirements: The player must already exist in the database.<p>
     * The chat must already exist in the database.
     */
    public void addPlayerToChat(Player player, Chat chat) {
        beginTransaction();
        Query q = em.createNativeQuery("call juntarConversa(?, ?)")
                .setParameter(1, player.getId())
                .setParameter(2, chat.getId());
        q.executeUpdate();
        commit();
    }

    /* 2k */

    /**
     * Sends a message to a {@link Chat} by a {@link Player}.
     *
     * @param player  The player that sends the message.
     * @param chat    The chat.
     * @param message The text of the message.
     * @Requirements: The player must already exist in the database.<p>
     * The chat must already exist in the database.
     */
    public void sendMessage(Player player, Chat chat, String message) {
        beginTransaction();
        Query q = em.createNativeQuery("call enviarMensagem(?, ?, ?)")
                .setParameter(1, player.getId())
                .setParameter(2, chat.getId())
                .setParameter(3, message);
        q.executeUpdate();
        commit();
    }

    /* 2l */
    // view

    /**
     * Returns the players total info from the database.
     *
     * @return The players total info.
     * @Requirements: The players must not be banned.
     */
    public List<Jogadortotalinfo> getPlayersTotalInfoFromDB() {
        beginTransaction();
        Query q = em.createNativeQuery("CREATE OR REPLACE VIEW jogadorTotalInfo AS" +
                " SELECT j.id, j.estado, j.email, " +
                "totaljogosjogador(?) AS totalJogos, " +
                "totalpartidasjogador(?) AS totalPartidas, " +
                "totalpontosjogador(?) AS totalPontos " +
                "FROM jogador j WHERE j.estado <>'BANIDO'");
        q.executeUpdate();
        commit();

        beginTransaction();
        List<Jogadortotalinfo> result = playerTotalInfoRepository.findAll();
        commit();
        return result;
    }

    /**
     * TEST METHOD
     */
    public void test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("si-app.g17");
        System.out.println("EMF: " + emf);
        EntityManager em = emf.createEntityManager();
        System.out.println("EM: " + em);
        try {
            Jogador j = em.find(Jogador.class, 1);
            System.out.println(j.getId());
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(System.out));
        } finally {
            em.close();
            emf.close();
        }
    }
}
