package tech.adrianohrl.stile.control.production.reports;

import tech.adrianohrl.stile.control.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.control.production.reports.filters.FindByEmployee;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class MultipleSubordinateEfficiencyReport extends AbstractEfficiencyReport {

    private final List<SubordinateEfficiencyReport> subordinateEfficiencyReports = new ArrayList<>();

    public MultipleSubordinateEfficiencyReport(List<Subordinate> subordinates, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        for (Subordinate subordinate : subordinates) {
            FindByEmployee filter = new FindByEmployee<>(subordinate);
            events.execute(filter);
            subordinateEfficiencyReports.add(new SubordinateEfficiencyReport(subordinate, filter.getItems(), manager, startDate, endDate));
        }
    }

    @Override
    protected TreeMap<SeriesType, ReportNumericSeries> getSeriesMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
