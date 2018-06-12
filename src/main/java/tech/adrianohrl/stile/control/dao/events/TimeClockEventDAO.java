package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.model.events.TimeClockEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class TimeClockEventDAO extends AbstractEmployeeRelatedEventDAO<TimeClockEvent> {

    public TimeClockEventDAO(EntityManager em) {
        super(em, TimeClockEvent.class);
    }
    
}
