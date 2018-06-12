package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.model.personnel.Employee;
import java.util.function.Function;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
