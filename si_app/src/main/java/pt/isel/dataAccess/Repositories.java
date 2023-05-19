package pt.isel.dataAccess;

import pt.isel.model.entities.chat.Chat;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.GameStats;
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.MultiPlayerMatch;
import pt.isel.model.entities.game.matches.NormalMatch;
import pt.isel.model.entities.player.Player;
import pt.isel.model.entities.player.PlayerStats;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Alphanumeric;
import pt.isel.model.types.Email;

import java.util.List;

public class Repositories {
    private final JPAContext context;

    Repositories(JPAContext context) {
        this.context = context;
    }

    protected class RegionRepository implements pt.isel.logic.repositories.region.RegionRepository {
        @Override
        public Region findByKey(String key) {
            return context.em.createNamedQuery("Regiao.findByKey", Region.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }
        @SuppressWarnings("unchecked")
        @Override
        public List<Region> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<Region> findAll() {
            return context.em.createNamedQuery("Regiao.findAll", Region.class)
                    .getResultList();
        }
    }

    protected class PlayerRepository implements pt.isel.logic.repositories.player.PlayerRepository {
        @Override
        public Player findByKey(Integer key) {
            return context.em.createNamedQuery("Jogador.findByKey", Player.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }
        @SuppressWarnings("unchecked")
        @Override
        public List<Player> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<Player> findAll() {
            return context.em.createNamedQuery("Jogador.findAll", Player.class)
                    .getResultList();
        }

        @Override
        public Player findByUsername() {
            return context.em.createNamedQuery("Jogador.findByUsername", Player.class)
                    .getSingleResult();
        }

        @Override
        public Player findByEmail(Email email) {
            return context.em.createNamedQuery("Jogador.findByEmail", Player.class)
                    .setParameter("email", email.toString())
                    .getSingleResult();
        }
    }

    /**
     * TODO: Implement from here
     */
    protected class PlayerStatsRepository implements pt.isel.logic.repositories.player.PlayerStatsRepository {
        @Override
        public PlayerStats findByKey(Integer key) {
            return context.em.createNamedQuery("EstatisticasJogador.findByKey", PlayerStats.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }
        @SuppressWarnings("unchecked")
        @Override
        public List<PlayerStats> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<PlayerStats> findAll() {
            return context.em.createNamedQuery("EstatisticasJogador.findAll", PlayerStats.class)
                    .getResultList();
        }
    }

    protected class GameRepository implements pt.isel.logic.repositories.game.GameRepository {
        @Override
        public Game findByKey(Alphanumeric key) {
            return context.em.createNamedQuery("Jogo.findByKey", Game.class)
                    .setParameter("key", key.alphanumeric())
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<Game> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<Game> findAll() {
            return context.em.createNamedQuery("Jogo.findAll", Game.class)
                    .getResultList();
        }
    }

    protected class GameStatsRepository implements pt.isel.logic.repositories.game.GameStatsRepository {
        @Override
        public GameStats findByKey(Alphanumeric key) {
            return context.em.createNamedQuery("JogoEstatistica.findByKey", GameStats.class)
                    .setParameter("key", key.alphanumeric())
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<GameStats> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<GameStats> findAll() {
            return context.em.createNamedQuery("JogoEstatistica.findAll", GameStats.class)
                    .getResultList();
        }
    }

    protected class MatchRepository implements pt.isel.logic.repositories.game.match.MatchRepository {
        @Override
        public Match findByKey(Integer key) {
            return context.em.createNamedQuery("Partida.findByKey", Match.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<Match> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<Match> findAll() {
            return context.em.createNamedQuery("Partida.findAll", Match.class)
                    .getResultList();
        }
    }

    protected class NormalMatchRepository implements pt.isel.logic.repositories.game.match.NormalMatchRepository {
        @Override
        public NormalMatch findByKey(Integer key) {
            return context.em.createNamedQuery("PartidaNormal.findByKey", NormalMatch.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<NormalMatch> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<NormalMatch> findAll() {
            return context.em.createNamedQuery("PartidaNormal.findAll", NormalMatch.class)
                    .getResultList();
        }
    }

    protected class MultiPlayerMatchRepository implements pt.isel.logic.repositories.game.match.MultiPlayerMatchRepository {
        @Override
        public MultiPlayerMatch findByKey(Integer key) {
            return context.em.createNamedQuery("PartidaMultiplayer.findByKey", MultiPlayerMatch.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<MultiPlayerMatch> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<MultiPlayerMatch> findAll() {
            return context.em.createNamedQuery("PartidaMultiplayer.findAll", MultiPlayerMatch.class)
                    .getResultList();
        }
    }

    protected class BadgeRepository implements pt.isel.logic.repositories.game.badge.BadgeRepository {
        @Override
        public Badge findByKey(String key) {
            return context.em.createNamedQuery("Badge.findByKey", Badge.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<Badge> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<Badge> findAll() {
            return context.em.createNamedQuery("Badge.findAll", Badge.class)
                    .getResultList();
        }
    }

    protected class ChatRepository implements pt.isel.logic.repositories.chat.ChatRepository {

        @Override
        public Chat findByKey(Integer key) {
            return context.em.createNamedQuery("Chat.findByKey", Chat.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<Chat> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<Chat> findAll() {
            return context.em.createNamedQuery("Chat.findAll", Chat.class)
                    .getResultList();
        }
    }

    protected class MessageRepository implements pt.isel.logic.repositories.chat.MessageRepository {

        @Override
        public Message findByKey(Integer key) {
            return context.em.createNamedQuery("Message.findByKey", Message.class)
                    .setParameter("key", key)
                    .getSingleResult();
        }

        @SuppressWarnings("unchecked")
        @Override
        public List<Message> find(String jpql, Object... params) {
            return context.helperQueryImpl(jpql, params);
        }

        @Override
        public List<Message> findAll() {
            return context.em.createNamedQuery("Message.finAll", Message.class)
                    .getResultList();
        }
    }
}
