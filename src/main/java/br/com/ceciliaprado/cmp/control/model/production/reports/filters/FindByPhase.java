/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports.filters;

import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.production.Phase;
import br.com.ceciliaprado.cmp.util.AbstractFilter;

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
