package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.model.personnel.Employee;
import java.text.DecimalFormat;
import java.util.function.Function;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <S>
 */
public class ReportDoubleSeries<S extends SeriesType> extends ReportNumericSeries<S> {
    
    private double conversionFactor = 1.0;
    private final DecimalFormat formatter = new DecimalFormat("#0.00");
   
    public ReportDoubleSeries(S seriesEnum, Employee employee, AbstractProductionReport report, String unit, Function<EmployeeEventsPeriodBuilder, Number> function) {
        super(seriesEnum, report.getStartDate(), report.getEndDate(), unit, report.getBuilder().get(employee), function);
    }
    
    public ReportDoubleSeries(S seriesEnum, Employee employee, AbstractProductionReport report, String unit, Function<EmployeeEventsPeriodBuilder, Number> function, double conversionFactor) {
        super(seriesEnum, report.getStartDate(), report.getEndDate(), unit, report.getBuilder().get(employee), function);
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
    
    public void setUnit(String label, String unit, double conversionFactor) {
        this.unit = unit;
        this.conversionFactor = conversionFactor;
    }

    @Override
    protected Double zero() {
        return 0.0;
    }
    
}
