package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.util.AbstractFilter;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class FindBySector extends AbstractFilter<EntryEvent> {
    
    private final Sector sector;

    public FindBySector(Sector sector) {
        if (sector == null) {
            throw new RuntimeException("A non-null sector object is necessary for filtering!!!");
        }
        this.sector = sector;
    }

    @Override
    public void execute(EntryEvent entryEvent) {
        if (sector.equals(entryEvent.getSector())) {
            super.add(entryEvent);
        }
    }
    
}
