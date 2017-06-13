/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production.io;

import br.com.ceciliaprado.cmp.control.dao.production.ModelPhaseDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseDAO;
import br.com.ceciliaprado.cmp.control.dao.production.PhaseProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.dao.production.ProductionOrderDAO;
import br.com.ceciliaprado.cmp.control.model.production.io.PhaseProductionOrdersReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
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
