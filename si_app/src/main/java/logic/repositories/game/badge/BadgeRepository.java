package logic.repositories.game.badge;

import logic.repositories.Repository;
import model.entities.game.badge.Badge;
import model.entities.game.badge.CrachaId;

import java.util.List;

/**
 * {@link Badge} repository interface.
 */
public interface BadgeRepository extends Repository<Badge, List<Badge>, CrachaId> {
}
