package pt.isel.dataAccess;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import pt.isel.logic.mappers.game.GameMapper;
import pt.isel.logic.mappers.game.GameStatsMapper;
import pt.isel.logic.mappers.game.match.MatchMapper;
import pt.isel.logic.mappers.player.PlayerMapper;
import pt.isel.logic.mappers.player.PlayerStatsMapper;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.GameStats;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.PartidaId;
import pt.isel.model.entities.player.Player;
import pt.isel.model.entities.player.PlayerStats;
import pt.isel.model.types.Alphanumeric;

public class DataMappers {

    private final JPAContext context;

    public DataMappers(JPAContext context) {
        this.context = context;
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
            player.setRegionName(entity.getRegionName());
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
}
