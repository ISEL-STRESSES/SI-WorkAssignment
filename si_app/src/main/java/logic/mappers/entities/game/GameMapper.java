package logic.mappers.entities.game;

import logic.mappers.DataMapper;
import model.entities.game.Game;
import model.entities.game.Jogo;
import model.types.Alphanumeric;

/**
 * {@link Game} mapper interface to be used by the data mappers.
 */
public interface GameMapper extends DataMapper<Jogo, Alphanumeric> {
}
