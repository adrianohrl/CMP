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
import cmp.model.personal.Sector;
import cmp.model.personal.Subordinate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class SectorEfficiencyReport extends AbstractEfficiencyReport implements Iterable<SubordinateEfficiencyReport> {
    
    private final Sector sector;
    private final MultipleSubordinateEfficiencyReport multipleSubordinateEfficiencyReport;

    public SectorEfficiencyReport(Sector sector, List<Subordinate> subordinates, ArrayList<TimeClockEvent> timeClockEvents, ArrayList<EntryEvent> entryEvents, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(timeClockEvents, entryEvents, manager, startDate, endDate);
        this.sector = sector;
        multipleSubordinateEfficiencyReport = new MultipleSubordinateEfficiencyReport(subordinates, timeClockEvents, entryEvents, manager, startDate, endDate);
    }

    public SectorEfficiencyReport(Sector sector, ArrayList<TimeClockEvent> timeClockEvents, ArrayList<EntryEvent> entryEvents, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        this(sector, sector.getSupervisor().getSubordinates(), timeClockEvents, entryEvents, manager, startDate, endDate);
    }

    @Override
    public Iterator<SubordinateEfficiencyReport> iterator() {
        return multipleSubordinateEfficiencyReport.iterator();
    }
    
}
