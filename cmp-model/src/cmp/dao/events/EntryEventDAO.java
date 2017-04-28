/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.model.events.EntryEvent;
import cmp.model.personal.Sector;
import cmp.model.personal.Supervisor;
import cmp.model.production.ProductionStates;
import cmp.production.reports.filters.EntryEventsList;
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
    
    public EntryEventsList<EntryEvent> findEntryEventsThatCanBeRestarted() {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                + "AND ee.productionState != " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList<EntryEvent> findEntryEventsThatCanBePaused() {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                + "AND ee.productionState = " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList<EntryEvent> findEntryEventsThatCanBeRestarted(Supervisor supervisor) {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee JOIN ee.supervisor sup "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                + "AND sup.name = '" + supervisor.getName() + "' "
                + "AND ee.productionState != " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList<EntryEvent> findEntryEventsThatCanBePaused(Supervisor supervisor) {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee JOIN ee.supervisor sup "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                + "AND sup.name = '" + supervisor.getName() + "' "
                + "AND ee.productionState = " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList<EntryEvent> findEntryEventsThatCanBeRestarted(Sector sector) {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee JOIN ee.sector sec "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                + "AND sec.name = '" + sector.getName() + "' "
                + "AND ee.productionState != " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
    public EntryEventsList<EntryEvent> findEntryEventsThatCanBePaused(Sector sector) {
        return new EntryEventsList(em.createQuery("SELECT ee "
                + "FROM EntryEvent ee JOIN ee.sector sec "
                + "WHERE ee.phaseProductionOrder.pendent = TRUE "
                + "AND sec.name = '" + sector.getName() + "' "
                + "AND ee.productionState = " + ProductionStates.PAUSED.ordinal()).getResultList());
    }
    
}
