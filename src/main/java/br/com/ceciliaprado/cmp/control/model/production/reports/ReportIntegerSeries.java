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
 * @param <S>
 */
public class ReportIntegerSeries<S extends SeriesType> extends ReportNumericSeries<S> {

    public ReportIntegerSeries(S seriesEnum, Employee employee, AbstractProductionReport report, String unit, Function<EmployeeEventsPeriodBuilder, Number> function) {
        super(seriesEnum, report.getStartDate(), report.getEndDate(), unit, report.getBuilder().get(employee), function);
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
