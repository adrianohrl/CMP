/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports.filters;

import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.personnel.Supervisor;
import br.com.ceciliaprado.cmp.util.AbstractFilter;

/**
 *
 * @author adrianohrl
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
