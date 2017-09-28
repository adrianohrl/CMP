/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.control.model.production.reports.filters.FindByPeriod;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.util.CalendarFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractProductionReport implements Iterable<ReportNumericSeries> {
    
    private final Manager manager;
    private final Calendar emissionDate = new GregorianCalendar();
    protected final Calendar startDate;
    protected final Calendar endDate;
    protected final EventsPeriodBuilder builder;
    private final TreeMap<String, ReportNumericSeries> seriesMap = new TreeMap<>();

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
            throw new ReportException("The report end date must be before its emission date!!!");
        }
        this.manager = manager;
        this.endDate = endDate;
        this.startDate = startDate;
        FindByPeriod filter = new FindByPeriod(startDate, endDate);
        events.execute(filter);
        this.builder = new EventsPeriodBuilder(filter.getItems());
    }
    
    protected abstract TreeMap<String, ReportNumericSeries> getSeriesMap(); 
    
    public ReportNumericSeries getSeries(String name) {
        return seriesMap.get(name);
    }

    @Override
    public Iterator<ReportNumericSeries> iterator() {
        if (seriesMap.isEmpty()) {
            TreeMap<String, ReportNumericSeries> map = getSeriesMap();
            if (!map.isEmpty()) {
                seriesMap.putAll(getSeriesMap());
            } else {
                System.out.println("The report map is unknown!!!");
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
