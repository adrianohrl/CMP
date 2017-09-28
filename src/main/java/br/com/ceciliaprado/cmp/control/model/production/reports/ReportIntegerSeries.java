/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.model.personnel.Employee;
import java.util.function.Function;

/**
 *
 * @author adrianohrl
 */
public class ReportIntegerSeries extends ReportNumericSeries {

    public ReportIntegerSeries(int orderNumber, String name, Employee employee, AbstractProductionReport report, String unit, Function<EmployeeEventsPeriodBuilder, Number> function) {
        super(orderNumber, name, report.getStartDate(), report.getEndDate(), unit, report.getBuilder().get(employee), function);
    }

    @Override
    public Integer getTotal() {
        return builder != null ? function.apply(builder).intValue() : 0;
    }

    @Override
    protected Integer zero() {
        return 0;
    }
    
}