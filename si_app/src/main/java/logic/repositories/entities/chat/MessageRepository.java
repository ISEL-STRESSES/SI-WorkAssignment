package logic.repositories.entities.chat;

import logic.repositories.Repository;
import model.entities.chat.Mensagem;
import model.entities.chat.MensagemId;
import model.entities.chat.Message;

import java.util.List;

/**
 * {@link Message} repository interface.
 */
public interface MessageRepository extends Repository<Mensagem, List<Mensagem>, MensagemId> {
}
