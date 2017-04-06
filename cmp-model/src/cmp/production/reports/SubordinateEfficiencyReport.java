/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.exceptions.ReportException;
import cmp.model.events.EntryEvent;
import cmp.model.events.TimeClockEvent;
import cmp.model.personal.Manager;
import cmp.model.personal.Subordinate;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author adrianohrl
 */
public class SubordinateEfficiencyReport extends AbstractEfficiencyReport {
    
    private final Subordinate subordinate;

    public SubordinateEfficiencyReport(Subordinate subordinate, ArrayList<TimeClockEvent> timeClockEvents, ArrayList<EntryEvent> entryEvents, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(timeClockEvents, entryEvents, manager, startDate, endDate);
        this.subordinate = subordinate;
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }
    
}
