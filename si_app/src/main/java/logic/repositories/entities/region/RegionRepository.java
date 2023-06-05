package logic.repositories.entities.region;

import logic.repositories.Repository;
import model.entities.region.Regiao;
import model.entities.region.Region;

import java.util.List;

/**
 * {@link Region} repository interface.
 */
public interface RegionRepository extends Repository<Regiao, List<Regiao>, String> {
}

