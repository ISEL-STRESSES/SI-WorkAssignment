package pt.isel.dataAccess;

import jakarta.persistence.*;
import pt.isel.logic.Context;
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
import pt.isel.model.entities.player.Player;

import java.util.List;

public class JPAContext implements Context {

    private final String persistentCtx;
    private EntityManagerFactory emf;
    protected EntityManager em;
    private EntityTransaction tx;
    private int txcount;

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

    /* Data mappers */

    private final DataMappers.PlayerDataMapper playerDataMapper;

    @Override
    public void beginTransaction() {
        if(tx == null || !tx.isActive()) {
            tx = em.getTransaction();
            tx.begin();
            txcount =0;
        }
        ++txcount;
    }

    @Override
    public void commit() {
        --txcount;
        if(txcount ==0 && tx != null) {
            tx.commit();
            tx = null;
        }
    }

    @Override
    public void flush() {
        em.flush();
    }

    @Override
    public void rollback(){
        if(txcount ==0 && tx != null) {
            tx.rollback();
            tx = null;
        }
    }

    @Override
    public void close() {
        if(tx != null && tx.isActive()) tx.rollback();
        if(em != null && em.isOpen()) em.close();
        if(emf != null && emf.isOpen()) emf.close();
    }

    @Override
    public void connect() {
        try {
            if(emf == null || !emf.isOpen()) this.emf = Persistence.createEntityManagerFactory(persistentCtx);
            if(em == null || !em.isOpen()) this.em = emf.createEntityManager();
        } catch (PersistenceException exception) {
            System.out.println(exception.getMessage() + ".");
            System.out.println("Please check the name of the persistence in the Persistence.xml file located in resources/META-INF!");
            System.out.println("The name of the persistence in there should match the name for the Factory instance to run!");
            System.out.println("If that doesn't work, delete the target folder and rebuild the project!");
            System.exit(1);
        }
    }



    protected List helperQueryImpl(String jpql, Object... params) {
        Query q = em.createQuery(jpql);

        for(int i = 0; i < params.length; ++i)
            q.setParameter(i+1, params[i]);

        return q.getResultList();
    }

    public JPAContext() {
        this("si-app");
    }

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
        this.playerDataMapper = dataMappers.new PlayerDataMapper();
    }

    /* repos*/
    /**
     * @return
     */
    @Override
    public RegionRepository getRegions() {
        return regionRepository;
    }
    @Override
    public PlayerRepository getPlayers() {
        return playerRepository;
    }

    /**
     * @return
     */
    @Override
    public PlayerStatsRepository getPlayersStats() {
        return playerStatsRepository;
    }

    /**
     * @return
     */
    @Override
    public GameRepository getGames() {
        return gameRepository;
    }

    /**
     * @return
     */
    @Override
    public MatchRepository getMatches() {
        return matchRepository;
    }

    /**
     * @return
     */
    @Override
    public NormalMatchRepository getNormalMatches() {
        return normalMatchRepository;
    }

    /**
     * @return
     */
    @Override
    public MultiPlayerMatchRepository getMultiPlayerMatches() {
        return multiPlayerMatchRepository;
    }

    /**
     * @return
     */
    @Override
    public BadgeRepository getBadges() {
        return badgeRepository;
    }

    /**
     * @return
     */
    @Override
    public ChatRepository getChats() {
        return chatRepository;
    }

    /**
     * @return
     */
    @Override
    public MessageRepository getMessages() {
        return messageRepository;
    }

    /* exercises */
    /*2d1*/
    public void createPlayer(Player player) {
        beginTransaction();
        Query q = em.createNativeQuery("call create_jogador(?, ?, ?)")
                .setParameter(1, player.getNomeRegiao())
                .setParameter(2, player.getUsername())
                .setParameter(3, player.getEmail().toString());
        q.executeUpdate();
        commit();
    }
    /*2d2*/

}
