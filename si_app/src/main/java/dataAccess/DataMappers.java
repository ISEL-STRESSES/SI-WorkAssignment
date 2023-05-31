package dataAccess;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import logic.mappers.chat.ChatMapper;
import logic.mappers.chat.MessageMapper;
import logic.mappers.game.GameMapper;
import logic.mappers.game.GameStatsMapper;
import logic.mappers.game.badge.BadgeMapper;
import logic.mappers.game.match.MatchMapper;
import logic.mappers.game.match.MultiplayerMatchMapper;
import logic.mappers.game.match.NormalMatchMapper;
import logic.mappers.player.PlayerMapper;
import logic.mappers.player.PlayerStatsMapper;
import logic.mappers.region.RegionMapper;
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
        public String create(Region entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public Region read(String id) {
            return context._em.find(Region.class, id);
        }

        /**
         * Updates a region in the database.
         *
         * @param entity region to be updated
         * @return The key of the updated region
         */
        @Override
        public String update(Region entity) {
            context.beginTransaction();
            Region region = context._em.find(Region.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (region == null) {
                throw new EntityNotFoundException("Region with id " + entity.getId() + " not found");
            }
            region.setId(entity.getId());
            //region.setDescricao(entity.getDescricao());
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
            Region region = context._em.find(Region.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (region == null) {
                throw new EntityNotFoundException("Region with id " + id + " not found");
            }
            context._em.remove(region);
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
        public Integer create(Player entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public Player read(Integer id) {
            return context._em.find(Player.class, id);
        }

        /**
         * Updates a player in the database.
         *
         * @param entity Player to be updated
         * @return The key of the updated player
         */
        @Override
        public Integer update(Player entity) {
            context.beginTransaction();
            Player player = context._em.find(Player.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (player == null) {
                throw new EntityNotFoundException("Player with id " + entity.getId() + " not found");
            }
            // player.setId(entity.getId());
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
            Player player = context._em.find(Player.class, id, LockModeType.PESSIMISTIC_WRITE);
            PlayerStats playerStats = context._em.find(PlayerStats.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (player == null) {
                throw new EntityNotFoundException("Player with id " + id + " not found");
            }
            context._em.remove(playerStats);
            context._em.remove(player);
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
        public Integer create(PlayerStats entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public PlayerStats read(Integer id) {
            return context._em.find(PlayerStats.class, id);
        }

        /**
         * Updates a player stats in the database.
         *
         * @param entity PlayerStats to be updated
         * @return The key of the updated player stats
         */
        @Override
        public Integer update(PlayerStats entity) {
            context.beginTransaction();
            PlayerStats playerStats = context._em.find(PlayerStats.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
        public Alphanumeric create(Game entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public Game read(Alphanumeric id) {
            return context._em.find(Game.class, id);
        }

        /**
         * Updates a game in the database.
         *
         * @param entity game to be updated
         * @return The key of the updated game
         */
        @Override
        public Alphanumeric update(Game entity) {
            context.beginTransaction();
            Game game = context._em.find(Game.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
            Game game = context._em.find(Game.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (game == null) {
                throw new EntityNotFoundException("Game with id " + id + " not found");
            }
            GameStats gameStats = context._em.find(GameStats.class, id, LockModeType.PESSIMISTIC_WRITE);
            context._em.remove(gameStats);
            context._em.remove(game);
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
        public Alphanumeric create(GameStats entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public GameStats read(Alphanumeric id) {
            return context._em.find(GameStats.class, id);
        }

        /**
         * Updates a game stats in the database.
         *
         * @param entity game stats to be updated
         * @return The key of the updated game stats
         */
        @Override
        public Alphanumeric update(GameStats entity) {
            context.beginTransaction();
            GameStats gameStats = context._em.find(GameStats.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
        public PartidaId create(Match entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public Match read(PartidaId id) {
            return null;
        }

        /**
         * Updates a match in the database.
         *
         * @param entity match to be updated
         * @return The key of the updated match
         */
        @Override
        public PartidaId update(Match entity) {
            return null;
        }

        /**
         * Deletes a match from the database.
         *
         * @param id Key of the match to be deleted
         */
        @Override
        public void delete(PartidaId id) {

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
        public PartidaNormalId create(NormalMatch entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public NormalMatch read(PartidaNormalId id) {
            return context._em.find(NormalMatch.class, id);
        }

        /**
         * Updates a normal match in the database.
         *
         * @param entity normal match to be updated
         * @return The key of the updated normal match
         */
        @Override
        public PartidaNormalId update(NormalMatch entity) {
            context.beginTransaction();
            NormalMatch normalMatch = context._em.find(NormalMatch.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (normalMatch == null) {
                throw new EntityNotFoundException("NormalMatch with id " + entity.getId() + " not found");
            }
            normalMatch.setId(entity.getId());
            normalMatch.setMatchNumber(entity.getMatchNumber());
            normalMatch.setGameId(entity.getGameId());
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
            NormalMatch normalMatch = context._em.find(NormalMatch.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (normalMatch == null) {
                throw new EntityNotFoundException("NormalMatch with id " + id + " not found");
            }
            context._em.remove(normalMatch);
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
        public PartidaMultijogadorId create(MultiPlayerMatch entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public MultiPlayerMatch read(PartidaMultijogadorId id) {
            return context._em.find(MultiPlayerMatch.class, id);
        }

        /**
         * Updates a multiplayer match in the database.
         *
         * @param entity multiplayer match to be updated
         * @return The key of the updated multiplayer match
         */
        @Override
        public PartidaMultijogadorId update(MultiPlayerMatch entity) {
            context.beginTransaction();
            MultiPlayerMatch multiPlayerMatch = context._em.find(MultiPlayerMatch.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (multiPlayerMatch == null) {
                throw new EntityNotFoundException("MultiPlayerMatch with id " + entity.getId() + " not found");
            }
            multiPlayerMatch.setId(entity.getId());
            multiPlayerMatch.setMatchNumber(entity.getMatchNumber());
            multiPlayerMatch.setGameId(entity.getGameId());
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
            MultiPlayerMatch multiPlayerMatch = context._em.find(MultiPlayerMatch.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (multiPlayerMatch == null) {
                throw new EntityNotFoundException("MultiPlayerMatch with id " + id + " not found");
            }
            context._em.remove(multiPlayerMatch);
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
        public CrachaId create(Badge entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public Badge read(CrachaId id) {
            return context._em.find(Badge.class, id);
        }

        /**
         * Updates a badge in the database.
         *
         * @param entity badge to be updated
         * @return The key of the updated badge
         */
        @Override
        public CrachaId update(Badge entity) {
            context.beginTransaction();
            Badge badge = context._em.find(Badge.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
            Badge badge = context._em.find(Badge.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (badge == null) {
                throw new EntityNotFoundException("Badge with id " + id + " not found");
            }
            context._em.remove(badge);
            context.commit();
        }
    }

    /**
     * Creates the data mapper for the {@link Chat} entity.
     */
    protected class ChatDataMapper implements ChatMapper {

        /**
         * Creates a new chat in the database.
         *
         * @param entity chat to be created
         * @return The key of the created chat
         */
        @Override
        public Integer create(Chat entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public Chat read(Integer id) {
            return context._em.find(Chat.class, id);
        }

        /**
         * Updates a chat in the database.
         *
         * @param entity chat to be updated
         * @return The key of the updated chat
         */
        @Override
        public Integer update(Chat entity) {
            context.beginTransaction();
            Chat chat = context._em.find(Chat.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
            Chat chat = context._em.find(Chat.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (chat == null) {
                throw new EntityNotFoundException("Chat with id " + id + " not found");
            }
            context._em.remove(chat);
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
        public MensagemId create(Message entity) {
            context.beginTransaction();
            context._em.persist(entity);
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
        public Message read(MensagemId id) {
            return context._em.find(Message.class, id);
        }

        /**
         * Updates a message in the database.
         *
         * @param entity message to be updated
         * @return The key of the updated message
         */
        @Override
        public MensagemId update(Message entity) {
            context.beginTransaction();
            Message message = context._em.find(Message.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
            Message message = context._em.find(Message.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (message == null) {
                throw new EntityNotFoundException("Message with id " + id + " not found");
            }
            context._em.remove(message);
            context.commit();
        }
    }
}
