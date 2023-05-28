package pt.isel.logic.mappers.chat;

import pt.isel.logic.mappers.DataMapper;
import pt.isel.model.entities.chat.Chat;
import pt.isel.model.entities.chat.Message;

/**
 * {@link Message} mapper interface to be used by the data mappers.
 * TODO: Check the type of the primary key (Integer, Integer)
 */
public interface MessageMapper extends DataMapper<Message, String> {
}
