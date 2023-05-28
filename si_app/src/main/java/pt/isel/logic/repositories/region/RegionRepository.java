package pt.isel.logic.repositories.region;

import pt.isel.logic.repositories.Repository;
import pt.isel.model.entities.chat.Message;
import pt.isel.model.entities.region.Region;

import java.util.List;

// check if it's possible to use the Region interface or needs to be the Region implementation class
/**
 * {@link Region} repository interface.
 */
public interface RegionRepository extends Repository<Region, List<Region>, String> {
}
