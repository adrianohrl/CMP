/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.CasualtyEntryEvent;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.order.ProductionStates;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsPeriod extends AbstractEventsPeriod<EntryEvent, EntryEvent> {

    public EntryEventsPeriod(EntryEvent firstEvent, EntryEvent lastEvent) throws ReportException {
        super(!firstEvent.isSettingFree() ? firstEvent.getPhaseProductionOrder() : null, firstEvent, lastEvent);
        ProductionStates firstState = firstEvent.getProductionState();
        ProductionStates lastState = lastEvent.getProductionState();
        if (firstState == lastState) {
            throw new ReportException("The first event state must be different than the last event one!!!");
        }
        if (firstState.isPaused() && lastState.isFinished()) {
            throw new ReportException("The state transition from PAUSED to FINISHED is NOT allowed!!!");
        }
    }

    @Override
    public int getProducedQuantity() {
        ProductionStates state = lastEvent.getProductionState();
        if (state.isFreerState()) {
            return lastEvent.getProducedQuantity();
        }
        return 0;
    }

    @Override
    public int getReturnedQuantity() {
        if (lastEvent instanceof CasualtyEntryEvent && lastEvent.getProductionState().isReturned()) {
            CasualtyEntryEvent event = (CasualtyEntryEvent) lastEvent;
            return event.getReturnedQuantity();
        }
        return 0;
    }

    @Override
    public boolean isFreeWorkingPeriod() {
        return lastEvent.getProductionState().isStartingState() || firstEvent.getProductionState().isPaused();
    }

    @Override
    public boolean isEffectiveWorkingPeriod() {
        return firstEvent.getProductionState().isStartingState();
    }
    
}
