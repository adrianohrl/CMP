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
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 * @param <S>
 */
public abstract class AbstractEfficiencyReport<S extends SeriesType> extends AbstractAttendanceReport<S> {
    
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

    protected Map<AttendanceSeriesTypes, ReportNumericSeries<AttendanceSeriesTypes>> getSeriesMap(Employee employee) {
        throw new RuntimeException("Invalid usage");
    }

    protected Map<EfficiencySeriesTypes, ReportNumericSeries<EfficiencySeriesTypes>> getSeriesMap(Subordinate subordinate) {
        Map<EfficiencySeriesTypes, ReportNumericSeries<EfficiencySeriesTypes>> map = new TreeMap<>();
        double conversionFactor = 1.0 / 60.0;
        ReportNumericSeries<EfficiencySeriesTypes> series;
        series = new ReportDoubleSeries<>(EfficiencySeriesTypes.EFFECTIVE_DURATION, subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalEffectiveDuration, conversionFactor);
        map.put(EfficiencySeriesTypes.EFFECTIVE_DURATION, series);
        series = new ReportDoubleSeries<>(EfficiencySeriesTypes.EXPECTED_DURATION, subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalExpectedDuration, conversionFactor);
        map.put(EfficiencySeriesTypes.EXPECTED_DURATION, series);
        series = new ReportDoubleSeries<>(EfficiencySeriesTypes.FREE_DURATION, subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalFreeDuration, conversionFactor);
        map.put(EfficiencySeriesTypes.FREE_DURATION, series);
        series = new ReportDoubleSeries<>(EfficiencySeriesTypes.TOTAL_DURATION, subordinate, this, "[h]", EmployeeEventsPeriodBuilder::getTotalDuration, conversionFactor);
        map.put(EfficiencySeriesTypes.TOTAL_DURATION, series);
        series = new ReportIntegerSeries<>(EfficiencySeriesTypes.PRODUCED_QUANTITY, subordinate, this, "[un]", EmployeeEventsPeriodBuilder::getTotalProducedQuantity);
        map.put(EfficiencySeriesTypes.PRODUCED_QUANTITY, series);
        series = new ReportIntegerSeries<>(EfficiencySeriesTypes.RETURNED_QUANTITY, subordinate, this, "[un]", EmployeeEventsPeriodBuilder::getTotalReturnedQuantity);
        map.put(EfficiencySeriesTypes.RETURNED_QUANTITY, series);
        series = new ReportDoubleSeries<>(EfficiencySeriesTypes.EFFECTIVE_EFFICIENCY, subordinate, this, "%", EmployeeEventsPeriodBuilder::getTotalEffectiveEfficiency, 100.0);
        map.put(EfficiencySeriesTypes.EFFECTIVE_EFFICIENCY, series);
        series = new ReportDoubleSeries<>(EfficiencySeriesTypes.TOTAL_EFFICIENCY, subordinate, this, "%", EmployeeEventsPeriodBuilder::getTotalEfficiency, 100.0);
        map.put(EfficiencySeriesTypes.TOTAL_EFFICIENCY, series);
        return map;
    }
    
}
