package pt.isel.logic.repositories.player;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.player.PlayerStats;

import java.util.List;

/**
 * {@link PlayerStats} repository interface.
 */
public interface PlayerStatsRepository extends Repository<PlayerStats, List<PlayerStats>, Integer> {
}
