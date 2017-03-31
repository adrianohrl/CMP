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
import cmp.model.production.ProductionException;
import cmp.model.production.ProductionStates;
import java.util.Calendar;

/**
 *
 * @author adrianohrl
 */
public class CasualtyEntryEvent extends EntryEvent {
    
    private Casualty casualty;
    private int returnedQuantity;

    public CasualtyEntryEvent() {
    }

    /**
     * This constructor must be used when the phase production order has just PAUSED
     * @param casualty
     * @param sector
     * @param supervisor
     * @param phaseProductionOrder
     * @param subordinate
     * @param productionState
     * @param producedQuantity
     * @param eventDate
     * @param observation
     * @throws ProductionException 
     */
    public CasualtyEntryEvent(Casualty casualty, Sector sector, Supervisor supervisor, PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, ProductionStates productionState, int producedQuantity, Calendar eventDate, String observation) throws ProductionException {
        super(sector, supervisor, phaseProductionOrder, subordinate, productionState, producedQuantity, eventDate, observation);
        this.casualty = casualty;
    }

    /**
     * This constructor must be used when the phase production order has just RETURNED
     * @param casualty
     * @param sector
     * @param supervisor
     * @param phaseProductionOrder
     * @param subordinate
     * @param returnedQuantity
     * @param productionState
     * @param eventDate
     * @param observation 
     */
    public CasualtyEntryEvent(Casualty casualty, Sector sector, Supervisor supervisor, PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, int returnedQuantity, ProductionStates productionState, Calendar eventDate, String observation) throws ProductionException {
        super(sector, supervisor, phaseProductionOrder, subordinate, productionState, eventDate, observation);
        this.casualty = casualty;
        if (returnedQuantity <= 0) {
            throw new ProductionException("The returned quantity must be positive!!!");
        }
        if (returnedQuantity > phaseProductionOrder.getRemaingQuantity()) {
            throw new ProductionException("The returned quantity is greater then the remaining quantity that the phase production order needs to be done!!!");
        }
        this.returnedQuantity = returnedQuantity;
    }

    public Casualty getCasualty() {
        return casualty;
    }

    public void setCasualty(Casualty casualty) {
        this.casualty = casualty;
    }

    public int getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(int returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }
    
}
