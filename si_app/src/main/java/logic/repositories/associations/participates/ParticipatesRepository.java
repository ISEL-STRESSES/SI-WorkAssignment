package logic.repositories.associations.participates;

import logic.repositories.Repository;
import model.associacions.participates.Participa;
import model.associacions.participates.ParticipaId;
import model.associacions.participates.Participates;

import java.util.List;

/**
 * {@link Participates} mapper interface to be used by the data mappers.
 */
public interface ParticipatesRepository extends Repository<Participa, List<Participa>, ParticipaId> {
}
