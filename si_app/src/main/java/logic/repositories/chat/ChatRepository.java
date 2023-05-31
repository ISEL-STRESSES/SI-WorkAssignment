package logic.repositories.chat;

import logic.repositories.Repository;
import model.entities.chat.Conversa;

import java.util.List;

/**
 * {@link model.entities.chat.Conversa} repository interface.
 */
public interface ChatRepository extends Repository<Conversa, List<Conversa>, Integer> {
}
