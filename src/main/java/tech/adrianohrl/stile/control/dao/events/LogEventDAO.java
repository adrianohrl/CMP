package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.model.events.LogEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class LogEventDAO extends AbstractEmployeeRelatedEventDAO<LogEvent> {

    public LogEventDAO(EntityManager em) {
        super(em, LogEvent.class);
    }
    
}
