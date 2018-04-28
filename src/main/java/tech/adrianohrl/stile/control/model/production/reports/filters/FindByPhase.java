/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.EntryEvent;
import tech.adrianohrl.stile.model.production.Phase;
import tech.adrianohrl.stile.util.AbstractFilter;

/**
 *
 * @author adrianohrl
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
