/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.exceptions.ReportException;
import cmp.model.events.CasualtyEntryEvent;
import cmp.model.events.EntryEvent;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionStates;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsPeriod extends AbstractEventsPeriod<EntryEvent, EntryEvent> {

    public EntryEventsPeriod(PhaseProductionOrder phaseProductionOrder, EntryEvent firstEvent, EntryEvent lastEvent) throws ReportException {
        super(phaseProductionOrder, firstEvent, lastEvent);
        ProductionStates firstState = firstEvent.getProductionState();
        ProductionStates lastState = lastEvent.getProductionState();
        if (firstState == lastState) {
            throw new ReportException("The first event state must be different than the last event one!!!");
        }
        if (firstState.isFinishingState()) {
            throw new ReportException("The first event state must not be a starting one before another entry event of the same phase production order!!!");
        }
        if (lastState.isStartingState()) {
            if (lastState.isStarted()) {
                throw new ReportException("The last event state cannot be STARTED!!!");
            }
            else if (firstState.isPaused()) {
                throw new ReportException("The last event state can only be RESTARTED when the first event state is PAUSED!!!");
            }
        }
        if (firstState.isPaused() && lastState.isFinished()) {
            throw new ReportException("The state transition from PAUSED to FINISHED is NOT allowed!!!");
        }
    }

    @Override
    public double getProducedQuantity() {
        ProductionStates state = lastEvent.getProductionState();
        if (state.isPaused() || state.isFinished()) {
            return lastEvent.getProducedQuantity();
        }
        return 0.0;
    }

    @Override
    public double getReturnedQuantity() {
        if (lastEvent instanceof CasualtyEntryEvent && lastEvent.getProductionState().isReturned()) {
            CasualtyEntryEvent event = (CasualtyEntryEvent) lastEvent;
            return event.getReturnedQuantity();
        }
        return 0.0;
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
