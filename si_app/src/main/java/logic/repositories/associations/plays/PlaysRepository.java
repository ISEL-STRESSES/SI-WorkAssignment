package logic.repositories.associations.plays;

import logic.repositories.Repository;
import model.associacions.plays.Joga;
import model.associacions.plays.JogaId;
import model.associacions.plays.Plays;

import java.util.List;

/**
 * {@link Plays} mapper interface to be used by the data mappers.
 */
public interface PlaysRepository extends Repository<Joga, List<Joga>, JogaId> {
}
