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
    private final static String EMPLOYEE_NAME_COLUMN_TITLE = "Employee Name";
    private final static String EMPLOYEE_CODE_COLUMN_TITLE = "Employee Code";
    private final static String ARRIVAL_COLUMN_TITLE = "Arrival";
    private final static String OBSERVATION_COLUMN_TITLE = "Observation";
    
    private final CSVReader csvReader;
    private final ArrayList<TimeClockEvent> timeClockEvents = new ArrayList<>();

    public TimeClockEventsReader(String fileName) throws IOException {
        ArrayList<Field> defaultFields = new ArrayList<>();
        defaultFields.add(new CalendarField(DATE_COLUMN_TITLE, "dd/MM/yyyy", true));
        defaultFields.add(new CalendarField(TIME_COLUMN_TITLE, "hh:mm", true));
        defaultFields.add(new StringField(EMPLOYEE_NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(EMPLOYEE_CODE_COLUMN_TITLE, false));
        defaultFields.add(new BooleanField(ARRIVAL_COLUMN_TITLE, "y", true));
        defaultFields.add(new StringField(OBSERVATION_COLUMN_TITLE, false));
        csvReader = new CSVReader(fileName, defaultFields);
        csvReader.readColumnTitles();
        readTimeClockEvents();
        csvReader.close();
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
    
    private TimeClockEvent getTimeClockEvent(ArrayList<Field> fields) {
        Calendar calendar = Calendars.sum(getFieldValue(fields, DATE_COLUMN_TITLE), getFieldValue(fields, TIME_COLUMN_TITLE));
        Employee employee = new Subordinate(getFieldValue(fields, EMPLOYEE_CODE_COLUMN_TITLE), getFieldValue(fields, EMPLOYEE_NAME_COLUMN_TITLE));
        return new TimeClockEvent(employee, getFieldValue(fields, ARRIVAL_COLUMN_TITLE), calendar, getFieldValue(fields, OBSERVATION_COLUMN_TITLE));
    }
    
    private <T> T getFieldValue(ArrayList<Field> fields, String fieldTitle) {
        for (Field field : fields) {
            if (field.equals(fieldTitle)) {
                return (T) field.getValue();
            }
        }
        return null;
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
