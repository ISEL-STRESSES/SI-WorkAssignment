package logic.mappers.entities.chat;

import logic.mappers.DataMapper;
import model.entities.chat.Mensagem;
import model.entities.chat.MensagemId;
import model.entities.chat.Message;

/**
 * {@link Message} mapper interface to be used by the data mappers.
 */
public interface MessageMapper extends DataMapper<Mensagem, MensagemId> {
}
