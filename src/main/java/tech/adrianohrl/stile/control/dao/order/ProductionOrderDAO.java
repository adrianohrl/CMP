package tech.adrianohrl.stile.control.dao.order;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.order.ProductionOrder;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ProductionOrderDAO extends DAO<ProductionOrder, String> {

    public ProductionOrderDAO(EntityManager em) {
        super(em, ProductionOrder.class);
    }

    @Override
    public boolean isRegistered(ProductionOrder entity) {
        return super.find(entity.getReference()) != null;
    }
    
}
