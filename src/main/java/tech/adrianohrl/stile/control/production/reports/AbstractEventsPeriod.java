package tech.adrianohrl.stile.control.production.reports;

import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEvent;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.order.PhaseProductionOrder;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <F>
 * @param <L>
 */
public abstract class AbstractEventsPeriod<F extends AbstractEvent, L extends AbstractEvent> {
    
    protected final PhaseProductionOrder phaseProductionOrder;
    protected final F firstEvent;
    protected final L lastEvent;

    public AbstractEventsPeriod(PhaseProductionOrder phaseProductionOrder, F firstEvent, L lastEvent) throws ReportException {
        if (firstEvent.getEventDate().after(lastEvent.getEventDate())) {
            throw new ReportException("The first event must not be after the last event!!!");
        }
        if (firstEvent instanceof EntryEvent) {
            EntryEvent entryEvent = (EntryEvent) firstEvent;
            if (phaseProductionOrder != null && !phaseProductionOrder.equals(entryEvent.getPhaseProductionOrder())) {
                throw new ReportException("The first event must be related to the input phase production order!!!");
            }
        }
        else if (!(firstEvent instanceof TimeClockEvent)) {
            throw new ReportException("The first event must be an instance of TimeClockEvent or EntryEvent!!!");
        }
        this.firstEvent = firstEvent;
        if (lastEvent instanceof EntryEvent) {
            EntryEvent entryEvent = (EntryEvent) lastEvent;
            if (phaseProductionOrder != null && !phaseProductionOrder.equals(entryEvent.getPhaseProductionOrder())) {
                throw new ReportException("The last event must be related to the input phase production order because the first one is not a freer state!!!");
            }
        }
        else if (!(lastEvent instanceof TimeClockEvent)) {
            throw new ReportException("The first event must be an instance of TimeClockEvent or EntryEvent: " + firstEvent.getClass().getSimpleName() + "!!!");
        }
        this.lastEvent = lastEvent;
        this.phaseProductionOrder = phaseProductionOrder;
    }

    @Override
    public String toString() {
        return "AbstractEventsPeriod{" + "phaseProductionOrder=" + phaseProductionOrder + ", firstEvent=" + firstEvent + ", lastEvent=" + lastEvent + '}';
    }
    
    public double getDuration() {
        return (lastEvent.getEventDate().getTimeInMillis() - firstEvent.getEventDate().getTimeInMillis()) / 60000;
    }
    
    public int getProducedQuantity() {
        return 0;
    }
    
    public int getReturnedQuantity() {
        return 0;
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
