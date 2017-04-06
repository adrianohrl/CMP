/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.exceptions.ReportException;
import cmp.model.events.TimeClockEvent;
import cmp.model.production.PhaseProductionOrder;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsPeriod extends AbstractEventsPeriod<TimeClockEvent, TimeClockEvent> {

    public TimeClockEventsPeriod(PhaseProductionOrder phaseProductionOrder, TimeClockEvent firstEvent, TimeClockEvent lastEvent) throws ReportException {
        super(phaseProductionOrder, firstEvent, lastEvent);
    }

    @Override
    public boolean isWorkingPeriod() {
        return firstEvent.isArrival() && !lastEvent.isArrival();
    }

    @Override
    public boolean isFreeWorkingPeriod() {
        return firstEvent.isArrival() && !lastEvent.isArrival();
    }

    @Override
    public boolean isEffectiveWorkingPeriod() {
        return false;
    }
    
}
