/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.TimeClockEvent;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsPeriod extends AbstractEventsPeriod<TimeClockEvent, TimeClockEvent> {

    public TimeClockEventsPeriod(TimeClockEvent firstEvent, TimeClockEvent lastEvent) throws ReportException {
        super(null, firstEvent, lastEvent);
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
