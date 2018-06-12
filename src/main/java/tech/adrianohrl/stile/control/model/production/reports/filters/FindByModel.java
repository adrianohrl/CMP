package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.util.AbstractFilter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FindByModel extends AbstractFilter<EntryEvent> {
    
    private final Model model;

    public FindByModel(Model model) {
        if (model == null) {
            throw new RuntimeException("A non-null model object is necessary for filtering!!!");
        }
        this.model = model;
    }

    @Override
    public void execute(EntryEvent entryEvent) {
        if (model.equals(entryEvent.getPhaseProductionOrder().getProductionOrder().getModel())) {
            super.add(entryEvent);
        }
    }
}
