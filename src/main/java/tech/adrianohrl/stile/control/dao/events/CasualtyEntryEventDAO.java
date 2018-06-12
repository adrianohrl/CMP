package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.model.events.CasualtyEntryEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CasualtyEntryEventDAO extends EntryEventDAO<CasualtyEntryEvent> {

    public CasualtyEntryEventDAO(EntityManager em) {
        super(em, CasualtyEntryEvent.class);
    }
    
}
