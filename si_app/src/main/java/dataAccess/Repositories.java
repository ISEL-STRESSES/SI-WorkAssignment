package dataAccess;

import model.entities.chat.Chat;
import model.entities.chat.MensagemId;
import model.entities.chat.Message;
import model.entities.game.Game;
import model.entities.game.GameStats;
import model.entities.game.badge.Badge;
import model.entities.game.badge.CrachaId;
import model.entities.game.matches.Match;
import model.entities.game.matches.PartidaId;
import model.entities.game.matches.multiplayer.MultiPlayerMatch;
import model.entities.game.matches.multiplayer.PartidaMultijogadorId;
import model.entities.game.matches.normal.NormalMatch;
import model.entities.game.matches.normal.PartidaNormalId;
import model.entities.player.Player;
import model.entities.player.PlayerStats;
import model.entities.region.Region;
import model.types.Alphanumeric;
import model.types.Email;
import model.views.Jogadortotalinfo;

import java.util.List;

/**
 * Class responsible for creating the repositories.
 */
public class Repositories {
    private final JPAContext context;

    /**
     * Creates a new instance of the {@link Repositories} class.
     *
     * @param context Context of the JPA.
     */
    Repositories(JPAContext context) {
        this.context = context;
    }

    /**
     * Creates the repository for the entity {@link Region}.
     */
    protected class RegionRepository implements logic.repositories.region.RegionRepository {
        /**
         * Finds the region by the key.
         *
         * @param key Key of the region.
         * @return The region.
         */
        @Override
        public Region findByKey(String key) {
            return context._em.createNamedQuery("Regiao.findByKey", Region.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find regions.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of regions.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<Region> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the regions.
         *
         * @return The list of regions.
         */
        @Override
        public List<Region> findAll() {
            return context._em.createNamedQuery("Regiao.findAll", Region.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link Player}.
     */
    protected class PlayerRepository implements logic.repositories.player.PlayerRepository {
        /**
         * Finds the player by the key.
         *
         * @param key Key of the player.
         * @return The player.
         */
        @Override
        public Player findByKey(Integer key) {
            return context._em.createNamedQuery("Jogador.findByKey", Player.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find players.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of players.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<Player> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the players.
         *
         * @return The list of players.
         */
        @Override
        public List<Player> findAll() {
            return context._em.createNamedQuery("Jogador.findAll", Player.class)
                    .getResultList();
        }

        /**
         * Finds the player by the username.
         *
         * @param username Username of the player.
         * @return The player.
         */
        @Override
        public Player findByUsername(String username) {
            return context._em.createNamedQuery("Jogador.findByUsername", Player.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }

        /**
         * Finds the player by the email.
         *
         * @param email Email of the player.
         * @return The player.
         */
        @Override
        public Player findByEmail(Email email) {
            return context._em.createNamedQuery("Jogador.findByEmail", Player.class)
                    .setParameter("email", email.toString())
                    .getSingleResult();
        }
    }

    /**
     * Creates the repository for the entity {@link PlayerStats}.
     */
    protected class PlayerStatsRepository implements logic.repositories.player.PlayerStatsRepository {
        /**
         * Finds the player stats by the key.
         *
         * @param key Key of the player stats.
         * @return The player stats.
         */
        @Override
        public PlayerStats findByKey(Integer key) {
            return context._em.createNamedQuery("JogadorEstatistica.findByKey", PlayerStats.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find player stats.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of player stats.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<PlayerStats> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the player stats.
         *
         * @return The list of player stats.
         */
        @Override
        public List<PlayerStats> findAll() {
            return context._em.createNamedQuery("JogadorEstatistica.findAll", PlayerStats.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link Game}.
     */
    protected class GameRepository implements logic.repositories.game.GameRepository {
        /**
         * Finds the game by the key.
         *
         * @param key Key of the game.
         * @return The game.
         */
        @Override
        public Game findByKey(Alphanumeric key) {
            return context._em.createNamedQuery("Jogo.findByKey", Game.class)
                    .setParameter("key", key.alphanumeric())
                    .getSingleResult();
        }

        /**
         * Find games.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of games.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<Game> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the games.
         *
         * @return The list of games.
         */
        @Override
        public List<Game> findAll() {
            return context._em.createNamedQuery("Jogo.findAll", Game.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link GameStats}.
     */
    protected class GameStatsRepository implements logic.repositories.game.GameStatsRepository {
        /**
         * Finds the game stats by the key.
         *
         * @param key Key of the game stats.
         * @return The game stats.
         */
        @Override
        public GameStats findByKey(Alphanumeric key) {
            return context._em.createNamedQuery("JogoEstatistica.findByKey", GameStats.class)
                    .setParameter("key", key.alphanumeric())
                    .getSingleResult();
        }

        /**
         * Find game stats.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of game stats.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<GameStats> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the game stats.
         *
         * @return The list of game stats.
         */
        @Override
        public List<GameStats> findAll() {
            return context._em.createNamedQuery("JogoEstatistica.findAll", GameStats.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link Match}.
     */
    protected class MatchRepository implements logic.repositories.game.match.MatchRepository {
        /**
         * Finds the match by the key.
         *
         * @param key Key of the match.
         * @return The match.
         */
        @Override
        public Match findByKey(PartidaId key) {
            return context._em.createNamedQuery("Partida.findByKey", Match.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find matches.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of matches.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<Match> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the matches.
         *
         * @return The list of matches.
         */
        @Override
        public List<Match> findAll() {
            return context._em.createNamedQuery("Partida.findAll", Match.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link NormalMatch}.
     */
    protected class NormalMatchRepository implements logic.repositories.game.match.NormalMatchRepository {
        /**
         * Finds the normal match by the key.
         *
         * @param key Key of the entity
         * @return The normal match.
         */
        @Override
        public NormalMatch findByKey(PartidaNormalId key) {
            return context._em.createNamedQuery("PartidaNormal.findByKey", NormalMatch.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find normal matches.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of normal matches.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<NormalMatch> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the normal matches.
         *
         * @return The list of normal matches.
         */
        @Override
        public List<NormalMatch> findAll() {
            return context._em.createNamedQuery("PartidaNormal.findAll", NormalMatch.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link MultiPlayerMatch}.
     */
    protected class MultiPlayerMatchRepository implements logic.repositories.game.match.MultiPlayerMatchRepository {
        /**
         * Finds the multiplayer match by the key.
         *
         * @param key Key of the entity
         * @return The multiplayer match.
         */
        @Override
        public MultiPlayerMatch findByKey(PartidaMultijogadorId key) {
            return context._em.createNamedQuery("PartidaMultijogador.findByKey", MultiPlayerMatch.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find multiplayer matches.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of multiplayer matches.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<MultiPlayerMatch> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the multiplayer matches.
         *
         * @return The list of multiplayer matches.
         */
        @Override
        public List<MultiPlayerMatch> findAll() {
            return context._em.createNamedQuery("PartidaMultijogador.findAll", MultiPlayerMatch.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link Badge}.
     */
    protected class BadgeRepository implements logic.repositories.game.badge.BadgeRepository {
        /**
         * Finds the badge by the key.
         *
         * @param key Key of the badge.
         * @return The badge.
         */
        @Override
        public Badge findByKey(CrachaId key) {
            return context._em.createNamedQuery("Cracha.findByKey", Badge.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find badges.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of badges.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<Badge> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the badges.
         *
         * @return The list of badges.
         */
        @Override
        public List<Badge> findAll() {
            return context._em.createNamedQuery("Cracha.findAll", Badge.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link Chat}.
     */
    protected class ChatRepository implements logic.repositories.chat.ChatRepository {
        /**
         * Finds the chat by the key.
         *
         * @param key Key of the chat.
         * @return The chat.
         */
        @Override
        public Chat findByKey(Integer key) {
            return context._em.createNamedQuery("Conversa.findByKey", Chat.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find chats.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of chats.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<Chat> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the chats.
         *
         * @return The list of chats.
         */
        @Override
        public List<Chat> findAll() {
            return context._em.createNamedQuery("Conversa.findAll", Chat.class)
                    .getResultList();
        }
    }

    /**
     * Creates the repository for the entity {@link Message}.
     */
    protected class MessageRepository implements logic.repositories.chat.MessageRepository {
        /**
         * Finds the message by the key.
         *
         * @param key Key of the entity message.
         * @return The message.
         */
        @Override
        public Message findByKey(MensagemId key) {
            return context._em.createNamedQuery("Mensagem.findByKey", Message.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Find messages.
         *
         * @param jpql   Query to be executed.
         * @param params Parameters of the query.
         * @return The list of messages.
         */
        @SuppressWarnings("unchecked")
        @Override
        public List<Message> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the messages.
         *
         * @return The list of messages.
         */
        @Override
        public List<Message> findAll() {
            return context._em.createNamedQuery("Mensagem.findAll", Message.class)
                    .getResultList();
        }
    }

    /**
     * Creates a repository for the view {@link Jogadortotalinfo}
     */
    protected class PlayerTotalInfoRepository implements logic.repositories.view.PlayerTotalInfoRepository {

        /**
         * Finds the Player total info by its key
         *
         * @param key Key of the Player
         * @return PLayer total info
         */
        @Override
        public Jogadortotalinfo findByKey(Integer key) {
            return context._em.createNamedQuery("Jogadortotalinfo.findByKey", Jogadortotalinfo.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        /**
         * Finds a Collection of Players total info by the given query
         *
         * @param jpql   query to be executed
         * @param params parameters of the query
         * @return Collection of Players total info
         */
        @Override
        public List<Jogadortotalinfo> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all Players total info
         *
         * @return Collection of Players total info
         */
        @Override
        public List<Jogadortotalinfo> findAll() {
            return null;
        }
    }
}
