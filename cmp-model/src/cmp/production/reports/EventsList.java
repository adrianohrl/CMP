/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.production.reports;

import cmp.model.events.AbstractEvent;
import cmp.util.Command;
import cmp.util.Execute;
import java.util.ArrayList;

/**
 *
 * @author adrianohrl
 * @param <T>
 */
public class EventsList<T extends AbstractEvent> extends ArrayList<T> implements Execute<T> {

    public EventsList() {
    }

    @Override
    public void execute(Command<T> command) {
        for (T item : this) {
            command.execute(item);
        }
    }
    
}
