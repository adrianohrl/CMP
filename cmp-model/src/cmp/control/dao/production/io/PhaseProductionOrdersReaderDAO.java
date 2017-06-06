/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production.io;

import cmp.control.dao.production.ModelPhaseDAO;
import cmp.control.dao.production.PhaseDAO;
import cmp.control.dao.production.PhaseProductionOrderDAO;
import cmp.control.dao.production.ProductionOrderDAO;
import cmp.control.model.production.io.PhaseProductionOrdersReader;
import cmp.exceptions.IOException;
import cmp.model.production.Model;
import cmp.model.production.ModelPhase;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionOrder;
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

    @Override
    public Iterator<PhaseProductionOrder> iterator() {
        return registeredPhaseProductionOrders.iterator();
    }
    
}
