/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.order.io;

import tech.adrianohrl.stile.control.dao.production.ModelPhaseDAO;
import tech.adrianohrl.stile.control.dao.production.PhaseDAO;
import tech.adrianohrl.stile.control.dao.order.PhaseProductionOrderDAO;
import tech.adrianohrl.stile.control.dao.order.ProductionOrderDAO;
import tech.adrianohrl.stile.control.model.production.io.PhaseProductionOrdersReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPhase;
import tech.adrianohrl.stile.model.order.PhaseProductionOrder;
import tech.adrianohrl.stile.model.order.ProductionOrder;
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
