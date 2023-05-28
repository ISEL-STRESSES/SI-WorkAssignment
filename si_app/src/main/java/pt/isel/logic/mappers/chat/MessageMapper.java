package pt.isel.logic.mappers.chat;

import pt.isel.logic.mappers.DataMapper;
import pt.isel.model.entities.chat.MensagemId;
import pt.isel.model.entities.chat.Message;

/**
 * Interface to be implemented by the data mappers that perform operations on the {@link Message} entity type.
 */
public interface MessageMapper extends DataMapper<Message, MensagemId> {
}
