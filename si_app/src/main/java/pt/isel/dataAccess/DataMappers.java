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
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.PartidaId;
import pt.isel.model.entities.player.Player;
import pt.isel.model.entities.player.PlayerStats;
import pt.isel.model.types.Alphanumeric;

/**
 * Class responsible for creating the data mappers.
 */
public class DataMappers {


    private final JPAContext context;

    public DataMappers(JPAContext context) {
        this.context = context;
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
        public Player read(Integer id) {
            return context.em.find(Player.class, id);
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
         * Deletes a player from the database.
         *
         * @param id Key of the player to be deleted
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
        public PlayerStats read(Integer id) {
            return context.em.find(PlayerStats.class, id);
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
        public Game read(Alphanumeric id) {
            return context.em.find(Game.class, id);
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
         * Deletes a game from the database.
         *
         * @param id Key of the game to be deleted
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
        public GameStats read(Alphanumeric id) {
            return context.em.find(GameStats.class, id);
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
}
