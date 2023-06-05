package logic.repositories.associations.purchase;

import logic.repositories.Repository;
import model.associacions.purchase.Compra;
import model.associacions.purchase.CompraId;
import model.associacions.purchase.Purchase;

import java.util.List;

/**
 * {@link Purchase} mapper interface to be used by the data mappers.
 */
public interface PurchaseRepository extends Repository<Compra, List<Compra>, CompraId> {
}
