package pt.isel.logic.repositories.chat;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.chat.MensagemId;
import pt.isel.model.entities.chat.Message;

import java.util.List;

public interface MessageRepository extends Repository<Message, List<Message>, MensagemId> {
}
