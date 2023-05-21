package pt.isel.dataAccess;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import pt.isel.logic.mappers.game.GameMapper;
import pt.isel.logic.mappers.game.GameStatsMapper;
import pt.isel.logic.mappers.game.match.MatchMapper;
import pt.isel.model.entities.game.Game;
import pt.isel.model.entities.game.GameStats;
import pt.isel.model.entities.game.matches.Match;
import pt.isel.model.entities.game.matches.MatchId;
import pt.isel.model.entities.player.Player;
import pt.isel.model.entities.player.PlayerStats;
import pt.isel.model.types.Alphanumeric;

public class DataMappers {

    private final JPAContext context;

    public DataMappers(JPAContext context) {
        this.context = context;
    }

    protected class PlayerDataMapper implements pt.isel.logic.mappers.player.PlayerMapper {

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
            player.setEstado(entity.getEstado());
            player.setNomeRegiao(entity.getNomeRegiao());
            player.setJogadorEstatistica(entity.getJogadorEstatistica());
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

    protected class PlayerStatsDataMapper implements pt.isel.logic.mappers.player.PlayerStatsMapper {

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
            return entity.getPlayerId();
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
            PlayerStats playerStats = context.em.find(PlayerStats.class, entity.getPlayerId(), LockModeType.PESSIMISTIC_WRITE);
            if (playerStats == null) {
                throw new EntityNotFoundException("PlayerStats with id " + entity.getPlayerId() + " not found");
            }
            playerStats.setPlayerId(entity.getPlayerId());
            playerStats.setNrJogos(entity.getNrJogos());
            playerStats.setNrPartidas(entity.getNrPartidas());
            playerStats.setTotalPontos(entity.getTotalPontos());
            context.commit();
            return entity.getPlayerId();
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
            game.setNome(entity.getNome());
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
            return entity.getGameId();
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
            GameStats gameStats = context.em.find(GameStats.class, entity.getGameId(), LockModeType.PESSIMISTIC_WRITE);
            if (gameStats == null) {
                throw new EntityNotFoundException("GameStats with id " + entity.getGameId() + " not found");
            }
            gameStats.setId(entity.getGameId());
            gameStats.setGame(entity.getGame());
            gameStats.setNrPartidas(entity.getNrPartidas());
            gameStats.setTotalPontos(entity.getTotalPontos());
            gameStats.setNrJogadores(entity.getNrJogadores());
            context.commit();
            return entity.getGameId();
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
        public MatchId create(Match entity) {
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
        public Match read(MatchId id) {
            return null;
        }

        /**
         * Updates an entity in the database.
         *
         * @param entity Entity to be updated
         * @return The key of the updated entity
         */
        @Override
        public MatchId update(Match entity) {
            return null;
        }

        /**
         * Deletes an entity from the database.
         *
         * @param id Key of the entity to be deleted
         */
        @Override
        public void delete(MatchId id) {

        }
    }
}
