package tech.adrianohrl.stile.control.dao.order;

import tech.adrianohrl.stile.model.order.ProductionOrder;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ArchivableDAO;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ProductionOrderDAO extends ArchivableDAO<ProductionOrder, String> {

    public ProductionOrderDAO(EntityManager em) {
        super(em, ProductionOrder.class);
    }

    @Override
    public boolean isRegistered(ProductionOrder entity) {
        return super.find(entity.getReference()) != null;
    }
    
}
