/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.events.io;

import cmp.exceptions.IOException;
import cmp.model.events.AbstractEmployeeRelatedEvent;
import cmp.model.events.TimeClockEvent;
import cmp.model.personal.Employee;
import cmp.model.personal.Subordinate;
import cmp.production.reports.filters.EmployeeRelatedEventsList;
import cmp.production.reports.filters.FindByEmployee;
import cmp.util.CSVReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
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
    
    private final ArrayList<Field> defaultFields = new ArrayList<>();
    private final ArrayList<TimeClockEvent> timeClockEvents = new ArrayList<>();

    public TimeClockEventsReader(String fileName) throws IOException {
        defaultFields.add(new CalendarField(DATE_COLUMN_TITLE, "dd/MM/yyyy", true));
        defaultFields.add(new CalendarField(TIME_COLUMN_TITLE, "hh:mm", true));
        defaultFields.add(new StringField(EMPLOYEE_NAME_COLUMN_TITLE, true));
        defaultFields.add(new StringField(EMPLOYEE_CODE_COLUMN_TITLE, false));
        defaultFields.add(new BooleanField(ARRIVAL_COLUMN_TITLE, "y", true));
        defaultFields.add(new StringField(OBSERVATION_COLUMN_TITLE, false));
        readTimeClockEvents(fileName);
    }
    
    private void readTimeClockEvents(String fileName) throws IOException {
        CSVReader csvReader = new CSVReader(fileName);
        int counter = 0;
        int index;
        for (String fieldTitle : csvReader) {
            index = indexOf(fieldTitle);
            if (index == -1) {
                throw new IOException("Inexistent time clock event field!!!");
            }
            defaultFields.get(index).setColumnIndex(counter++);
        }
        for (int i = 0; i < defaultFields.size(); i++) {
            Field field = defaultFields.get(i);
            if (!field.exists()) {
                if (field.isMandatory()) {
                    throw new IOException(field.getTitle() + " column title is mandatory!!!");
                } else {
                    defaultFields.remove(field);
                }
            }
        }
        if (defaultFields.isEmpty()) {
            throw new IOException("No column titles found!!!");
        }
        Collections.sort(defaultFields);
        ArrayList<Field> fields;
        Iterator<String> readValue = csvReader.iterator();
        while (!csvReader.eof()) {
            fields = new ArrayList<>(defaultFields);
            for (Field field : fields) {
                if (!readValue.hasNext() && field.isMandatory()) {
                    throw new IOException("Expected a new value in " + field.getTitle() + " column!!!");
                }
                try {
                    field.setValue(readValue.next());
                } catch (ParseException e) {
                    throw new IOException("Parse Exception catched: " + e.getMessage());
                }
            }
            timeClockEvents.add(getTimeClockEvent(fields));
            readValue = csvReader.iterator();
        }
        csvReader.close();
        Collections.sort(timeClockEvents);
        validate();
    }
    
    private int indexOf(String fieldTitle) {
        for (int i = 0; i < defaultFields.size(); i++) {
            if (defaultFields.get(i).equals(fieldTitle)) {
                return i;
            }
        }
        return -1;
    }
    
    private TimeClockEvent getTimeClockEvent(ArrayList<Field> fields) {
        Calendar calendar = sum(getFieldValue(fields, DATE_COLUMN_TITLE), getFieldValue(fields, TIME_COLUMN_TITLE));
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
    
    private Calendar sum(Calendar date, Calendar time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date.getTimeInMillis());
        calendar.set(Calendar.HOUR, time.get(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, time.get(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, time.get(Calendar.MILLISECOND));
        return calendar;
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

    @Override
    public Iterator<TimeClockEvent> iterator() {
        return timeClockEvents.iterator();
    }
    
}
