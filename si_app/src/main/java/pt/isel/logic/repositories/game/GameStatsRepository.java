package pt.isel.logic.repositories.game;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.GameStats;
import pt.isel.model.types.Alphanumeric;

import java.util.List;

/**
 * {@link GameStats} repository interface.
 */
public interface GameStatsRepository extends Repository<GameStats, List<GameStats>, Alphanumeric> {
}
