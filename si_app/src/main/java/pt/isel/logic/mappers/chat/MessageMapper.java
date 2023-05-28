package pt.isel.logic.mappers.chat;

import pt.isel.logic.mappers.DataMapper;
import pt.isel.model.entities.chat.MensagemId;
import pt.isel.model.entities.chat.Message;

/**
 * {@link Message} mapper interface to be used by the data mappers.
 */
public interface MessageMapper extends DataMapper<Message, MensagemId> {
}
