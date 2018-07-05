package tech.adrianohrl.stile.control.production.reports;

import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.CasualtyEntryEvent;
import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.order.ProductionStates;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
