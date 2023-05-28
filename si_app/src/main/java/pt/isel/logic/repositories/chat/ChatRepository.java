package pt.isel.logic.repositories.chat;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.chat.Chat;

import java.util.List;

/**
 * {@link Chat} repository interface.
 */
public interface ChatRepository extends Repository<Chat, List<Chat>, Integer> {
}
