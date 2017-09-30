/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public class SectorEfficiencyReport extends AbstractEfficiencyReport {
    
    private final Sector sector;
    private final MultipleSubordinateEfficiencyReport multipleSubordinateEfficiencyReport;

    public SectorEfficiencyReport(Sector sector, List<Subordinate> subordinates, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        if (sector == null) {
            throw new ReportException("The report sector must not be null!!!");
        }
        this.sector = sector;
        multipleSubordinateEfficiencyReport = new MultipleSubordinateEfficiencyReport(subordinates, events, manager, startDate, endDate);
    }

    public SectorEfficiencyReport(Sector sector, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        this(sector, sector.getSupervisor().getSubordinates(), events, manager, startDate, endDate);
    }

    @Override
    protected TreeMap<ReportSeriesEnum, ReportNumericSeries> getSeriesMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
