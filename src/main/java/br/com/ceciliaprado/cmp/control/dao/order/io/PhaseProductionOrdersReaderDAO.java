/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.order.io;

import br.com.ceciliaprado.cmp.control.dao.production.ModelPhaseDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.control.dao.order.PhaseProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.dao.order.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.model.production.io.PhaseProductionOrdersReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.order.PhaseProductionOrder;
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
public class PhaseProductionOrdersReaderDAO extends PhaseProductionOrdersReader {
    
    private final EntityManager em;
    private final List<PhaseProductionOrder> registeredPhaseProductionOrders = new ArrayList<>();

    public PhaseProductionOrdersReaderDAO(EntityManager em) {
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
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        for (PhaseProductionOrder phaseProductionOrder : getReadEntities()) {
            if (!phaseProductionOrderDAO.isRegistered(phaseProductionOrder)) {
                phaseProductionOrderDAO.create(phaseProductionOrder);
                registeredPhaseProductionOrders.add(phaseProductionOrder);
            }
        }
    }

    @Override
    protected ModelPhase getPhase(String phaseName, Model model) {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        ModelPhaseDAO modelPhaseDAO = new ModelPhaseDAO(em);
        return modelPhaseDAO.find(model, phaseDAO.find(phaseName));
    }

    @Override
    protected ProductionOrder getProductionOrder(String orderReference) {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        return productionOrderDAO.find(orderReference);
    }

    public List<PhaseProductionOrder> getRegisteredPhaseProductionOrders() {
        return registeredPhaseProductionOrders;
    }

    @Override
    public Iterator<PhaseProductionOrder> iterator() {
        return registeredPhaseProductionOrders.iterator();
    }
    
}
