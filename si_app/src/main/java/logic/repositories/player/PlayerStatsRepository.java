package logic.repositories.player;

import logic.repositories.Repository;
import model.entities.player.PlayerStats;

import java.util.List;

/**
 * {@link PlayerStats} repository interface.
 */
public interface PlayerStatsRepository extends Repository<PlayerStats, List<PlayerStats>, Integer> {
}
