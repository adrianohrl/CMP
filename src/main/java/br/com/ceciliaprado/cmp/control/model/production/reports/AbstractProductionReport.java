/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.exceptions.ReportException;
import br.com.ceciliaprado.cmp.model.personnel.Manager;
import br.com.ceciliaprado.cmp.util.CalendarFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author adrianohrl
 */
public abstract class AbstractProductionReport {
    
    private final Manager manager;
    private final Calendar emissionDate = new GregorianCalendar();
    private final Calendar startDate;
    private final Calendar endDate;

    public AbstractProductionReport(Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        this.manager = manager;
        if (startDate.after(endDate)) {
            throw new ReportException("The report start date must be before its end date!!!");
        }
        if (startDate.after(emissionDate)) {
            throw new ReportException("The report start date must be before its emission date!!!");
        }
        this.startDate = startDate;
        if (endDate.after(emissionDate)) {
            throw new ReportException("The report end date must be before its emission date!!!");
        }
        this.endDate = endDate;
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
    
}
