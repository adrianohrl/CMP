/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.FindByPeriod;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.util.CalendarFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 *
 * @author adrianohrl
 */
public abstract class ReportNumericSeries implements Iterable<Map.Entry<Calendar, Number>>, Comparable<ReportNumericSeries> {
    
    private final int orderNumber;
    private final String name;
    private final Calendar startDate;
    private final Calendar endDate;
    protected String unit;
    protected final EmployeeEventsPeriodBuilder builder;
    protected final Function<EmployeeEventsPeriodBuilder, Number> function;

    public ReportNumericSeries(int orderNumber, String name, Calendar startDate, 
            Calendar endDate, String unit, EmployeeEventsPeriodBuilder builder, 
            Function<EmployeeEventsPeriodBuilder, Number> function) {
        this.orderNumber = orderNumber;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.unit = unit;
        this.builder = builder;
        this.function = function;
    }
    
    protected TreeMap<Calendar, Number> getSeries() {
        TreeMap<Calendar, Number> series = new TreeMap<>();
        Calendar dayStart = (Calendar) startDate.clone();
        dayStart.set(Calendar.HOUR_OF_DAY, 0);
        dayStart.set(Calendar.MINUTE, 0);
        dayStart.set(Calendar.SECOND, 0);
        dayStart.set(Calendar.MILLISECOND, 0);
        Calendar dayEnd = (Calendar) dayStart.clone();
        dayEnd.add(Calendar.DAY_OF_MONTH, 1);
        dayEnd.add(Calendar.MILLISECOND, -1);
        FindByPeriod<AbstractEmployeeRelatedEvent> filter;
        EmployeeEventsPeriodBuilder periodBuilder;
        while (!dayEnd.after(endDate)) {
            Number value = zero();
            if (builder != null) {
                filter = new FindByPeriod<>(dayStart, dayEnd);
                builder.execute(filter);
                try {
                    periodBuilder = new EmployeeEventsPeriodBuilder(builder.getEmployee(), filter.getItems());
                    value = function.apply(periodBuilder);
                } catch (ReportException e) {
                    System.out.println("ReportException caught: " + e.getMessage());
                }
            }
            series.put((Calendar) dayStart.clone(), convert(value));
            dayStart.add(Calendar.DAY_OF_MONTH, 1);
            dayEnd.add(Calendar.DAY_OF_MONTH, 1);
        }
        return series;
    }  
    
    public Map<Object, Number> getChartSeries() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<Object, Number> series = new TreeMap<>();
        for (Map.Entry<Calendar, Number> data : this) {
            series.put(formatter.format(data.getKey()), data.getValue());
        }
        return series;
    }
    
    protected abstract Number zero();
    
    public abstract Number getTotal();
    
    protected String format(Number value) {
        return value + (unit.isEmpty() ? "" : " " + unit);
    }
    
    protected Number convert(Number value) {
        return value;
    }
    
    public String format(Map.Entry<Calendar, Number> data) {
        return CalendarFormat.format(data.getKey()) + ": " + format(data.getValue());
    }
    
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ReportNumericSeries && equals(((ReportNumericSeries) obj).name);
    }
    
    public boolean equals(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    @Override
    public int compareTo(ReportNumericSeries element) {
        return orderNumber - element.orderNumber;
    }

    @Override
    public Iterator<Map.Entry<Calendar, Number>> iterator() {
        return getSeries().entrySet().iterator();
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
    
}
