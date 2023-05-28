package pt.isel.logic.repositories.game.badge;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.badge.Badge;
import pt.isel.model.entities.game.badge.CrachaId;

import java.util.List;

/**
 * Interface to access the badges repository
 */
public interface BadgeRepository extends Repository<Badge, List<Badge>, CrachaId> {
}
