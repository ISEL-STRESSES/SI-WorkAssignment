package logic.repositories.game;

import logic.repositories.Repository;
import model.entities.game.GameStats;
import model.types.Alphanumeric;

import java.util.List;

/**
 * {@link GameStats} repository interface.
 */
public interface GameStatsRepository extends Repository<GameStats, List<GameStats>, Alphanumeric> {
}
