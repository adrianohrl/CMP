package tech.adrianohrl.stile.control.model.production.reports.filters;

import tech.adrianohrl.stile.model.events.AbstractEvent;
import tech.adrianohrl.util.Command;
import tech.adrianohrl.util.Execute;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
