package pt.isel.logic.repositories.game;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.game.Game;
import pt.isel.model.types.Alphanumeric;

import java.util.List;

public interface GameRepository extends Repository<Game, List<Game>, Alphanumeric> {
}
