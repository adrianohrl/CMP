/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;

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
