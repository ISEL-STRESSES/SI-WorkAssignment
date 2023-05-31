package logic.repositories.chat;

import logic.repositories.Repository;
import model.entities.chat.Chat;

import java.util.List;

/**
 * {@link Chat} repository interface.
 */
public interface ChatRepository extends Repository<Chat, List<Chat>, Integer> {
}
