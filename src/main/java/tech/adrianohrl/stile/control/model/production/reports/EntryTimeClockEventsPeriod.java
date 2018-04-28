/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.events.TimeClockEvent;

/**
 *
 * @author adrianohrl
 */
public class EntryTimeClockEventsPeriod extends AbstractEventsPeriod<EntryEvent, TimeClockEvent> {

    public EntryTimeClockEventsPeriod(EntryEvent firstEvent, TimeClockEvent lastEvent) throws ReportException {
        super(!firstEvent.isSettingFree() ? firstEvent.getPhaseProductionOrder() : null, firstEvent, lastEvent);
        if (lastEvent.isArrival()) {
            throw new ReportException("An ARRIVAL time clock event must be a last event only if the first event is a DEPARTURE time clock event!!!");
        }
    }

    @Override
    public boolean isFreeWorkingPeriod() {
        return !firstEvent.getProductionState().isStartingState();
    }

    @Override
    public boolean isEffectiveWorkingPeriod() {
        return firstEvent.getProductionState().isStartingState();
    }
    
}
