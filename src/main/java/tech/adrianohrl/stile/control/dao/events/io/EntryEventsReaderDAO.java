package tech.adrianohrl.stile.control.dao.events.io;

import tech.adrianohrl.stile.control.dao.events.CasualtyDAO;
import tech.adrianohrl.stile.control.dao.events.CasualtyEntryEventDAO;
import tech.adrianohrl.stile.control.dao.events.EntryEventDAO;
import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.control.dao.personnel.SubordinateDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.control.dao.production.ModelDAO;
import tech.adrianohrl.stile.control.dao.production.ModelPhaseDAO;
import tech.adrianohrl.stile.control.dao.order.PhaseProductionOrderDAO;
import tech.adrianohrl.stile.control.dao.order.ProductionOrderDAO;
import tech.adrianohrl.stile.model.events.io.EntryEventsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.exceptions.ProductionException;
import tech.adrianohrl.stile.model.events.Casualty;
import tech.adrianohrl.stile.model.events.CasualtyEntryEvent;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.model.personnel.Supervisor;
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
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class EntryEventsReaderDAO extends EntryEventsReader {
    
    private final EntityManager em;
    private final List<EntryEvent> registeredEvents = new ArrayList<>();

    public EntryEventsReaderDAO(EntityManager em) {
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
        EntryEventDAO entryEventDAO = new EntryEventDAO(em);
        CasualtyEntryEventDAO casualtyEntryEventDAO = new CasualtyEntryEventDAO(em);
        for (EntryEvent event : getReadEntities()) {
            if (!entryEventDAO.isRegistered(event)) {
                if (event instanceof CasualtyEntryEvent) {
                    casualtyEntryEventDAO.create((CasualtyEntryEvent) event);
                } else if (event instanceof EntryEvent) {
                    entryEventDAO.create(event);
                } 
                registeredEvents.add(event);
            }
        }
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
    protected ModelPhase createPhase(String phaseName, double expectedDuration, Model model, Sector sector) throws IOException {
        ModelPhaseDAO phaseDAO = new ModelPhaseDAO(em);
        ModelPhase phase = phaseDAO.find(model.getName(), phaseName);
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
    protected PhaseProductionOrder createPhaseProductionOrder(ModelPhase phase, ProductionOrder productionOrder, int totalQuantity) throws ProductionException, IOException {
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        PhaseProductionOrder phaseProductionOrder = phaseProductionOrderDAO.find(phase.getPhase().getName(), productionOrder.getReference());
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

    public List<EntryEvent> getRegisteredEvents() {
        return registeredEvents;
    }

    @Override
    public Iterator<EntryEvent> iterator() {
        return registeredEvents.iterator();
    }
    
}
