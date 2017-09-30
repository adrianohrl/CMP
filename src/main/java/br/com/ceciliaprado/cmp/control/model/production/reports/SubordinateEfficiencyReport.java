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
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author adrianohrl
 */
public class SubordinateEfficiencyReport extends AbstractEfficiencyReport<EfficiencySeriesTypes> {
    
    private final Subordinate subordinate;
    
    public SubordinateEfficiencyReport(Subordinate subordinate, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        if (subordinate == null) {
            throw new ReportException("The report subordinate must not be null!!!");
        }
        this.subordinate = subordinate;
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }

    @Override
    protected Map<EfficiencySeriesTypes, ReportNumericSeries<EfficiencySeriesTypes>> getSeriesMap() {
        return super.getSeriesMap(subordinate);
    }
    
}
