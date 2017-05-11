/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events.io;

import cmp.dao.events.CasualtyDAO;
import cmp.dao.personal.SectorDAO;
import cmp.dao.personal.SubordinateDAO;
import cmp.dao.personal.SupervisorDAO;
import cmp.dao.production.ModelDAO;
import cmp.dao.production.PhaseDAO;
import cmp.dao.production.PhaseProductionOrderDAO;
import cmp.dao.production.ProductionOrderDAO;
import cmp.events.io.EntryEventsReader;
import cmp.exceptions.IOException;
import cmp.exceptions.ProductionException;
import cmp.model.events.Casualty;
import cmp.model.personal.Sector;
import cmp.model.personal.Subordinate;
import cmp.model.personal.Supervisor;
import cmp.model.production.Model;
import cmp.model.production.Phase;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionOrder;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsReaderDAO extends EntryEventsReader {
    
    private EntityManager em;

    public EntryEventsReaderDAO(EntityManager em, String fileName) throws IOException {
        super(fileName);
        this.em = em;
    }
    
    @Override
    protected Supervisor createSupervisor(String supervisorName) throws IOException {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        Supervisor supervisor = supervisorDAO.find(supervisorName);
        if (supervisor == null) {
            throw new IOException("The input supervisor (whose name is " + supervisorName + ") is not registered yet!!!");
        }
        return supervisor;
    }    

    @Override
    protected Sector createSector(String sectorName, Supervisor supervisor) throws IOException {
        SectorDAO sectorDAO = new SectorDAO(em);
        Sector sector = sectorDAO.find(sectorName);
        if (sector == null) {
            throw new IOException("The input sector (whose name is " + sectorName + ") is not registered yet!!!");
        }
        if (!sectorDAO.isSectorSupervisor(sectorName, supervisor.getName())) {
            throw new IOException(supervisor.getName() + " is not supervisor of the sector " + sectorName + "!!!");
        }
        return sector;
    }

    @Override
    protected Subordinate createSubordinate(String subordinateName, Supervisor supervisor) throws IOException {
        SubordinateDAO subordinateDAO = new SubordinateDAO(em);
        Subordinate subordinate = subordinateDAO.find(subordinateName);
        if (subordinate == null) {
            throw new IOException("The input subordinate (whose name is " + subordinateName + ") is not registered yet!!!");
        }
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        if (!supervisorDAO.isSupervisorSubordinate(supervisor.getName(), subordinateName)) {
            throw new IOException(subordinateName + " is not subordinate of the supervisor " + supervisor.getName() + "!!!");
        }
        return subordinate;
    }

    @Override
    protected Model createModel(String modelReference) throws IOException {
        ModelDAO modelDAO = new ModelDAO(em);
        Model model = modelDAO.find(modelReference);
        if (model == null) {
            throw new IOException("The input model (whose reference is " + modelReference + ") is not registered yet!!!");
        }
        return model;
    }

    @Override
    protected ProductionOrder createProductionOrder(String productionOrderName, Model model) throws IOException {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        ProductionOrder productionOrder = productionOrderDAO.find(productionOrderName);
        if (productionOrder == null) {
            throw new IOException("The input production order (" + productionOrderName +  ") is not registered yet!!!");
        }
        return productionOrder;
    }

    @Override
    protected Phase createPhase(String phaseName, double expectedDuration, Model model) throws IOException {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        Phase phase = phaseDAO.find(phaseName);
        if (phase == null) {
            throw new IOException("The input phase (whose name is " + phaseName + ") is not registered yet!!!");
        }
        ModelDAO modelDAO = new ModelDAO(em);
        if (!modelDAO.isModelPhase(model.getName(), phaseName)) {
            throw new IOException(phaseName + " is not a phase of model " + model.getName() + "!!!");
        }
        return phase;
    }

    @Override
    protected PhaseProductionOrder createPhaseProductionOrder(Phase phase, ProductionOrder productionOrder, int totalQuantity) throws ProductionException, IOException {
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        PhaseProductionOrder phaseProductionOrder = phaseProductionOrderDAO.find(phase.getName(), productionOrder.getReference());
        if (phaseProductionOrder == null) {
            phaseProductionOrder = new PhaseProductionOrder(phase, productionOrder, totalQuantity);
            phaseProductionOrderDAO.create(phaseProductionOrder);
        }
        return phaseProductionOrder;
    }

    @Override
    protected Casualty createCasualty(String casualtyName) throws IOException {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        Casualty casualty = casualtyDAO.find(casualtyName);
        if (casualty == null) {
            throw new IOException("The input casualty (whose name is " + casualtyName + ") is not registered yet!!!");
        }
        return casualty;
    }
    
}
