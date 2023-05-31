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
import utils.Pair;

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
    protected EntityManager _em;
    private EntityManagerFactory _emf;
    private EntityTransaction _tx;
    private int _tx_count;

    /**
     * Constructor of the JPAContext class.
     * The name of the persistence is set to "si-app.g17".
     */
    public JPAContext() {
        this("JPAExemplo");
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
        if (_tx == null || !_tx.isActive()) {
            _tx = _em.getTransaction();
            _tx.begin();
            _tx_count = 0;
        }
        ++_tx_count;
    }

    /**
     * Commits a transaction.
     * If the transaction counter is 0, it commits the transaction and sets it to null.
     */
    @Override
    public void commit() {
        --_tx_count;
        if (_tx_count == 0 && _tx != null) {
            _tx.commit();
            _tx = null;
        }
    }

    /**
     * Flushes the entity manager.
     */
    @Override
    public void flush() {
        _em.flush();
    }

    /**
     * Rolls back a transaction.
     * If the transaction counter is 0, it rolls back the transaction and sets it to null.
     */
    @Override
    public void rollback() {
        if (_tx_count == 0 && _tx != null) {
            _tx.rollback();
            _tx = null;
        }
    }

    /**
     * Closes the entity manager, entity manager factory and rolls back the transaction if it is active.
     */
    @Override
    public void close() {
        if (_tx != null && _tx.isActive()) _tx.rollback();
        if (_em != null && _em.isOpen()) _em.close();
        if (_emf != null && _emf.isOpen()) _emf.close();
    }

    /**
     * Connects to the database.
     * If the entity manager factory is null or closed, it creates a new one.
     * If the entity manager is null or closed, it creates a new one.
     */
    @Override
    public void connect() {
        try {
            if (_emf == null || !_emf.isOpen())
                this._emf = Persistence.createEntityManagerFactory(persistentCtx);
            if (_em == null || !_em.isOpen()) {
                this._em = _emf.createEntityManager();
                System.out.println(_em);
            }
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
        Query q = _em.createQuery(jpql);

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

    /**
     * getter for the {@link Repositories.PlayerTotalInfoRepository}
     *
     * @return the {@link Repositories.PlayerTotalInfoRepository}
     */
    @Override
    public Repositories.PlayerTotalInfoRepository getPlayerTotalInfo() {
        return playerTotalInfoRepository;
    }

    /* exercises */
    /* 2d1 */

    /**
     * TODO
     */
    public void createPlayer(Player player) {
        beginTransaction();
        Query q = _em.createNativeQuery("call create_jogador(?, ?, ?)")
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
        Query q = _em.createNativeQuery("call update_estado_jogador(?, ?)")
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
        Query q = _em.createNativeQuery("call totalPontosJogador(?)")
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
        Query q = _em.createNativeQuery("call totalJogosJogador(?)")
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
        Query q = _em.createNativeQuery("call PontosJogoPorJogador(?)")
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
        Query q = _em.createNativeQuery("call associarCracha(?, ?, ?)")
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
        Query q = _em.createNativeQuery("call iniciarConversa(?, ?, ?)")
                .setParameter(1, player1.getId())
                .setParameter(2, player2.getId());
        //.setParameter(3, chatId);
        q.executeUpdate();
        commit();
        return null;
    }

    /* 2j */

    /**
     * TODO
     */
    public void addPlayerToChat(Player player, Chat chat) {
        beginTransaction();
        Query q = _em.createNativeQuery("call juntarConversa(?, ?)")
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
        Query q = _em.createNativeQuery("call enviarMensagem(?, ?, ?)")
                .setParameter(1, player.getId())
                .setParameter(2, chat.getId())
                .setParameter(3, message);
        q.executeUpdate();
        commit();
    }

    /* 2l */
    // view

    /**
     * TODO
     */
    public List<Jogadortotalinfo> getPlayersTotalInfoFromDB() {
        beginTransaction();
        Query q = _em.createNativeQuery("CREATE OR REPLACE VIEW jogadorTotalInfo AS" +
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

    public void test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAExemplo");
        EntityManager em = emf.createEntityManager();
        try {
            Jogador j = em.find(Jogador.class, 1);
            System.out.println(j.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }
    /* 2m */
    // trigger
    /* 2n */
    // trigger
}
