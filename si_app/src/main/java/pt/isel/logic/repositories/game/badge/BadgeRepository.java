package pt.isel.logic.repositories.game.badge;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.badge.CrachaId;

import java.util.List;

/**
 * {@link Badge} repository interface.
 */
public interface BadgeRepository extends Repository<Badge, List<Badge>, CrachaId> {
}
