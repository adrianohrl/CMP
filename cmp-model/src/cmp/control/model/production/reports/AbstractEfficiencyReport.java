/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.model.production.reports;

import cmp.exceptions.ReportException;
import cmp.model.events.EntryEvent;
import cmp.model.events.TimeClockEvent;
import cmp.model.personnel.Manager;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractEfficiencyReport extends AbstractProductionReport {
    
    protected final ArrayList<TimeClockEvent> timeClockEvents;
    protected final ArrayList<EntryEvent> entryEvents;

    public AbstractEfficiencyReport(ArrayList<TimeClockEvent> timeClockEvents, ArrayList<EntryEvent> entryEvents, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(manager, startDate, endDate);
        for (TimeClockEvent event : timeClockEvents) {
            Calendar date = event.getEventDate();
            if (date.before(startDate)) {
                throw new ReportException("The time event date must not be before the report start date!!!");
            }
            if (date.after(endDate)) {
                throw new ReportException("The time event date must not be after the report end date!!!");
            }
        }
        this.timeClockEvents = timeClockEvents;
        for (EntryEvent event : entryEvents) {
            Calendar date = event.getEventDate();
            if (date.before(startDate)) {
                throw new ReportException("The entry event date must not be before the report start date!!!");
            }
            if (date.after(endDate)) {
                throw new ReportException("The entry event date must not be after the report end date!!!");
            }
        }
        this.entryEvents = entryEvents;
    }

    public ArrayList<TimeClockEvent> getTimeClockEvents() {
        return timeClockEvents;
    }

    public ArrayList<EntryEvent> getEntryEvents() {
        return entryEvents;
    }
    
}
