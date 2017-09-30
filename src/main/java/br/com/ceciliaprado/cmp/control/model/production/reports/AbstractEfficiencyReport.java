/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.FindByType;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.model.personnel.Subordinate;
import java.util.Calendar;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractEfficiencyReport extends AbstractAttendanceReport {
    
    protected final EmployeeRelatedEventsList<EntryEvent> entryEvents = new EmployeeRelatedEventsList<>();
    
    public AbstractEfficiencyReport(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        FindByType<EntryEvent> filter = new FindByType<>(EntryEvent.class);
        events.execute(filter);
        for (AbstractEmployeeRelatedEvent event : filter) {
            this.entryEvents.add((EntryEvent) event);
        }
    }

    public EmployeeRelatedEventsList<EntryEvent> getEntryEvents() {
        return entryEvents;
    }

    @Override
    protected TreeMap<ReportSeriesEnum, ReportNumericSeries> getSeriesMap(Employee employee) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected TreeMap<ReportSeriesEnum, ReportNumericSeries> getSeriesMap(Subordinate subordinate) {
        TreeMap<ReportSeriesEnum, ReportNumericSeries> map = new TreeMap<>();
        double conversionFactor = 1.0 / 60.0;
        ReportNumericSeries series;
        series = new ReportDoubleSeries(EfficiencyReportSeries.EFFECTIVE_DURATION, subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalEffectiveDuration, conversionFactor);
        map.put(EfficiencyReportSeries.EFFECTIVE_DURATION, series);
        series = new ReportDoubleSeries(EfficiencyReportSeries.EXPECTED_DURATION, subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalExpectedDuration, conversionFactor);
        map.put(EfficiencyReportSeries.EXPECTED_DURATION, series);
        series = new ReportDoubleSeries(EfficiencyReportSeries.FREE_DURATION, subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalFreeDuration, conversionFactor);
        map.put(EfficiencyReportSeries.FREE_DURATION, series);
        series = new ReportDoubleSeries(EfficiencyReportSeries.TOTAL_DURATION, subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalDuration, conversionFactor);
        map.put(EfficiencyReportSeries.TOTAL_DURATION, series);
        series = new ReportIntegerSeries(EfficiencyReportSeries.PRODUCED_QUANTITY, subordinate, this, "[un]", EmployeeEventsPeriodBuilder::getTotalProducedQuantity);
        map.put(EfficiencyReportSeries.PRODUCED_QUANTITY, series);
        series = new ReportIntegerSeries(EfficiencyReportSeries.RETURNED_QUANTITY, subordinate, this, "[un]", EmployeeEventsPeriodBuilder::getTotalReturnedQuantity);
        map.put(EfficiencyReportSeries.RETURNED_QUANTITY, series);
        series = new ReportDoubleSeries(EfficiencyReportSeries.EFFECTIVE_EFFICIENCY, subordinate, this, "%", EmployeeEventsPeriodBuilder::getTotalEffectiveEfficiency, 100.0);
        map.put(EfficiencyReportSeries.EFFECTIVE_EFFICIENCY, series);
        series = new ReportDoubleSeries(EfficiencyReportSeries.TOTAL_EFFICIENCY, subordinate, this, "%", EmployeeEventsPeriodBuilder::getTotalEfficiency, 100.0);
        map.put(EfficiencyReportSeries.TOTAL_EFFICIENCY, series);
        return map;
    }
    
}
