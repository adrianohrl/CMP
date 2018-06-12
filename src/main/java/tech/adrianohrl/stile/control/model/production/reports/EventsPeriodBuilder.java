package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.control.model.production.reports.filters.FindByEmployee;
import tech.adrianohrl.stile.control.model.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.util.Calendars;
import tech.adrianohrl.util.Command;
import tech.adrianohrl.util.Execute;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class EventsPeriodBuilder implements Iterable<EmployeeEventsPeriodBuilder>, Execute<AbstractEmployeeRelatedEvent> {
    
    private final EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events = new EmployeeRelatedEventsList<>();
    private final List<EmployeeEventsPeriodBuilder> builders = new ArrayList<>();
    
    public EventsPeriodBuilder(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events) throws ReportException {
        if (events.isEmpty()) {
            throw new ReportException("There is no event to build!!!");
        }
        this.events.addAll(events);
        prepare();
        build();
    }
    
    private void prepare() {
        Collections.sort(events);
        for (Employee employee : events.getInvolvedEmployees()) {
            FindByEmployee filter = new FindByEmployee(employee);
            events.execute(filter);
            EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> employeeEvents = filter.getItems();
            int i = 0;
            AbstractEmployeeRelatedEvent event = employeeEvents.get(i);
            Calendar previousDate = event.getEventDate();
            Calendar dayStart = (Calendar) previousDate.clone();
            dayStart.set(Calendar.HOUR_OF_DAY, 0);
            dayStart.set(Calendar.MINUTE, 0);
            dayStart.set(Calendar.SECOND, 0);
            dayStart.set(Calendar.MILLISECOND, 0);
            Calendar dayEnd = (Calendar) dayStart.clone();
            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
            dayEnd.add(Calendar.MILLISECOND, -1);
            if (!(event instanceof TimeClockEvent) || !((TimeClockEvent) event).isArrival()) {  
                event = new TimeClockEvent(employee, true, (Calendar) dayStart.clone(), null);
                events.add(event);
            }
            while (++i < employeeEvents.size())  {
                event = employeeEvents.get(i);
                Calendar nextDate = event.getEventDate();
                if (Calendars.changedDay(previousDate, nextDate)) {
                    event = employeeEvents.get(i - 1);
                    if (!(event instanceof TimeClockEvent) || ((TimeClockEvent) event).isArrival()) {
                        while (dayStart.after(previousDate) || dayEnd.before(previousDate)) {
                            dayStart.add(Calendar.DAY_OF_MONTH, 1);
                            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        while (dayEnd.before(nextDate) || dayEnd.equals(nextDate)) {
                            event = new TimeClockEvent(employee, false, (Calendar) dayEnd.clone(), null);
                            events.add(event);
                            dayStart.add(Calendar.DAY_OF_MONTH, 1);
                            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
                            event = new TimeClockEvent(employee, true, (Calendar) dayStart.clone(), null);
                            events.add(event);
                        }
                    }
                }
                previousDate = nextDate;           
            }
            event = employeeEvents.get(employeeEvents.size() - 1);
            if (!(event instanceof TimeClockEvent) || ((TimeClockEvent) event).isArrival()) {
                while (dayStart.after(previousDate) || dayEnd.before(previousDate)) {
                    dayStart.add(Calendar.DAY_OF_MONTH, 1);
                    dayEnd.add(Calendar.DAY_OF_MONTH, 1);
                }
                event = new TimeClockEvent(employee, false, (Calendar) dayEnd.clone(), null);
                events.add(event);
            }
        }
        Collections.sort(events);
    }
    
    private void build() throws ReportException {
        FindByEmployee filter;
        for (Employee employee : events.getInvolvedEmployees()) {
            filter = new FindByEmployee(employee);
            events.execute(filter);
            builders.add(new EmployeeEventsPeriodBuilder(employee, filter.getItems()));
        }
    }
    
    public EmployeeEventsPeriodBuilder get(Employee employee) {
        for (EmployeeEventsPeriodBuilder builder : this) {
            if (employee.equals(builder.getEmployee())) {
                return builder;
            }
        }
        return null;
    }

    @Override
    public Iterator<EmployeeEventsPeriodBuilder> iterator() {
        return builders.iterator();
    }     

    @Override
    public void execute(Command<AbstractEmployeeRelatedEvent> command) {
        events.execute(command);
    }
    
}
