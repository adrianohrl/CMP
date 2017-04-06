/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.exceptions.ReportException;
import cmp.model.events.AbstractEvent;
import cmp.model.events.EntryEvent;
import cmp.model.events.TimeClockEvent;
import cmp.model.production.PhaseProductionOrder;

/**
 *
 * @author adrianohrl
 * @param <F>
 * @param <L>
 */
public abstract class AbstractEventsPeriod<F extends AbstractEvent, L extends AbstractEvent> {
    
    protected final PhaseProductionOrder phaseProductionOrder;
    protected final F firstEvent;
    protected final L lastEvent;

    public AbstractEventsPeriod(PhaseProductionOrder phaseProductionOrder, F firstEvent, L lastEvent) throws ReportException {
        this.phaseProductionOrder = phaseProductionOrder;
        if (firstEvent.getEventDate().after(lastEvent.getEventDate())) {
            throw new ReportException("The first event must not be after the last event!!!");
        }
        if (firstEvent instanceof EntryEvent) {
            EntryEvent entryEvent = (EntryEvent) firstEvent;
            if (!phaseProductionOrder.equals(entryEvent.getPhaseProductionOrder())) {
                throw new ReportException("The first event must be related to the input phase production order!!!");
            }
        }
        else if (!(firstEvent instanceof TimeClockEvent)) {
            throw new ReportException("The first event must be an instance of TimeClockEvent or EntryEvent!!!");
        }
        this.firstEvent = firstEvent;
        if (lastEvent instanceof EntryEvent) {
            EntryEvent entryEvent = (EntryEvent) lastEvent;
            if (!phaseProductionOrder.equals(entryEvent.getPhaseProductionOrder())) {
                throw new ReportException("The first event must be related to the input phase production order!!!");
            }
        }
        else if (!(firstEvent instanceof TimeClockEvent)) {
            throw new ReportException("The first event must be an instance of TimeClockEvent or EntryEvent!!!");
        }
        this.lastEvent = lastEvent;
    }
    
    public double getDuration() {
        return (lastEvent.getEventDate().getTimeInMillis() - firstEvent.getEventDate().getTimeInMillis()) / 60000;
    }
    
    public double getProducedQuantity() {
        return 0.0;
    }
    
    public double getReturnedQuantity() {
        return 0.0;
    }
    
    public double getExpectedWorkingDuration() {
        return phaseProductionOrder.getPhase().getExpectedDuration() * getProducedQuantity();
    }
    
    public double getEffectiveWorkingDuration() {
        return getDuration() * getProducedQuantity();
    }
    
    public boolean isWorkingPeriod() {
        return true;
    }
    
    public boolean isFreeWorkingPeriod() {
        return false;
    }
    
    public boolean isEffectiveWorkingPeriod() {
        return false;
    }

    public PhaseProductionOrder getPhaseProductionOrder() {
        return phaseProductionOrder;
    }

    public F getFirstEvent() {
        return firstEvent;
    }

    public L getLastEvent() {
        return lastEvent;
    }
    
}
