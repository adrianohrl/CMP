package tech.adrianohrl.stile.model.events.io;

import tech.adrianohrl.util.BooleanField;
import tech.adrianohrl.util.Field;
import tech.adrianohrl.util.CalendarField;
import tech.adrianohrl.util.StringField;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.TimeClockEvent;
import tech.adrianohrl.stile.model.personnel.Employee;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import tech.adrianohrl.stile.control.production.reports.EventsPeriodBuilder;
import tech.adrianohrl.stile.control.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.control.production.reports.filters.FindByEmployee;
import tech.adrianohrl.util.AbstractReader;
import tech.adrianohrl.util.Calendars;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class TimeClockEventsReader extends AbstractReader<TimeClockEvent> {
    
    /** Column Titles **/
    private final static String DATE_COLUMN_TITLE = "Date";
    private final static String TIME_COLUMN_TITLE = "Time";
    private final static String EMPLOYEE_COLUMN_TITLE = "Employee";
    private final static String ARRIVAL_COLUMN_TITLE = "Arrival";
    private final static String OBSERVATION_COLUMN_TITLE = "Observation";
    
    HashMap<Employee, Boolean> arrivalMap = new HashMap<>();
    
    @Override
    protected List<Field> getDefaultFields() {
        List<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new CalendarField(DATE_COLUMN_TITLE, "dd/MM/yyyy", true));
        defaultFields.add(new CalendarField(TIME_COLUMN_TITLE, "HH:mm", true));
        defaultFields.add(new StringField(EMPLOYEE_COLUMN_TITLE, true));
        defaultFields.add(new BooleanField(ARRIVAL_COLUMN_TITLE, "y", false));
        defaultFields.add(new StringField(OBSERVATION_COLUMN_TITLE, false));
        return defaultFields;
    }

    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        validate();
    }
    
    @Override
    protected TimeClockEvent build(List<Field> fields) throws IOException {
        Calendar calendar = Calendars.combine((Calendar) Field.getFieldValue(fields, DATE_COLUMN_TITLE), (Calendar) Field.getFieldValue(fields, TIME_COLUMN_TITLE));
        Employee employee = createEmployee(Field.getFieldValue(fields, EMPLOYEE_COLUMN_TITLE));
        Boolean arrival = Field.getFieldValue(fields, ARRIVAL_COLUMN_TITLE);
        if (arrival == null) {                    
            arrivalMap.putIfAbsent(employee, true);
            arrival = arrivalMap.get(employee);
            arrivalMap.put(employee, !arrival);
        }
        return new TimeClockEvent(employee, arrival, calendar, Field.getFieldValue(fields, OBSERVATION_COLUMN_TITLE));
    }
    
    protected Employee createEmployee(String employeeName) throws IOException {
        return new Subordinate("", employeeName);
    }
    
    private void validate() throws IOException {
        TimeClockEvent previous;
        FindByEmployee<TimeClockEvent> filter;
        EmployeeRelatedEventsList<TimeClockEvent> events = new EmployeeRelatedEventsList<>(getReadEntities());
        for (Employee employee : events.getInvolvedEmployees()) {
            filter = new FindByEmployee<>(employee);
            events.execute(filter);
            previous = null;
            for (TimeClockEvent current : filter.getItems()) {
                if (previous != null) {
                    if (previous.compareTo(current) >= 0) {
                        throw new IOException(previous + " must happen before " + current);
                    }
                    if (previous.isArrival() == current.isArrival()) {
                        throw new IOException(previous + " must be different than " + current);
                    }
                }
                previous = current;
            }
        }
    }
    
    public EmployeeRelatedEventsList getEmployeeRelatedEventsList() {
        return new EmployeeRelatedEventsList(getReadEntities());
    }
    
    public EventsPeriodBuilder getEventsPeriodBuilder() throws ReportException {
        return new EventsPeriodBuilder(new EmployeeRelatedEventsList(getReadEntities()));
    }
    
}
