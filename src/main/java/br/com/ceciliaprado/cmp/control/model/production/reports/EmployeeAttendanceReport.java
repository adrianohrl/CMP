/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.control.model.production.reports.filters.EmployeeRelatedEventsList;
import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.events.AbstractEmployeeRelatedEvent;
import br.com.ceciliaprado.cmp.model.events.TimeClockEvent;
import br.com.ceciliaprado.cmp.model.personnel.Employee;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author adrianohrl
 */
public class EmployeeAttendanceReport extends AbstractAttendanceReport {
    
    private final Employee employee;

    public EmployeeAttendanceReport(Employee employee, List<TimeClockEvent> timeClockEvents, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(new EmployeeRelatedEventsList<>(timeClockEvents), manager, startDate, endDate);
        this.employee = employee;
    }

    public EmployeeAttendanceReport(Employee employee, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    @Override
    protected TreeMap<String, ReportNumericSeries> getSeriesMap() {
        return super.getSeriesMap(employee);
    }
    
}
