/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.FindByEmployee;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public class MultipleSubordinateEfficiencyReport extends AbstractEfficiencyReport {

    private final List<SubordinateEfficiencyReport> subordinateEfficiencyReports = new ArrayList<>();

    public MultipleSubordinateEfficiencyReport(List<Subordinate> subordinates, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        for (Subordinate subordinate : subordinates) {
            FindByEmployee filter = new FindByEmployee<>(subordinate);
            events.execute(filter);
            subordinateEfficiencyReports.add(new SubordinateEfficiencyReport(subordinate, filter.getItems(), manager, startDate, endDate));
        }
    }

    @Override
    protected TreeMap<String, ReportNumericSeries> getSeriesMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
