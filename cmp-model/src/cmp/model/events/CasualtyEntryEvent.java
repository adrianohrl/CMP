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
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author adrianohrl
 */
@Entity
public class CasualtyEntryEvent extends EntryEvent {
    
    @ManyToOne
    private Casualty casualty;
    private int returnedQuantity = 0;

    public CasualtyEntryEvent() {
    }

    /**
     * This constructor must be used when the phase production order has just PAUSED or RETURNED
     * @param casualty
     * @param sector
     * @param supervisor
     * @param phaseProductionOrder
     * @param subordinate
     * @param productionState
     * @param producedQuantity
     * @param eventDate
     * @param observation 
     * @throws cmp.exceptions.ProductionException 
     */
    public CasualtyEntryEvent(Casualty casualty, Sector sector, Supervisor supervisor, PhaseProductionOrder phaseProductionOrder, Subordinate subordinate, ProductionStates productionState, int producedQuantity, Calendar eventDate, String observation) throws ProductionException {
        super(sector, supervisor, phaseProductionOrder, subordinate, productionState, producedQuantity, observation, eventDate);
        this.casualty = casualty;
        if (productionState.isReturned()) {
            this.returnedQuantity = phaseProductionOrder.getRemaingQuantity() - producedQuantity;
            if (this.returnedQuantity <= 0) {
                throw new ProductionException("The returned quantity must be positive!!!");
            }
        } else if (!productionState.isPaused()) {
            throw new ProductionException("This constructor must only be used when the production state is PAUSED or RETURNED!!!");    
        }
    }

    @Override
    public String toString() {
        return super.toString() + (returnedQuantity != 0 && getProducedQuantity() != 0 ? " and" : "") + 
                (returnedQuantity != 0 ? " returned " + returnedQuantity + " [un]" : "");
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
