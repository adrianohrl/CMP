/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.events.io;

import cmp.util.BooleanField;
import cmp.util.Field;
import cmp.util.CalendarField;
import cmp.util.StringField;
import cmp.exceptions.IOException;
import cmp.exceptions.ReportException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.TimeClockEvent;
import cmp.model.personal.Employee;
import cmp.model.personal.Subordinate;
import cmp.production.reports.EventsPeriodBuilder;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.production.reports.filters.FindByEmployee;
import cmp.util.CSVReader;
import cmp.util.Calendars;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsReader implements Iterable<TimeClockEvent> {
    
    /** Column Titles **/
    private final static String DATE_COLUMN_TITLE = "Date";
    private final static String TIME_COLUMN_TITLE = "Time";
    private final static String EMPLOYEE_COLUMN_TITLE = "Employee";
    private final static String ARRIVAL_COLUMN_TITLE = "Arrival";
    private final static String OBSERVATION_COLUMN_TITLE = "Observation";
    
    private final CSVReader csvReader;
    private final ArrayList<TimeClockEvent> timeClockEvents = new ArrayList<>();

    public TimeClockEventsReader(String fileName) throws IOException {
        csvReader = new CSVReader(fileName, getDefaultFields());
    }
    
    public void readFile() throws IOException {
        csvReader.readColumnTitles();
        readTimeClockEvents();
        csvReader.close();
    }
    
    private ArrayList<Field> getDefaultFields() {
        ArrayList<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new CalendarField(DATE_COLUMN_TITLE, "dd/MM/yyyy", true));
        defaultFields.add(new CalendarField(TIME_COLUMN_TITLE, "HH:mm", true));
        defaultFields.add(new StringField(EMPLOYEE_COLUMN_TITLE, true));
        defaultFields.add(new BooleanField(ARRIVAL_COLUMN_TITLE, "y", true));
        defaultFields.add(new StringField(OBSERVATION_COLUMN_TITLE, false));
        return defaultFields;
    }
    
    private void readTimeClockEvents() throws IOException {
        ArrayList<Field> fields = csvReader.fillFields();
        while (fields != null) {
            timeClockEvents.add(getTimeClockEvent(fields));
            fields = csvReader.fillFields();
        }
        Collections.sort(timeClockEvents);
        validate();
    }
    
    private TimeClockEvent getTimeClockEvent(ArrayList<Field> fields) throws IOException {
        Calendar calendar = Calendars.sum((Calendar) Field.getFieldValue(fields, DATE_COLUMN_TITLE), (Calendar) Field.getFieldValue(fields, TIME_COLUMN_TITLE));
        Employee employee = createEmployee(Field.getFieldValue(fields, EMPLOYEE_COLUMN_TITLE));
        return new TimeClockEvent(employee, Field.getFieldValue(fields, ARRIVAL_COLUMN_TITLE), calendar, Field.getFieldValue(fields, OBSERVATION_COLUMN_TITLE));
    }
    
    protected Employee createEmployee(String employeeName) throws IOException {
        return new Subordinate("", employeeName);
    }
    
    private void validate() throws IOException {
        TimeClockEvent previous;
        TimeClockEvent current;
        FindByEmployee filter;
        EmployeeRelatedEventsList events = new EmployeeRelatedEventsList(timeClockEvents);
        for (Employee employee : events.getInvolvedEmployees()) {
            filter = new FindByEmployee(employee);
            events.execute(filter);
            previous = null;
            for (AbstractEmployeeRelatedEvent event : filter.getItems()) {
                current = (TimeClockEvent) event;
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

    public ArrayList<TimeClockEvent> getTimeClockEvents() {
        return timeClockEvents;
    }
    
    public EmployeeRelatedEventsList getEmployeeRelatedEventsList() {
        return new EmployeeRelatedEventsList(timeClockEvents);
    }
    
    public EventsPeriodBuilder getEventsPeriodBuilder() throws ReportException {
        return new EventsPeriodBuilder(new EmployeeRelatedEventsList(timeClockEvents));
    }

    @Override
    public Iterator<TimeClockEvent> iterator() {
        return timeClockEvents.iterator();
    }
    
}
