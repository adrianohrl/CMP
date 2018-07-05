package tech.adrianohrl.stile.control.production.reports;

import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.CasualtyEntryEvent;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.order.ProductionStates;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
