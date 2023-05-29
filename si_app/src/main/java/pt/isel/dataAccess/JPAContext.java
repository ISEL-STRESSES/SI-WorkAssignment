package pt.isel.dataAccess;

import jakarta.persistence.*;
import pt.isel.logic.Context;
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
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.matches.multiplayer.PartidaMultijogadorId;
import pt.isel.model.entities.player.Player;
import pt.isel.model.entities.chat.Chat;
import pt.isel.model.types.PlayerState;
import pt.isel.model.views.Jogadortotalinfo;
import pt.isel.utils.Pair;

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
            if (emf == null || !emf.isOpen()) this.emf = Persistence.createEntityManagerFactory(persistentCtx);
            if (em == null || !em.isOpen()) this.em = emf.createEntityManager();
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
     * getter for the {@link RegionRepository}
     *
     * @return the {@link RegionRepository}
     */
    @Override
    public RegionRepository getRegions() {
        return regionRepository;
    }

    /**
     * getter for the {@link PlayerRepository}
     *
     * @return the {@link PlayerRepository}
     */
    @Override
    public PlayerRepository getPlayers() {
        return playerRepository;
    }

    /**
     * getter for the {@link PlayerStatsRepository}
     *
     * @return the {@link PlayerStatsRepository}
     */
    @Override
    public PlayerStatsRepository getPlayersStats() {
        return playerStatsRepository;
    }

    /**
     * getter for the {@link GameRepository}
     *
     * @return the {@link GameRepository}
     */
    @Override
    public GameRepository getGames() {
        return gameRepository;
    }

    /**
     * getter for the {@link GameStatsRepository}
     *
     * @return the {@link GameStatsRepository}
     */
    @Override
    public GameStatsRepository getGamesStats() {
        return gameStatsRepository;
    }

    /**
     * getter for the {@link MatchRepository}
     *
     * @return the {@link MatchRepository}
     */
    @Override
    public MatchRepository getMatches() {
        return matchRepository;
    }

    /**
     * getter for the {@link NormalMatchRepository}
     *
     * @return the {@link NormalMatchRepository}
     */
    @Override
    public NormalMatchRepository getNormalMatches() {
        return normalMatchRepository;
    }

    /**
     * getter for the {@link MultiPlayerMatchRepository}
     *
     * @return the {@link MultiPlayerMatchRepository}
     */
    @Override
    public MultiPlayerMatchRepository getMultiPlayerMatches() {
        return multiPlayerMatchRepository;
    }

    /**
     * getter for the {@link BadgeRepository}
     *
     * @return the {@link BadgeRepository}
     */
    @Override
    public BadgeRepository getBadges() {
        return badgeRepository;
    }

    /**
     * getter for the {@link ChatRepository}
     *
     * @return the {@link ChatRepository}
     */
    @Override
    public ChatRepository getChats() {
        return chatRepository;
    }

    /**
     * getter for the {@link MessageRepository}
     *
     * @return the {@link MessageRepository}
     */
    @Override
    public MessageRepository getMessages() {
        return messageRepository;
    }

    /* exercises */
    /* 2d1 */

    /**
     * TODO
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
     * TODO
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
     * TODO
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
     * TODO
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
     * TODO
     */
    public List<Pair<Player, Integer>> gamePointsByPlayer(Game game) {
        beginTransaction();
        Query q = em.createNativeQuery("call PontosJogoPorJogador(?)")
                .setParameter(1, game.getId());
        List<Pair<Player, Integer>> result = q.getResultList();
        commit();
        return result;
    }

    /* 2h */
    /**
     * TODO
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
     * TODO
     */
    public Integer initiateChat(Player player1, Player player2) {
        beginTransaction();
        Integer chatId;
        Query q = em.createNativeQuery("call iniciarConversa(?, ?, ?)")
                .setParameter(1, player1.getId())
                .setParameter(2, player2.getId())
                .setParameter(3, chatId);
        q.executeUpdate();
        commit();
        return chatId;
    }

    /* 2j */
    /**
     * TODO
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
     * TODO
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
    public List<Jogadortotalinfo> getPlayerTotalInfo() {
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
        List<Jogadortotalinfo> result; // = viewRepository.findAll();
        commit();
        return result;
    }
    /* 2m */
    // trigger
    /* 2n */
    // trigger
}
