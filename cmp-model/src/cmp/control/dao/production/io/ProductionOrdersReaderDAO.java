/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production.io;

import cmp.control.dao.production.ModelDAO;
import cmp.control.dao.production.ProductionOrderDAO;
import cmp.control.model.production.io.ProductionOrdersReader;
import cmp.exceptions.IOException;
import cmp.model.production.Model;
import cmp.model.production.ProductionOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
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

    @Override
    public Iterator<ProductionOrder> iterator() {
        return registeredProductionOrders.iterator();
    }
    
}
