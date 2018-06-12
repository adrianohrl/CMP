package tech.adrianohrl.stile.control.dao.personnel;

import tech.adrianohrl.stile.model.personnel.Loggable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <L>
 */
public class LoggableDAO<L extends Loggable> extends EmployeeDAO<L> {

    public LoggableDAO(EntityManager em) {
        super(em);
    }
    
    protected LoggableDAO(EntityManager em, Class clazz) {
        super(em, clazz);
    }

    @Override
    public L find(String login) {
        L loggable = super.find(login);
        if (loggable == null) {
            try {
                loggable = (L) em.createQuery("SELECT e "
                    + "FROM Employee e "
                    + "WHERE e.login = '" + login + "'").getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
        return loggable;
    }
    
}
