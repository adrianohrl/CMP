/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports.filters;

import cmp.model.events.EntryEvent;
import cmp.model.production.Model;
import cmp.util.AbstractFilter;

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
