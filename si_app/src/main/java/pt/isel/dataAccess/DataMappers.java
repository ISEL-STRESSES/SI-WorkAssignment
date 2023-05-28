package pt.isel.dataAccess;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import pt.isel.logic.mappers.chat.ChatMapper;
import pt.isel.logic.mappers.chat.MessageMapper;
import pt.isel.logic.mappers.game.GameMapper;
import pt.isel.logic.mappers.game.GameStatsMapper;
import pt.isel.logic.mappers.game.badge.BadgeMapper;
import pt.isel.logic.mappers.game.match.MatchMapper;
import pt.isel.logic.mappers.game.match.MultiplayerMatchMapper;
import pt.isel.logic.mappers.game.match.NormalMatchMapper;
import pt.isel.logic.mappers.player.PlayerMapper;
import pt.isel.logic.mappers.player.PlayerStatsMapper;
import pt.isel.logic.mappers.region.RegionMapper;
import pt.isel.model.entities.chat.Chat;
import pt.isel.model.entities.chat.MensagemId;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.GameStats;
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.badge.CrachaId;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.PartidaId;
import pt.isel.model.entities.game.matches.multiplayer.MultiPlayerMatch;
import pt.isel.model.entities.game.matches.multiplayer.PartidaMultijogadorId;
import pt.isel.model.entities.game.matches.normal.NormalMatch;
import pt.isel.model.entities.game.matches.normal.PartidaNormalId;
import pt.isel.model.entities.player.Player;
import pt.isel.model.entities.player.PlayerStats;
import pt.isel.model.entities.region.Region;
import pt.isel.model.types.Alphanumeric;

public class DataMappers {

    private final JPAContext context;

    public DataMappers(JPAContext context) {
        this.context = context;
    }

    protected class RegionDataMapper implements RegionMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public String create(Region entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public Region read(String id) {
            return context.em.find(Region.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public String update(Region entity) {
            context.beginTransaction();
            Region region = context.em.find(Region.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
            if (region == null) {
                throw new EntityNotFoundException("Region with id " + entity.getId() + " not found");
            }
            region.setId(entity.getId());
            //region.setDescricao(entity.getDescricao());
            context.commit();
            return entity.getId();
        }

        /**
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(String id) {
            context.beginTransaction();
            Region region = context.em.find(Region.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (region == null) {
                throw new EntityNotFoundException("Region with id " + id + " not found");
            }
            context.em.remove(region);
            context.commit();
        }
    }

    protected class PlayerDataMapper implements PlayerMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public Integer create(Player entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public Player read(Integer id) {
            return context.em.find(Player.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public Integer update(Player entity) {
            context.beginTransaction();
            Player player = context.em.find(Player.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(Integer id) {
            context.beginTransaction();
            Player player = context.em.find(Player.class, id, LockModeType.PESSIMISTIC_WRITE);
            PlayerStats playerStats = context.em.find(PlayerStats.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (player == null) {
                throw new EntityNotFoundException("Player with id " + id + " not found");
            }
            context.em.remove(playerStats);
            context.em.remove(player);
            context.commit();
        }
    }

    protected class PlayerStatsDataMapper implements PlayerStatsMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public Integer create(PlayerStats entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public PlayerStats read(Integer id) {
            return context.em.find(PlayerStats.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public Integer update(PlayerStats entity) {
            context.beginTransaction();
            PlayerStats playerStats = context.em.find(PlayerStats.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(Integer id) {
            throw new UnsupportedOperationException("Not possible to delete PlayerStats");
        }
    }

    protected class GameDataMapper implements GameMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public Alphanumeric create(Game entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public Game read(Alphanumeric id) {
            return context.em.find(Game.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public Alphanumeric update(Game entity) {
            context.beginTransaction();
            Game game = context.em.find(Game.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(Alphanumeric id) {
            context.beginTransaction();
            Game game = context.em.find(Game.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (game == null) {
                throw new EntityNotFoundException("Game with id " + id + " not found");
            }
            GameStats gameStats = context.em.find(GameStats.class, id, LockModeType.PESSIMISTIC_WRITE);
            context.em.remove(gameStats);
            context.em.remove(game);
            context.commit();
        }
    }

    protected class GameStatsDataMapper implements GameStatsMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public Alphanumeric create(GameStats entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public GameStats read(Alphanumeric id) {
            return context.em.find(GameStats.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public Alphanumeric update(GameStats entity) {
            context.beginTransaction();
            GameStats gameStats = context.em.find(GameStats.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(Alphanumeric id) {
            throw new UnsupportedOperationException("Not possible to delete GameStats");
        }
    }

    protected class MatchDataMapper implements MatchMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public PartidaId create(Match entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public Match read(PartidaId id) {
            return null;
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public PartidaId update(Match entity) {
            return null;
        }

        /**
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(PartidaId id) {

        }
    }

    protected class NormalMatchDataMapper implements NormalMatchMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public PartidaNormalId create(NormalMatch entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public NormalMatch read(PartidaNormalId id) {
            return context.em.find(NormalMatch.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public PartidaNormalId update(NormalMatch entity) {
            context.beginTransaction();
            NormalMatch normalMatch = context.em.find(NormalMatch.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
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

    protected class MultiPlayerMatchDataMapper implements MultiplayerMatchMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public PartidaMultijogadorId create(MultiPlayerMatch entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public MultiPlayerMatch read(PartidaMultijogadorId id) {
            return context.em.find(MultiPlayerMatch.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public PartidaMultijogadorId update(MultiPlayerMatch entity) {
            context.beginTransaction();
            MultiPlayerMatch multiPlayerMatch = context.em.find(MultiPlayerMatch.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(PartidaMultijogadorId id) {
            context.beginTransaction();
            MultiPlayerMatch multiPlayerMatch = context.em.find(MultiPlayerMatch.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (multiPlayerMatch == null) {
                throw new EntityNotFoundException("MultiPlayerMatch with id " + id + " not found");
            }
            context.em.remove(multiPlayerMatch);
            context.commit();
        }
    }

    protected class BadgeDataMapper implements BadgeMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public CrachaId create(Badge entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public Badge read(CrachaId id) {
            return context.em.find(Badge.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public CrachaId update(Badge entity) {
            context.beginTransaction();
            Badge badge = context.em.find(Badge.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(CrachaId id) {
            context.beginTransaction();
            Badge badge = context.em.find(Badge.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (badge == null) {
                throw new EntityNotFoundException("Badge with id " + id + " not found");
            }
            context.em.remove(badge);
            context.commit();
        }
    }

    protected class ChatDataMapper implements ChatMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public Integer create(Chat entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public Chat read(Integer id) {
            return context.em.find(Chat.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public Integer update(Chat entity) {
            context.beginTransaction();
            Chat chat = context.em.find(Chat.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(Integer id) {
            context.beginTransaction();
            Chat chat = context.em.find(Chat.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (chat == null) {
                throw new EntityNotFoundException("Chat with id " + id + " not found");
            }
            context.em.remove(chat);
            context.commit();
        }
    }

    protected class MessageDataMapper implements MessageMapper {

        /**
         * Creates a new entity in the database.
         *
         * @param entity Entity to be created
         * @return The key of the created entity
         */
        @Override
        public MensagemId create(Message entity) {
            context.beginTransaction();
            context.em.persist(entity);
            context.commit();
            return entity.getId();
        }

        /**
         * Reads an entity from the database.
         *
         * @param id Key of the entity to be read
         * @return The entity with the given key
         */
        @Override
        public Message read(MensagemId id) {
            return context.em.find(Message.class, id);
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public MensagemId update(Message entity) {
            context.beginTransaction();
            Message message = context.em.find(Message.class, entity.getId(), LockModeType.PESSIMISTIC_WRITE);
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
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(MensagemId id) {
            context.beginTransaction();
            Message message = context.em.find(Message.class, id, LockModeType.PESSIMISTIC_WRITE);
            if (message == null) {
                throw new EntityNotFoundException("Message with id " + id + " not found");
            }
            context.em.remove(message);
            context.commit();
        }
    }
}
