/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.events;

import cmp.model.personal.Sector;
import cmp.model.personal.Subordinate;
import cmp.model.personal.Supervisor;
import cmp.model.production.PhaseProductionOrder;
import cmp.exceptions.ProductionException;
import cmp.model.production.ProductionStates;
import java.util.Calendar;

/**
 *
 * @author adrianohrl
 */
public class EntryEvent extends AbstractEvent {
    
    private Sector sector;
    private Supervisor supervisor;
    private PhaseProductionOrder phaseProductionOrder;
    private Subordinate subordinate;
    private ProductionStates productionState;
    private int producedQuantity = 0;
    
    public EntryEvent() {
    }

    private EntryEvent(Sector sector, Supervisor supervisor, PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, ProductionStates productionState, String observation, Calendar eventDate) throws ProductionException {
        super(eventDate, observation);
        this.sector = sector;
        this.supervisor = supervisor;
        this.phaseProductionOrder = phaseProductionOrder;
        this.subordinate = subordinate;
        if (productionState == phaseProductionOrder.getProductionState()) {
            throw new ProductionException("No production state transition happened!!!");
        }
        this.productionState = productionState;
    }
    
    protected EntryEvent(Sector sector, Supervisor supervisor, PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, ProductionStates productionState, int producedQuantity, String observation, Calendar eventDate) throws ProductionException {
        this(sector, supervisor, phaseProductionOrder, subordinate, productionState, observation, eventDate);
        if (producedQuantity < 0) {
            throw new ProductionException("The produced quantity must not be negative!!!");
        }
        if (producedQuantity > phaseProductionOrder.getTotalQuantity()) {
            throw new ProductionException("The produced quantity must be less the total quantity!!!");
        }
        this.producedQuantity = producedQuantity;
    }
    
    /**
     * This constructor must be used when the phase production order has just STARTED, RESTARTED or FINISHED
     * @param sector
     * @param supervisor
     * @param phaseProductionOrder
     * @param subordinate
     * @param productionState
     * @param eventDate
     * @param observation 
     * @throws cmp.exceptions.ProductionException 
     */
    public EntryEvent(Sector sector, Supervisor supervisor, PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, ProductionStates productionState, Calendar eventDate, String observation) throws ProductionException {
        this(sector, supervisor, phaseProductionOrder, subordinate, productionState, observation, eventDate);
        if (productionState.isFinished()) {
            producedQuantity = phaseProductionOrder.getRemaingQuantity();
        } else if (!productionState.isStarted() && !productionState.isRestarted()) {
            throw new ProductionException("This constructor must only be used when the production state is STARTED, RESTARTED or FINISHED!!!");    
        }
    }

    @Override
    public boolean equals(AbstractEvent event) {
        return event instanceof EntryEvent && equals((EntryEvent) event);
    }
    
    public boolean equals(EntryEvent event) {
        return super.equals(event) && phaseProductionOrder.equals(event.phaseProductionOrder);
    }

    public int compareTo(EntryEvent event) {
        int cmp = phaseProductionOrder.compareTo(event.phaseProductionOrder);
        return cmp == 0 ? super.compareTo(event) : cmp; 
    }

    @Override
    public String toString() {
        return supervisor + " has changed " + subordinate + 
                "'s phase production order (" + phaseProductionOrder + ") at " + sector + " to " + productionState + 
                " produced " + (phaseProductionOrder.getProducedQuantity() + this.producedQuantity) + " [un] of " + 
                (phaseProductionOrder.getTotalQuantity()) + " [un]";
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public PhaseProductionOrder getPhaseProductionOrder() {
        return phaseProductionOrder;
    }

    public void setPhaseProductionOrder(PhaseProductionOrder phaseProductionOrder) {
        this.phaseProductionOrder = phaseProductionOrder;
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(Subordinate subordinate) {
        this.subordinate = subordinate;
    }

    public ProductionStates getProductionState() {
        return productionState;
    }

    public void setProductionState(ProductionStates productionState) {
        this.productionState = productionState;
    }

    public int getProducedQuantity() {
        return producedQuantity;
    }

    public void setProducedQuantity(int producedQuantity) {
        this.producedQuantity = producedQuantity;
    }
    
}
