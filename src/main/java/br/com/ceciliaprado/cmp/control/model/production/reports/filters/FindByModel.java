/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports.filters;

import br.com.ceciliaprado.cmp.model.events.EntryEvent;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.util.AbstractFilter;

/**
 *
 * @author adrianohrl
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
