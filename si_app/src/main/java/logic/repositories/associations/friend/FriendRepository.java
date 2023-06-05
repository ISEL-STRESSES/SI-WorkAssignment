package logic.repositories.associations.friend;

import logic.repositories.Repository;
import model.associacions.friend.Amigo;
import model.associacions.friend.AmigoId;

import java.util.List;

public interface FriendRepository extends Repository<Amigo, List<Amigo>, AmigoId> {
}
