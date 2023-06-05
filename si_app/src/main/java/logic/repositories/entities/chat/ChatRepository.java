package logic.repositories.entities.chat;

import logic.repositories.Repository;
import model.entities.chat.Chat;
import model.entities.chat.Conversa;

import java.util.List;

/**
 * {@link Conversa} repository interface.
 */
public interface ChatRepository extends Repository<Conversa, List<Conversa>, Integer> {
    Chat findByName(String name);
}
