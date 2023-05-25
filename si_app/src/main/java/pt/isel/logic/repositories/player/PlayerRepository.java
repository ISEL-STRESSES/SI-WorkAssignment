package pt.isel.logic.repositories.player;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.player.Player;
import pt.isel.model.types.Email;

import java.util.List;

public interface PlayerRepository extends Repository<Player, List<Player>, Integer> {

    Player findByUsername(String username);

    Player findByEmail(Email email);
}
