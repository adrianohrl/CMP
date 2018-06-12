package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.control.dao.order.PhaseProductionOrderDAO;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import tech.adrianohrl.stile.model.order.ProductionStates;
import tech.adrianohrl.stile.control.model.production.reports.filters.EntryEventsList;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <E>
 */
public class EntryEventDAO<E extends EntryEvent> extends AbstractEmployeeRelatedEventDAO<E> {

    public EntryEventDAO(EntityManager em) {
        super(em, EntryEvent.class);
    }
    
    protected EntryEventDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }

    @Override
    public void create(E entryEvent) throws EntityExistsException {
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        phaseProductionOrderDAO.update(entryEvent.getPhaseProductionOrder());  
        super.create(entryEvent);
    }
    
    public EntryEventsList findEntryEventsThatCanBeRestarted() {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                    + "AND ee.productionState = " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList findEntryEventsThatCanBePaused() {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                    + "AND ee.productionState != " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList findEntryEventsThatCanBeRestarted(Supervisor supervisor) {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee JOIN ee.supervisor sup "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                    + "AND sup.name = '" + supervisor.getName() + "' "
                    + "AND ee.productionState = " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList findEntryEventsThatCanBePaused(Supervisor supervisor) {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee JOIN ee.supervisor sup "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                    + "AND sup.name = '" + supervisor.getName() + "' "
                    + "AND ee.productionState != " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList findEntryEventsThatCanBeRestarted(Sector sector) {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee JOIN ee.sector sec "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                    + "AND sec.name = '" + sector.getName() + "' "
                    + "AND ee.productionState = " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList findEntryEventsThatCanBePaused(Sector sector) {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee JOIN ee.sector sec "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                    + "AND sec.name = '" + sector.getName() + "' "
                    + "AND ee.productionState != " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
}
