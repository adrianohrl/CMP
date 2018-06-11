/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.control.model.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.control.model.production.reports.filters.FindByPeriod;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.util.CalendarFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import tech.adrianohrl.util.CalendarUtil;

/**
 *
 * @author adrianohrl
 * @param <S>
 */
public abstract class AbstractProductionReport<S extends SeriesType> implements Iterable<ReportNumericSeries> {
    
    private final Manager manager;
    private final Calendar emissionDate = CalendarUtil.now();
    protected final Calendar startDate;
    protected final Calendar endDate;
    protected final EventsPeriodBuilder builder;
    private final Map<S, ReportNumericSeries> seriesMap = new TreeMap<>();

    protected AbstractProductionReport(EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        if (manager == null) {
            throw new ReportException("The report manager must not be null!!!");
        }
        if (startDate.after(endDate)) {
            throw new ReportException("The report start date must be before its end date!!!");
        }
        if (startDate.after(emissionDate)) {
            throw new ReportException("The report start date must be before its emission date!!!");
        }
        if (endDate.after(emissionDate)) {
            endDate = emissionDate;
        }
        this.manager = manager;
        this.endDate = endDate;
        this.startDate = startDate;
        FindByPeriod filter = new FindByPeriod(startDate, endDate);
        events.execute(filter);
        this.builder = new EventsPeriodBuilder(filter.getItems());
    }
    
    protected abstract Map<S, ReportNumericSeries<S>> getSeriesMap(); 
    
    public ReportNumericSeries getSeries(S seriesType) {
        if (seriesMap.isEmpty()) {
            Map<S, ReportNumericSeries<S>> map = getSeriesMap();
            if (!map.isEmpty()) {
                seriesMap.putAll(getSeriesMap());
            } else {
                throw new RuntimeException("The report map is unknown!!!");
            }
        }
        return seriesMap.get(seriesType);
    }

    @Override
    public Iterator<ReportNumericSeries> iterator() {
        if (seriesMap.isEmpty()) {
            Map<S, ReportNumericSeries<S>> map = getSeriesMap();
            if (!map.isEmpty()) {
                seriesMap.putAll(getSeriesMap());
            } else {
                throw new RuntimeException("The report map is unknown!!!");
            }
        }
        return seriesMap.values().iterator();
    }

    @Override
    public String toString() {
        return manager + " reported on " + CalendarFormat.format(emissionDate) + " from " + 
                CalendarFormat.format(startDate) + " to " + CalendarFormat.format(endDate);
    }

    public Manager getManager() {
        return manager;
    }

    public Calendar getEmissionDate() {
        return emissionDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public EventsPeriodBuilder getBuilder() {
        return builder;
    }
    
}
