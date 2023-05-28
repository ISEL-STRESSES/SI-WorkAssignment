package pt.isel.logic.repositories.game.badge;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.game.badge.Badge;

import java.util.List;

/**
 * {@link Badge} repository interface.
 * TODO: Check the type of the primary key (Alphanumeric, String)
 */
public interface BadgeRepository extends Repository<Badge, List<Badge>, String> {
}
