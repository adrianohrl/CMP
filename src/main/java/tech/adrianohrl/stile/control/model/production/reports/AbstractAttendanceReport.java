package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.control.model.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.control.model.production.reports.filters.FindByType;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Manager;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <S>
 */
public abstract class AbstractAttendanceReport<S extends SeriesType> extends AbstractProductionReport<S> {
    
    protected final EmployeeRelatedEventsList<TimeClockEvent> timeClockEvents = new EmployeeRelatedEventsList<>();
    
    protected AbstractAttendanceReport(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        FindByType<TimeClockEvent> filter = new FindByType<>(TimeClockEvent.class);
        this.builder.execute(filter);
        for (AbstractEmployeeRelatedEvent event : filter) {
            this.timeClockEvents.add((TimeClockEvent) event);
        }
    }

    public EmployeeRelatedEventsList<TimeClockEvent> getTimeClockEvents() {
        return timeClockEvents;
    }

    protected Map<AttendanceSeriesTypes, ReportNumericSeries<AttendanceSeriesTypes>> getSeriesMap(Employee employee) {
        Map<AttendanceSeriesTypes, ReportNumericSeries<AttendanceSeriesTypes>> map = new TreeMap<>();
        ReportNumericSeries<AttendanceSeriesTypes> series = new ReportDoubleSeries<>(AttendanceSeriesTypes.TOTAL_QUANTITY, employee, this, "[h]", EmployeeEventsPeriodBuilder::getTotalDuration, 1 / 60.0);
        map.put(AttendanceSeriesTypes.TOTAL_QUANTITY, series);
        return map;
    }
    
}
