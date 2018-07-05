package tech.adrianohrl.stile.control.production.reports;

import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.TimeClockEvent;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
