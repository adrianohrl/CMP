package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.events.AbstractEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <E>
 */
public class AbstractEventDAO<E extends AbstractEvent> extends DAO<E, Long> {

    protected AbstractEventDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }

    @Override
    public boolean isRegistered(E entity) {
        return super.find(entity.getCode()) != null;
    }
    
}
