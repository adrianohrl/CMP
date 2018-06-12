package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.production.Phase;
import tech.adrianohrl.util.AbstractFilter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FindByPhase extends AbstractFilter<EntryEvent> {
    
    private final Phase phase;

    public FindByPhase(Phase phase) {
        if (phase == null) {
            throw new RuntimeException("A non-null phase object is necessary for filtering!!!");
        }
        this.phase = phase;
    }

    @Override
    public void execute(EntryEvent entryEvent) {
        if (phase.equals(entryEvent.getPhaseProductionOrder().getPhase())) {
            super.add(entryEvent);
        }
    }
    
}
