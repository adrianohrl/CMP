package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.control.model.production.reports.filters.FindByPeriod;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.util.CalendarFormat;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <S>
 */
public abstract class ReportNumericSeries<S extends SeriesType> implements Iterable<Map.Entry<Calendar, Number>>, Comparable<Enum> {
    
    private final S type;
    private final Calendar startDate;
    private final Calendar endDate;
    protected String unit;
    protected final EmployeeEventsPeriodBuilder builder;
    protected final Function<EmployeeEventsPeriodBuilder, Number> function;

    public ReportNumericSeries(S type, Calendar startDate, 
            Calendar endDate, String unit, EmployeeEventsPeriodBuilder builder, 
            Function<EmployeeEventsPeriodBuilder, Number> function) {
        this.type = type;
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
        Map<Object, Number> series = new LinkedHashMap<>();
        for (Map.Entry<Calendar, Number> data : this) {
            series.put(formatter.format(data.getKey().getTime()), data.getValue());
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
        return CalendarFormat.formatDate(data.getKey()) + ": " + format(data.getValue());
    }
    
    @Override
    public String toString() {
        return type.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof ReportNumericSeries && equals(((ReportNumericSeries) obj).type);
    }
    
    public boolean equals(S type) {
        return equals(type.getName());
    }
    
    public boolean equals(String name) {
        return this.type.equals(name);
    }

    @Override
    public int compareTo(Enum type) {
        return this.type.compareTo(type);
    }

    @Override
    public Iterator<Map.Entry<Calendar, Number>> iterator() {
        return getSeries().entrySet().iterator();
    }

    public S getType() {
        return type;
    }

    public String getName() {
        return type.getName();
    }

    public String getUnit() {
        return unit;
    }
    
}
