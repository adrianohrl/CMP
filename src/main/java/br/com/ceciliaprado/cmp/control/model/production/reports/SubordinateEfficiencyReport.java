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
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public class SubordinateEfficiencyReport extends AbstractEfficiencyReport {
    
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
    protected TreeMap<String, ReportNumericSeries> getSeriesMap() {
        TreeMap<String, ReportNumericSeries> map = new TreeMap<>();
        double conversionFactor = 1.0 / 60.0;
        ReportNumericSeries series;
        series = new ReportDoubleSeries(0, "Effective Duration", subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalEffectiveDuration, conversionFactor);
        map.put(series.getName(), series);
        series = new ReportDoubleSeries(1, "Expected Duration", subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalExpectedDuration, conversionFactor);
        map.put(series.getName(), series);
        series = new ReportDoubleSeries(2, "Free Duration", subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalFreeDuration, conversionFactor);
        map.put(series.getName(), series);
        series = new ReportDoubleSeries(3, "Total Duration", subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalDuration, conversionFactor);
        map.put(series.getName(), series);
        series = new ReportIntegerSeries(4, "Produced Quantity", subordinate, this, "[un]", EmployeeEventsPeriodBuilder::getTotalProducedQuantity);
        map.put(series.getName(), series);
        series = new ReportIntegerSeries(5, "Returned Quantity", subordinate, this, "[un]", EmployeeEventsPeriodBuilder::getTotalReturnedQuantity);
        map.put(series.getName(), series);
        series = new ReportDoubleSeries(6, "Effective Efficiency", subordinate, this, "%", EmployeeEventsPeriodBuilder::getTotalEffectiveEfficiency, 100.0);
        map.put(series.getName(), series);
        series = new ReportDoubleSeries(7, "Total Efficiency", subordinate, this, "%", EmployeeEventsPeriodBuilder::getTotalEfficiency, 100.0);
        map.put(series.getName(), series);
        return map;
    }
    
}
