/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.order.io;

import br.com.ceciliaprado.cmp.control.dao.production.ModelDAO;
import br.com.ceciliaprado.cmp.control.dao.order.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.model.production.io.ProductionOrdersReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.order.ProductionOrder;
import java.io.InputStream;
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