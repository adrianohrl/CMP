/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.exceptions.ReportException;
import cmp.model.events.CasualtyEntryEvent;
import cmp.model.events.EntryEvent;
import cmp.model.events.TimeClockEvent;
import cmp.model.production.ProductionStates;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEntryEventsPeriod extends AbstractEventsPeriod<TimeClockEvent, EntryEvent> {

    public TimeClockEntryEventsPeriod(TimeClockEvent firstEvent, EntryEvent lastEvent) throws ReportException {
        super(!lastEvent.isStarting() ? lastEvent.getPhaseProductionOrder() : null, firstEvent, lastEvent);
        if (!firstEvent.isArrival()) {
            throw new ReportException("A DEPARTURE time clock event must be a first event only if the last event is an ARRIVAL time clock event!!!");
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
        return lastEvent.getProductionState().isStartingState();
    }

    @Override
    public boolean isEffectiveWorkingPeriod() {
        return !lastEvent.getProductionState().isStartingState();
    }
    
}
