package pt.isel.logic.repositories.game.badge;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.badge.Badge;

import java.util.List;

/**
 * Interface to access the badges repository
 * TODO: Check the type of the primary key (Alphanumeric, String)
 */
public interface BadgeRepository extends Repository<Badge, List<Badge>, String> {
}
