package tech.adrianohrl.stile.control.model.production.reports;

import tech.adrianohrl.stile.control.model.production.reports.filters.EmployeeRelatedEventsList;
import tech.adrianohrl.stile.exceptions.ReportException;
import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.stile.model.personnel.Manager;
import tech.adrianohrl.stile.model.personnel.Subordinate;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class SubordinateEfficiencyReport extends AbstractEfficiencyReport<EfficiencySeriesTypes> {
    
    private final Subordinate subordinate;
    
    public SubordinateEfficiencyReport(Subordinate subordinate, EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> events, Manager manager, Calendar startDate, Calendar endDate) throws ReportException {
        super(events, manager, startDate, endDate);
        if (subordinate == null) {
            throw new ReportException("The report subordinate must not be null!!!");
        }
        this.subordinate = subordinate;
    }

    public Subordinate getSubordinate() {
        return subordinate;
    }

    @Override
    protected Map<EfficiencySeriesTypes, ReportNumericSeries<EfficiencySeriesTypes>> getSeriesMap() {
        return super.getSeriesMap(subordinate);
    }
    
}
