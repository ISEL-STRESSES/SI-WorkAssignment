package dataAccess;

import model.entities.chat.*;
import model.entities.game.Game;
import model.entities.game.GameStats;
import model.entities.game.Jogo;
import model.entities.game.JogoEstatistica;
import model.entities.game.badge.Badge;
import model.entities.game.badge.Cracha;
import model.entities.game.badge.CrachaId;
import model.entities.game.matches.Match;
import model.entities.game.matches.Partida;
import model.entities.game.matches.PartidaId;
import model.entities.game.matches.multiplayer.MultiPlayerMatch;
import model.entities.game.matches.multiplayer.PartidaMultijogador;
import model.entities.game.matches.normal.NormalMatch;
import model.entities.game.matches.normal.PartidaNormal;
import model.entities.player.Jogador;
import model.entities.player.JogadorEstatistica;
import model.entities.player.Player;
import model.entities.player.PlayerStats;
import model.entities.region.Regiao;
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
        public Regiao findByKey(String key) {
            return context.em.createNamedQuery("Regiao.findByKey", Regiao.class)
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
        public List<Regiao> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the regions.
         *
         * @return The list of regions.
         */
        @Override
        public List<Regiao> findAll() {
            return context.em.createNamedQuery("Regiao.findAll", Regiao.class)
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
        public Jogador findByKey(Integer key) {
            return context.em.createNamedQuery("Jogador.findByKey", Jogador.class)
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
        public List<Jogador> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the players.
         *
         * @return The list of players.
         */
        @Override
        public List<Jogador> findAll() {
            return context.em.createNamedQuery("Jogador.findAll", Jogador.class)
                    .getResultList();
        }

        /**
         * Finds the player by the username.
         *
         * @param username Username of the player.
         * @return The player.
         */
        @Override
        public Jogador findByUsername(String username) {
            return context.em.createNamedQuery("Jogador.findByUsername", Jogador.class)
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
        public Jogador findByEmail(Email email) {
            return context.em.createNamedQuery("Jogador.findByEmail", Jogador.class)
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
        public JogadorEstatistica findByKey(Integer key) {
            return context.em.createNamedQuery("Jogador_Estatistica.findByKey", JogadorEstatistica.class)
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
        public List<JogadorEstatistica> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the player stats.
         *
         * @return The list of player stats.
         */
        @Override
        public List<JogadorEstatistica> findAll() {
            return context.em.createNamedQuery("Jogador_Estatistica.findAll", JogadorEstatistica.class)
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
        public Jogo findByKey(Alphanumeric key) {
            return context.em.createNamedQuery("Jogo.findByKey", Jogo.class)
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
        public List<Jogo> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the games.
         *
         * @return The list of games.
         */
        @Override
        public List<Jogo> findAll() {
            return context.em.createNamedQuery("Jogo.findAll", Jogo.class)
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
        public JogoEstatistica findByKey(Alphanumeric key) {
            return context.em.createNamedQuery("Jogo_Estatistica.findByKey", JogoEstatistica.class)
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
        public List<JogoEstatistica> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the game stats.
         *
         * @return The list of game stats.
         */
        @Override
        public List<JogoEstatistica> findAll() {
            return context.em.createNamedQuery("Jogo_Estatistica.findAll", JogoEstatistica.class)
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
        public Partida findByKey(PartidaId key) {
            return context.em.createNamedQuery("Partida.findByKey", Partida.class)
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
        public List<Partida> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the matches.
         *
         * @return The list of matches.
         */
        @Override
        public List<Partida> findAll() {
            return context.em.createNamedQuery("Partida.findAll", Partida.class)
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
        public PartidaNormal findByKey(PartidaId key) {
            return context.em.createNamedQuery("Partida_Normal.findByKey", PartidaNormal.class)
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
        public List<PartidaNormal> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the normal matches.
         *
         * @return The list of normal matches.
         */
        @Override
        public List<PartidaNormal> findAll() {
            return context.em.createNamedQuery("Partida_Normal.findAll", PartidaNormal.class)
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
        public PartidaMultijogador findByKey(PartidaId key) {
            return context.em.createNamedQuery("Partida_multijogador.findByKey", PartidaMultijogador.class)
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
        public List<PartidaMultijogador> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the multiplayer matches.
         *
         * @return The list of multiplayer matches.
         */
        @Override
        public List<PartidaMultijogador> findAll() {
            return context.em.createNamedQuery("Partida_multijogador.findAll", PartidaMultijogador.class)
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
        public Cracha findByKey(CrachaId key) {
            return context.em.createNamedQuery("Cracha.findByKey", Cracha.class)
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
        public List<Cracha> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the badges.
         *
         * @return The list of badges.
         */
        @Override
        public List<Cracha> findAll() {
            return context.em.createNamedQuery("Cracha.findAll", Cracha.class)
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
        public Conversa findByKey(Integer key) {
            return context.em.createNamedQuery("Conversa.findByKey", Conversa.class)
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
        public List<Conversa> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the chats.
         *
         * @return The list of chats.
         */
        @Override
        public List<Conversa> findAll() {
            return context.em.createNamedQuery("Conversa.findAll", Conversa.class)
                    .getResultList();
        }

        /**
         * Finds the chat by the name.
         *
         * @param name name of the chat.
         * @return The chat.
         */
        @Override
        public Conversa findByName(String name) {
            return context.em.createNamedQuery("Conversa.findByName", Conversa.class)
                    .setParameter("name", name)
                    .getSingleResult();
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
        public Mensagem findByKey(MensagemId key) {
            return context.em.createNamedQuery("Mensagem.findByKey", Mensagem.class)
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
        public List<Mensagem> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        /**
         * Finds all the messages.
         *
         * @return The list of messages.
         */
        @Override
        public List<Mensagem> findAll() {
            return context.em.createNamedQuery("Mensagem.findAll", Mensagem.class)
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
            return context.em.createNamedQuery("Jogadortotalinfo.findByKey", Jogadortotalinfo.class)
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
        @SuppressWarnings("unchecked")
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
