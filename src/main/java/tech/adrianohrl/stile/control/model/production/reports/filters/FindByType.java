package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.AbstractEmployeeRelatedEvent;
import tech.adrianohrl.util.AbstractFilter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public class FindByType<T extends AbstractEmployeeRelatedEvent> extends AbstractFilter<AbstractEmployeeRelatedEvent> {
    
    private final String type;
    private final Class<T> clazz;
    
    public FindByType(Class<T> clazz) {
        this.clazz = clazz;
        this.type = clazz.getName();
    }

    @Override
    public void execute(AbstractEmployeeRelatedEvent item) {
        if (type.equals(item.getClass().getName())) {
            super.add(item);
        }
    }
    
    @Override
    public EmployeeRelatedEventsList<AbstractEmployeeRelatedEvent> getItems() {
        return new EmployeeRelatedEventsList<>(super.getItems());
    }
    
}
