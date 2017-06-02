/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.events;

import cmp.control.dao.production.PhaseProductionOrderDAO;
import cmp.model.events.EntryEvent;
import cmp.model.personnel.Sector;
import cmp.model.personnel.Supervisor;
import cmp.model.production.ProductionStates;
import cmp.control.model.production.reports.filters.EntryEventsList;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
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
