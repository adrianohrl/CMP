/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports;

import br.com.ceciliaprado.cmp.model.personnel.Employee;
import java.text.DecimalFormat;
import java.util.function.Function;

/**
 *
 * @author adrianohrl
 */
public class ReportDoubleSeries extends ReportNumericSeries {
    
    private double conversionFactor = 1.0;
    private final DecimalFormat formatter = new DecimalFormat("#0.00");
   
    public ReportDoubleSeries(int orderNumber, String name, Employee employee, AbstractProductionReport report, String unit, Function<EmployeeEventsPeriodBuilder, Number> function) {
        super(orderNumber, name, report.getStartDate(), report.getEndDate(), unit, report.getBuilder().get(employee), function);
    }
    
    public ReportDoubleSeries(int orderNumber, String name, Employee employee, AbstractProductionReport report, String unit, Function<EmployeeEventsPeriodBuilder, Number> function, double conversionFactor) {
        super(orderNumber, name, report.getStartDate(), report.getEndDate(), unit, report.getBuilder().get(employee), function);
        this.conversionFactor = conversionFactor;
    }

    @Override
    public Double getTotal() {
        return builder != null ? convert(function.apply(builder)) : 0.0;
    }

    @Override
    protected Double convert(Number value) {
        return value.doubleValue() * conversionFactor;
    }

    @Override
    protected String format(Number value) {
        return formatter.format(value) + (unit.isEmpty() ? "" : " " + unit);
    }
    
    public void setUnit(String unit, double conversionFactor) {
        this.unit = unit;
        this.conversionFactor = conversionFactor;
    }

    @Override
    protected Double zero() {
        return 0.0;
    }
    
}