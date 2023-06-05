package dataAccess;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import logic.mappers.entities.chat.ChatMapper;
import logic.mappers.entities.chat.MessageMapper;
import logic.mappers.entities.game.GameMapper;
import logic.mappers.entities.game.GameStatsMapper;
import logic.mappers.entities.game.badge.BadgeMapper;
import logic.mappers.entities.game.match.MatchMapper;
import logic.mappers.entities.game.match.MultiplayerMatchMapper;
import logic.mappers.entities.game.match.NormalMatchMapper;
import logic.mappers.entities.player.PlayerMapper;
import logic.mappers.entities.player.PlayerStatsMapper;
import logic.mappers.entities.region.RegionMapper;
import model.associacions.earns.Earns;
import model.associacions.earns.Ganha;
import model.associacions.earns.GanhaId;
import model.associacions.friend.Amigo;
import model.associacions.friend.AmigoId;
import model.associacions.friend.Friend;
import model.associacions.participates.Participa;
import model.associacions.participates.ParticipaId;
import model.associacions.participates.Participates;
import model.associacions.plays.Joga;
import model.associacions.plays.JogaId;
import model.associacions.plays.Plays;
import model.associacions.purchase.Compra;
import model.associacions.purchase.CompraId;
import model.associacions.purchase.Purchase;
import model.entities.chat.Conversa;
import model.entities.chat.Mensagem;
import model.entities.chat.MensagemId;
import model.entities.chat.Message;
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
import model.entities.game.matches.multiplayer.PartidaMultijogadorId;
import model.entities.game.matches.normal.NormalMatch;
import model.entities.game.matches.normal.PartidaNormal;
import model.entities.game.matches.normal.PartidaNormalId;
import model.entities.player.Jogador;
import model.entities.player.JogadorEstatistica;
import model.entities.player.Player;
import model.entities.player.PlayerStats;
import model.entities.region.Regiao;
import model.entities.region.Region;
import model.types.Alphanumeric;

/**
 * Class responsible for creating the data mappers.
 */
public class DataMappers {

    private final JPAContext context;

    /**
     * Creates a new instance of the {@link DataMappers} class.
     *
     * @param context The JPA context
     */
    public DataMappers(JPAContext context) {
        this.context = context;
    }

    /**
     * Creates the data mapper for the {@link Region} entity.
     */
    protected class RegionDataMapper implements RegionMapper {

        /**
         * Creates a new region in the database.
         *
         * @param entity region to be created
         * @return The key of the created region
         */
        @Override
        public String create(Regiao entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a region from the database.
         *
         * @param id Key of the region to be read
         * @return The region with the given key
         */
        @Override
        public Regiao read(String id) {
            return context.em.find(Regiao.class, id);
        }

        /**
         * Updates a region in the database.
         *
         * @param entity region to be updated
         * @return The key of the updated region
         */
        @Override
        public String update(Regiao entity) {
            context.beginTransaction();
            Regiao region = context.em.find(Regiao.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (region == null) {
                throw new EntityNotFoundException("Region with id " + entity.getId() + " not found");
            }
            region.setId(entity.getId());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a region from the database.
         *
         * @param id Key of the region to be deleted
         */
        @Override
        public void delete(String id) {
            context.beginTransaction();
            Regiao region = context.em.find(Regiao.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (region == null) {
                throw new EntityNotFoundException("Region with id " + id + " not found");
            }
            context.em.remove(region);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Player} entity.
     */
    protected class PlayerDataMapper implements PlayerMapper {

        /**
         * Creates a new player in the database.
         *
         * @param entity player to be created
         * @return The key of the created player
         */
        @Override
        public Integer create(Jogador entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a player from the database.
         *
         * @param id Key of the player to be read
         * @return The player with the given key
         */
        @Override
        public Jogador read(Integer id) {
            return context.em.find(Jogador.class, id);
        }

        /**
         * Updates a player in the database.
         *
         * @param entity Player to be updated
         * @return The key of the updated player
         */
        @Override
        public Integer update(Jogador entity) {
            context.beginTransaction();
            Jogador player = context.em.find(Jogador.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (player == null) {
                throw new EntityNotFoundException("Player with id " + entity.getId() + " not found");
            }
            player.setUsername(entity.getUsername());
            player.setEmail(entity.getEmail());
            player.setState(entity.getState());
            player.setRegion(entity.getRegion());
            player.setStats(entity.getStats());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a player from the database.
         *
         * @param id Key of the player to be deleted
         */
        @Override
        public void delete(Integer id) {
            context.beginTransaction();
            Jogador player = context.em.find(Jogador.class, id, LockModeType.PESSIMISTIC_WRITE);
            JogadorEstatistica playerStats = context.em.find(JogadorEstatistica.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (player == null) {
                throw new EntityNotFoundException("Player with id " + id + " not found");
            }
            context.em.remove(playerStats);
            context.em.remove(player);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link PlayerStats} entity.
     */
    protected class PlayerStatsDataMapper implements PlayerStatsMapper {

        /**
         * Creates a new player stats in the database.
         *
         * @param entity PlayerStats to be created
         * @return The key of the created player stats
         */
        @Override
        public Integer create(JogadorEstatistica entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a player stats from the database.
         *
         * @param id Key of the PlayerStats to be read
         * @return The player stats with the given key
         */
        @Override
        public JogadorEstatistica read(Integer id) {
            return context.em.find(JogadorEstatistica.class, id);
        }

        /**
         * Updates a player stats in the database.
         *
         * @param entity PlayerStats to be updated
         * @return The key of the updated player stats
         */
        @Override
        public Integer update(JogadorEstatistica entity) {
            context.beginTransaction();
            JogadorEstatistica playerStats = context.em.find(JogadorEstatistica.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (playerStats == null) {
                throw new EntityNotFoundException("PlayerStats with id " + entity.getId() + " not found");
            }
            playerStats.setId(entity.getId());
            playerStats.setNrOfGames(entity.getNrOfGames());
            playerStats.setNrOfMatches(entity.getNrOfMatches());
            playerStats.setTotalOfPoints(entity.getTotalOfPoints());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a player stats from the database.
         *
         * @param id Key of the PlayerStats to be deleted
         */
        @Override
        public void delete(Integer id) {
            throw new UnsupportedOperationException("Not possible to delete PlayerStats");
        }
    }

    /**
     * Creates the data mapper for the {@link Game} entity.
     */
    protected class GameDataMapper implements GameMapper {

        /**
         * Creates a new game in the database.
         *
         * @param entity Game to be created
         * @return The key of the created game
         */
        @Override
        public Alphanumeric create(Jogo entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a game from the database.
         *
         * @param id Key of the game to be read
         * @return The game with the given key
         */
        @Override
        public Jogo read(Alphanumeric id) {
            return context.em.find(Jogo.class, id);
        }

        /**
         * Updates a game in the database.
         *
         * @param entity game to be updated
         * @return The key of the updated game
         */
        @Override
        public Alphanumeric update(Jogo entity) {
            context.beginTransaction();
            Jogo game = context.em.find(Jogo.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (game == null) {
                throw new EntityNotFoundException("Game with id " + entity.getId() + " not found");
            }
            game.setId(entity.getId());
            game.setName(entity.getName());
            game.setUrl(entity.getUrl());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a game from the database.
         *
         * @param id Key of the game to be deleted
         */
        @Override
        public void delete(Alphanumeric id) {
            context.beginTransaction();
            Jogo game = context.em.find(Jogo.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (game == null) {
                throw new EntityNotFoundException("Game with id " + id + " not found");
            }
            JogoEstatistica gameStats = context.em.find(JogoEstatistica.class, id, LockModeType.PESSIMISTIC_WRITE);
            context.em.remove(gameStats);
            context.em.remove(game);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link GameStats} entity.
     */
    protected class GameStatsDataMapper implements GameStatsMapper {

        /**
         * Creates a new game stats in the database.
         *
         * @param entity GameStats to be created
         * @return The key of the created game stats
         */
        @Override
        public Alphanumeric create(JogoEstatistica entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a game stats from the database.
         *
         * @param id Key of the game stats to be read
         * @return The game stats with the given key
         */
        @Override
        public JogoEstatistica read(Alphanumeric id) {
            return context.em.find(JogoEstatistica.class, id);
        }

        /**
         * Updates a game stats in the database.
         *
         * @param entity game stats to be updated
         * @return The key of the updated game stats
         */
        @Override
        public Alphanumeric update(JogoEstatistica entity) {
            context.beginTransaction();
            JogoEstatistica gameStats = context.em.find(JogoEstatistica.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (gameStats == null) {
                throw new EntityNotFoundException("GameStats with id " + entity.getId() + " not found");
            }
            gameStats.setId(entity.getId());
            gameStats.setGame(entity.getGame());
            gameStats.setNrOfMatches(entity.getNrOfMatches());
            gameStats.setTotalOfPoints(entity.getTotalOfPoints());
            gameStats.setNrOfPlayers(entity.getNrOfPlayers());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a game stats from the database.
         *
         * @param id Key of the game stats to be deleted
         */
        @Override
        public void delete(Alphanumeric id) {
            throw new UnsupportedOperationException("Not possible to delete GameStats");
        }
    }

    /**
     * Creates the data mapper for the {@link Match} entity.
     */
    protected class MatchDataMapper implements MatchMapper {

        /**
         * Creates a new match in the database.
         *
         * @param entity match to be created
         * @return The key of the created match
         */
        @Override
        public PartidaId create(Partida entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a match from the database.
         *
         * @param id Key of the match to be read
         * @return The match with the given key
         */
        @Override
        public Partida read(PartidaId id) {
            return context.em.find(Partida.class, id);
        }

        /**
         * Updates a match in the database.
         *
         * @param entity match to be updated
         * @return The key of the updated match
         */
        @Override
        public PartidaId update(Partida entity) {
            throw new UnsupportedOperationException("Not possible to update Match");
        }

        /**
         * Deletes a match from the database.
         *
         * @param id Key of the match to be deleted
         */
        @Override
        public void delete(PartidaId id) {
            throw new UnsupportedOperationException("Not possible to update Match");
        }
    }

    /**
     * Creates the data mapper for the {@link NormalMatch} entity.
     */
    protected class NormalMatchDataMapper implements NormalMatchMapper {

        /**
         * Creates a new normal match in the database.
         *
         * @param entity normal match to be created
         * @return The key of the created normal match
         */
        @Override
        public PartidaNormalId create(PartidaNormal entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a normal match from the database.
         *
         * @param id Key of the normal match to be read
         * @return The normal match with the given key
         */
        @Override
        public PartidaNormal read(PartidaNormalId id) {
            return context.em.find(PartidaNormal.class, id);
        }

        /**
         * Updates a normal match in the database.
         *
         * @param entity normal match to be updated
         * @return The key of the updated normal match
         */
        @Override
        public PartidaNormalId update(PartidaNormal entity) {
            context.beginTransaction();
            NormalMatch normalMatch = context.em.find(NormalMatch.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (normalMatch == null) {
                throw new EntityNotFoundException("NormalMatch with id " + entity.getId() + " not found");
            }
            normalMatch.setId(entity.getId());
            normalMatch.setMatch(entity.getMatch());
            normalMatch.setMatchDifficulty(entity.getMatchDifficulty());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a normal match from the database.
         *
         * @param id Key of the normal match to be deleted
         */
        @Override
        public void delete(PartidaNormalId id) {
            context.beginTransaction();
            NormalMatch normalMatch = context.em.find(NormalMatch.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (normalMatch == null) {
                throw new EntityNotFoundException("NormalMatch with id " + id + " not found");
            }
            context.em.remove(normalMatch);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link MultiPlayerMatch} entity.
     */
    protected class MultiPlayerMatchDataMapper implements MultiplayerMatchMapper {

        /**
         * Creates a new multiplayer match in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public PartidaMultijogadorId create(PartidaMultijogador entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a multiplayer match from the database.
         *
         * @param id Key of the multiplayer match to be read
         * @return The multiplayer match with the given key
         */
        @Override
        public PartidaMultijogador read(PartidaMultijogadorId id) {
            return context.em.find(PartidaMultijogador.class, id);
        }

        /**
         * Updates a multiplayer match in the database.
         *
         * @param entity multiplayer match to be updated
         * @return The key of the updated multiplayer match
         */
        @Override
        public PartidaMultijogadorId update(PartidaMultijogador entity) {
            context.beginTransaction();
            PartidaMultijogador multiPlayerMatch = context.em.find(PartidaMultijogador.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (multiPlayerMatch == null) {
                throw new EntityNotFoundException("MultiPlayerMatch with id " + entity.getId() + " not found");
            }
            multiPlayerMatch.setId(entity.getId());
            multiPlayerMatch.setMatch(entity.getMatch());
            multiPlayerMatch.setState(entity.getState());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a multiplayer match from the database.
         *
         * @param id Key of the multiplayer match to be deleted
         */
        @Override
        public void delete(PartidaMultijogadorId id) {
            context.beginTransaction();
            PartidaMultijogador multiPlayerMatch = context.em.find(PartidaMultijogador.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (multiPlayerMatch == null) {
                throw new EntityNotFoundException("MultiPlayerMatch with id " + id + " not found");
            }
            context.em.remove(multiPlayerMatch);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Badge} entity.
     */
    protected class BadgeDataMapper implements BadgeMapper {

        /**
         * Creates a new badge in the database.
         *
         * @param entity badge to be created
         * @return The key of the created badge
         */
        @Override
        public CrachaId create(Cracha entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a badge from the database.
         *
         * @param id Key of the badge to be read
         * @return The badge with the given key
         */
        @Override
        public Cracha read(CrachaId id) {
            return context.em.find(Cracha.class, id);
        }

        /**
         * Updates a badge in the database.
         *
         * @param entity badge to be updated
         * @return The key of the updated badge
         */
        @Override
        public CrachaId update(Cracha entity) {
            context.beginTransaction();
            Cracha badge = context.em.find(Cracha.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (badge == null) {
                throw new EntityNotFoundException("Badge with id " + entity.getId() + " not found");
            }
            badge.setId(entity.getId());
            badge.setGameId(entity.getGameId());
            badge.setImage(entity.getImage());
            badge.setPoints(entity.getPoints());
            badge.setGame(entity.getGame());
            badge.setName(entity.getName());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a badge from the database.
         *
         * @param id Key of the badge to be deleted
         */
        @Override
        public void delete(CrachaId id) {
            context.beginTransaction();
            Cracha badge = context.em.find(Cracha.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (badge == null) {
                throw new EntityNotFoundException("Badge with id " + id + " not found");
            }
            context.em.remove(badge);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Conversa} entity.
     */
    protected class ChatDataMapper implements ChatMapper {

        /**
         * Creates a new chat in the database.
         *
         * @param entity chat to be created
         * @return The key of the created chat
         */
        @Override
        public Integer create(Conversa entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a chat from the database.
         *
         * @param id Key of the chat to be read
         * @return The chat with the given key
         */
        @Override
        public Conversa read(Integer id) {
            return context.em.find(Conversa.class, id);
        }

        /**
         * Updates a chat in the database.
         *
         * @param entity chat to be updated
         * @return The key of the updated chat
         */
        @Override
        public Integer update(Conversa entity) {
            context.beginTransaction();
            Conversa chat = context.em.find(Conversa.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (chat == null) {
                throw new EntityNotFoundException("Chat with id " + entity.getId() + " not found");
            }
            chat.setId(entity.getId());
            chat.setName(entity.getName());
            chat.setMessages(entity.getMessages());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a chat from the database.
         *
         * @param id Key of the chat to be deleted
         */
        @Override
        public void delete(Integer id) {
            context.beginTransaction();
            Conversa chat = context.em.find(Conversa.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (chat == null) {
                throw new EntityNotFoundException("Chat with id " + id + " not found");
            }
            context.em.remove(chat);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Message} entity.
     */
    protected class MessageDataMapper implements MessageMapper {

        /**
         * Creates a new message in the database.
         *
         * @param entity message to be created
         * @return The key of the created message
         */
        @Override
        public MensagemId create(Mensagem entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a message from the database.
         *
         * @param id Key of the message to be read
         * @return The message with the given key
         */
        @Override
        public Mensagem read(MensagemId id) {
            return context.em.find(Mensagem.class, id);
        }

        /**
         * Updates a message in the database.
         *
         * @param entity message to be updated
         * @return The key of the updated message
         */
        @Override
        public MensagemId update(Mensagem entity) {
            context.beginTransaction();
            Mensagem message = context.em.find(Mensagem.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (message == null) {
                throw new EntityNotFoundException("Message with id " + entity.getId() + " not found");
            }
            message.setId(entity.getId());
            message.setChatId(entity.getChatId());
            message.setChat(entity.getChat());
            message.setMessageNumber(entity.getMessageNumber());
            message.setPlayer(entity.getPlayer());
            message.setUserId(entity.getUserId());
            message.setDate(entity.getDate());
            message.setText(entity.getText());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a message from the database.
         *
         * @param id Key of the message to be deleted
         */
        @Override
        public void delete(MensagemId id) {
            context.beginTransaction();
            Mensagem message = context.em.find(Mensagem.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (message == null) {
                throw new EntityNotFoundException("Message with id " + id + " not found");
            }
            context.em.remove(message);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Earns} association.
     */
    protected class EarnsDataMapper implements logic.mappers.associations.earns.EarnsMapper {


        /**
         * Creates a new earns association in the database.
         *
         * @param entity earns association to be created
         * @return The key of the created earns association
         */
        @Override
        public GanhaId create(Ganha entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an earns association from the database.
         *
         * @param id Key of the earns association to be read
         * @return The earns association with the given key
         */
        @Override
        public Ganha read(GanhaId id) {
            return context.em.find(Ganha.class, id);
        }

        /**
         * Updates an earns association in the database.
         *
         * @param entity earns association to be updated
         * @return The key of the updated earns association
         */
        @Override
        public GanhaId update(Ganha entity) {
            context.beginTransaction();
            Ganha earns = context.em.find(Ganha.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (earns == null) {
                throw new EntityNotFoundException("Earns with id " + entity.getId() + " not found");
            }
            earns.setId(entity.getId());
            earns.setBadge(entity.getBadge());
            earns.setIdPlayer(entity.getIdPlayer());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes an earns association from the database.
         *
         * @param id Key of the earns association to be deleted
         */
        @Override
        public void delete(GanhaId id) {
            context.beginTransaction();
            Ganha earns = context.em.find(Ganha.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (earns == null) {
                throw new EntityNotFoundException("Earns with id " + id + " not found");
            }
            context.em.remove(earns);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Friend} association.
     */
    protected class FriendDataMapper implements logic.mappers.associations.friend.FriendMapper {

        /**
         * Creates a new friend association in the database.
         *
         * @param entity friend association to be created
         * @return The key of the created friend association
         */
        @Override
        public AmigoId create(Amigo entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a friend association from the database.
         *
         * @param id Key of the friend association to be read
         * @return The friend association with the given key
         */
        @Override
        public Amigo read(AmigoId id) {
            return context.em.find(Amigo.class, id);
        }

        /**
         * Updates a friend association in the database.
         *
         * @param entity friend association to be updated
         * @return The key of the updated friend association
         */
        @Override
        public AmigoId update(Amigo entity) {
            context.beginTransaction();
            Amigo friend = context.em.find(Amigo.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (friend == null) {
                throw new EntityNotFoundException("Friend with id " + entity.getId() + " not found");
            }
            friend.setId(entity.getId());
            friend.setIdPlayer1(entity.getIdPlayer1());
            friend.setIdPlayer2(entity.getIdPlayer2());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a friend association from the database.
         *
         * @param id Key of the friend association to be deleted
         */
        @Override
        public void delete(AmigoId id) {
            context.beginTransaction();
            Amigo friend = context.em.find(Amigo.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (friend == null) {
                throw new EntityNotFoundException("Friend with id " + id + " not found");
            }
            context.em.remove(friend);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Participates} association.
     */
    protected class ParticipatesDataMapper implements logic.mappers.associations.participates.ParticipatesMapper {

        /**
         * Creates a new participates association in the database.
         *
         * @param entity participates association to be created
         * @return The key of the created participates association
         */
        @Override
        public ParticipaId create(Participa entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a participates association from the database.
         *
         * @param id Key of the participates association to be read
         * @return The participates association with the given key
         */
        @Override
        public Participa read(ParticipaId id) {
            return context.em.find(Participa.class, id);
        }

        /**
         * Updates a participates association in the database.
         *
         * @param entity participates association to be updated
         * @return The key of the updated participates association
         */
        @Override
        public ParticipaId update(Participa entity) {
            context.beginTransaction();
            Participa participates = context.em.find(Participa.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (participates == null) {
                throw new EntityNotFoundException("Participates with id " + entity.getId() + " not found");
            }
            participates.setId(entity.getId());
            participates.setIdPlayer(entity.getIdPlayer());
            participates.setIdChat(entity.getIdChat());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a participates association from the database.
         *
         * @param id Key of the participates association to be deleted
         */
        @Override
        public void delete(ParticipaId id) {
            context.beginTransaction();
            Participa participates = context.em.find(Participa.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (participates == null) {
                throw new EntityNotFoundException("Participates with id " + id + " not found");
            }
            context.em.remove(participates);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Plays} association.
     */
    protected class PlaysDataMapper implements logic.mappers.associations.plays.PlaysMapper {

        /**
         * Creates a new plays association in the database.
         *
         * @param entity plays association to be created
         * @return The key of the created plays association
         */
        @Override
        public JogaId create(Joga entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a plays association from the database.
         *
         * @param id Key of the plays association to be read
         * @return The plays association with the given key
         */
        @Override
        public Joga read(JogaId id) {
            return context.em.find(Joga.class, id);
        }

        /**
         * Updates a plays association in the database.
         *
         * @param entity plays association to be updated
         * @return The key of the updated plays association
         */
        @Override
        public JogaId update(Joga entity) {
            context.beginTransaction();
            Joga plays = context.em.find(Joga.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (plays == null) {
                throw new EntityNotFoundException("Plays with id " + entity.getId() + " not found");
            }
            plays.setId(entity.getId());
            plays.setIdPlayer(entity.getIdPlayer());
            plays.setMatch(entity.getMatch());
            plays.setPoints(entity.getPoints());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a plays association from the database.
         *
         * @param id Key of the plays association to be deleted
         */
        @Override
        public void delete(JogaId id) {
            context.beginTransaction();
            Joga plays = context.em.find(Joga.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (plays == null) {
                throw new EntityNotFoundException("Plays with id " + id + " not found");
            }
            context.em.remove(plays);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Purchase} association.
     */
    protected class PurchaseDataMapper implements logic.mappers.associations.purchase.PurchaseMapper {

        /**
         * Creates a new purchase association in the database.
         *
         * @param entity purchase association to be created
         * @return The key of the created purchase association
         */
        @Override
        public CompraId create(Compra entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads a purchase association from the database.
         *
         * @param id Key of the purchase association to be read
         * @return The purchase association with the given key
         */
        @Override
        public Compra read(CompraId id) {
            return context.em.find(Compra.class, id);
        }

        /**
         * Updates a purchase association in the database.
         *
         * @param entity purchase association to be updated
         * @return The key of the updated purchase association
         */
        @Override
        public CompraId update(Compra entity) {
            context.beginTransaction();
            Compra purchase = context.em.find(Compra.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (purchase == null) {
                throw new EntityNotFoundException("Purchase with id " + entity.getId() + " not found");
            }
            purchase.setId(entity.getId());
            purchase.setIdPlayer(entity.getIdPlayer());
            purchase.setIdGame(entity.getIdGame());
            purchase.setDate(entity.getDate());
            purchase.setPrice(entity.getPrice());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes a purchase association from the database.
         *
         * @param id Key of the purchase association to be deleted
         */
        @Override
        public void delete(CompraId id) {
            context.beginTransaction();
            Compra purchase = context.em.find(Compra.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (purchase == null) {
                throw new EntityNotFoundException("Purchase with id " + id + " not found");
            }
            context.em.remove(purchase);
            context.commit();
        }
    }
}
