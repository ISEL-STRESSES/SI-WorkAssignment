package logic.mappers.entities.game;

import logic.mappers.DataMapper;
import model.entities.game.GameStats;
import model.entities.game.JogoEstatistica;
import model.types.Alphanumeric;

/**
 * {@link GameStats} mapper interface to be used by the data mappers.
 */
public interface GameStatsMapper extends DataMapper<JogoEstatistica, Alphanumeric> {
}
