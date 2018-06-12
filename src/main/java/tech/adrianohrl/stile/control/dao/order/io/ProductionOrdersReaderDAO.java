package tech.adrianohrl.stile.control.dao.order.io;

import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.control.dao.order.ProductionOrderDAO;
import tech.adrianohrl.stile.control.model.production.io.ProductionOrdersReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.order.ProductionOrder;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ProductionOrdersReaderDAO extends ProductionOrdersReader {
    
    private final EntityManager em;
    private final List<ProductionOrder> registeredProductionOrders = new ArrayList<>();

    public ProductionOrdersReaderDAO(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        register();
    }

    @Override
    public void readFile(InputStream in) throws IOException {
        super.readFile(in);
        register();
    }
    
    private void register() {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        for (ProductionOrder productionOrder : getReadEntities()) {
            if (!productionOrderDAO.isRegistered(productionOrder)) {
                productionOrderDAO.create(productionOrder);
                registeredProductionOrders.add(productionOrder);
            }
        }
    }

    @Override
    protected Model getModel(String modelReference) {
        ModelDAO modelDAO = new ModelDAO(em);
        return modelDAO.find(modelReference);
    }

    public List<ProductionOrder> getRegisteredProductionOrders() {
        return registeredProductionOrders;
    }

    @Override
    public Iterator<ProductionOrder> iterator() {
        return registeredProductionOrders.iterator();
    }
    
}
