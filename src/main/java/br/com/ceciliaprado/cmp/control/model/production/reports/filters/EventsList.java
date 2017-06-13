/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.model.production.reports.filters;

import br.com.ceciliaprado.cmp.model.events.AbstractEvent;
import br.com.ceciliaprado.cmp.util.Command;
import br.com.ceciliaprado.cmp.util.Execute;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
public class EventsList<T extends AbstractEvent> extends ArrayList<T> implements Execute<T> {

    public EventsList() {
    }

    public EventsList(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public void execute(Command<T> command) {
        for (T item : this) {
            command.execute(item);
        }
    }
    
}
