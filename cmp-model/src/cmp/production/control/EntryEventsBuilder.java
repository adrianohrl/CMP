/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.control;

import cmp.exceptions.ProductionStateMachineException;
import cmp.model.events.AbstractEvent;
import cmp.model.events.Casualty;
import cmp.model.events.CasualtyEntryEvent;
import cmp.model.events.EntryEvent;
import cmp.model.personal.Sector;
import cmp.model.personal.Subordinate;
import cmp.model.personal.Supervisor;
import cmp.model.production.PhaseProductionOrder;
import cmp.exceptions.ProductionException;
import cmp.model.production.ProductionStates;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsBuilder {
    
    private final Sector sector;
    private final Supervisor supervisor;
    private final ArrayList<AbstractEvent> entryEvents = new ArrayList<>();

    public EntryEventsBuilder(Sector sector, Supervisor supervisor) {
        this.sector = sector;
        this.supervisor = supervisor;
    }
    
    /**
     * This method must be used when the new state of the input phase production order is STARTED.
     * @param phaseProductionOrder
     * @param subordinate
     * @param eventDate
     * @param observation
     * @throws ProductionException 
     */
    public void buildEntryEvent(PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, Calendar eventDate, String observation) throws ProductionException {
        if (phaseProductionOrder.getProductionState() != null) {
            throw new ProductionException("This method must be used when the input phase production order has not STARTED yet.");
        }
        buildEntryEvent(new EntryEvent(sector, supervisor, phaseProductionOrder, subordinate, ProductionStates.STARTED, eventDate, observation));        
    }
    
    /**
     * This method must be used when the new state of the input phase production order is RESTARTED, or FINISHED.
     * @param phaseProductionOrder
     * @param subordinate
     * @param productionState
     * @param eventDate
     * @param observation
     * @throws ProductionException 
     */
    public void buildEntryEvent(PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, ProductionStates productionState, Calendar eventDate, String observation) throws ProductionException {
        if (phaseProductionOrder.getProductionState() == null) {
            throw new ProductionException("This method must not be used when the input phase production order has not STARTED yet.");
        }
        if (!productionState.isRestarted() && !productionState.isFinished()) {
            throw new ProductionException("This method must be used when the new state of the input phase production order is RESTARTED, or FINISHED.");
        }
        buildEntryEvent(new EntryEvent(sector, supervisor, phaseProductionOrder, subordinate, productionState, eventDate, observation));        
    }
    
    /**
     * This method must be used when the new state of the input phase production order is PAUSED, or RETURNED.
     * @param phaseProductionOrder
     * @param subordinate
     * @param productionState
     * @param producedQuantity
     * @param eventDate
     * @param observation
     * @param casualty
     * @throws ProductionException 
     */
    public void buildEntryEvent(PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, ProductionStates productionState, int producedQuantity, Calendar eventDate, String observation, Casualty casualty) throws ProductionException {
        if (phaseProductionOrder.getProductionState() == null) {
            throw new ProductionException("This method must not be used when the input phase production order has not STARTED yet.");
        }
        if (!productionState.isPaused() && !productionState.isReturned()) {
            throw new ProductionException("This method must be used when the new state of the input phase production order is PAUSED, or RETURNED.");
        }
        buildEntryEvent(new CasualtyEntryEvent(casualty, sector, supervisor, phaseProductionOrder, subordinate, productionState, producedQuantity, eventDate, observation));
    }
    
    private void buildEntryEvent(EntryEvent entryEvent) throws ProductionException {
        try {
            processStateTransition(entryEvent);
            entryEvents.add(entryEvent);
        } catch (ProductionStateMachineException e) {
            throw new ProductionException("Production state transition: " + e.getMessage());
        }
    }
    
    private void buildEntryEvent(CasualtyEntryEvent entryEvent) throws ProductionException {
        try {
            processStateTransition(entryEvent);
            entryEvents.add(entryEvent);
        } catch (ProductionStateMachineException e) {
            throw new ProductionException("Production state transition: " + e.getMessage());
        }
    }
    
    private void processStateTransition(EntryEvent entryEvent) throws ProductionStateMachineException {
        ProductionStateMachineController controller = new ProductionStateMachineController(entryEvent.getPhaseProductionOrder());
        controller.process(entryEvent);
    }
    
    private void processStateTransition(CasualtyEntryEvent entryEvent) throws ProductionStateMachineException {
        ProductionStateMachineController controller = new ProductionStateMachineController(entryEvent.getPhaseProductionOrder());
        controller.process(entryEvent);
    }

    public ArrayList<AbstractEvent> getEntryEvents() {
        return entryEvents;
    }
    
}
