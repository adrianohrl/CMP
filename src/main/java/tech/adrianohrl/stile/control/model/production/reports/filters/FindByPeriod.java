/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.util.AbstractFilter;
import java.util.Calendar;
import java.util.Date;
import tech.adrianohrl.util.CalendarUtil;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
public class FindByPeriod<T extends AbstractEmployeeRelatedEvent> extends AbstractFilter<T> {
    
    private final Calendar start;
    private final Calendar end;

    public FindByPeriod(Date start, Date end) {
        if (start.after(end)) {
            throw new RuntimeException("The start date must be before the end one!!!");
        }
        this.start = CalendarUtil.now();
        this.start.setTime(start);
        this.end = CalendarUtil.now();
        this.end.setTime(end);
    }

    public FindByPeriod(Calendar start, Calendar end) {
        if (start.after(end)) {
            throw new RuntimeException("The start date must be before the end one!!!");
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public void execute(T event) {
        if (start.after(event.getEventDate()) || end.before(event.getEventDate())) {
            return;
        }
        super.add(event);
    }
    
    @Override
    public EmployeeRelatedEventsList<T> getItems() {
        return new EmployeeRelatedEventsList(super.getItems());
    }
    
}
