/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.util.AbstractFilter;

/**
 *
 * @author adrianohrl
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
