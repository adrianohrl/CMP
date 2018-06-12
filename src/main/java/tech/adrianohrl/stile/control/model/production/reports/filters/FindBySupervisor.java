package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import tech.adrianohrl.util.AbstractFilter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FindBySupervisor extends AbstractFilter<EntryEvent> {
    
    private final Supervisor supervisor;

    public FindBySupervisor(Supervisor supervisor) {
        if (supervisor == null) {
            throw new RuntimeException("A non-null supervisor object is necessary for filtering!!!");
        }
        this.supervisor = supervisor;
    }

    @Override
    public void execute(EntryEvent entryEvent) {
        if (supervisor.equals(entryEvent.getSupervisor())) {
            super.add(entryEvent);
        }
    }
    
}
