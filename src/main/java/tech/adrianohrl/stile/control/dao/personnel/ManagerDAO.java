package tech.adrianohrl.stile.control.dao.personnel;

import tech.adrianohrl.stile.model.personnel.Manager;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class ManagerDAO extends LoggableDAO<Manager> {

    public ManagerDAO(EntityManager em) {
        super(em, Manager.class);
    }
    
}
