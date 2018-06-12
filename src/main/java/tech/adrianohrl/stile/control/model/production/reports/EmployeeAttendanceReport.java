package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.control.model.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Manager;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class EmployeeAttendanceReport extends AbstractAttendanceReport<AttendanceSeriesTypes> {
    
    private final Employee employee;

    public EmployeeAttendanceReport(Employee employee, List<TimeClockEvent> timeClockEvents, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(new EmployeeRelatedEventsList<>(timeClockEvents), manager, startDate, endDate);
        this.employee = employee;
    }

    public EmployeeAttendanceReport(Employee employee, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    protected Map<AttendanceSeriesTypes, ReportNumericSeries<AttendanceSeriesTypes>> getSeriesMap() {
        return super.getSeriesMap(employee);
    }
    
}
