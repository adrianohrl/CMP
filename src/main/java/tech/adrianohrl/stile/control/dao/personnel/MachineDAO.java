package tech.adrianohrl.stile.control.dao.personnel;

import tech.adrianohrl.stile.control.dao.DAO;
import tech.adrianohrl.stile.model.personnel.Machine;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class MachineDAO extends DAO<Machine, String> {
    
    public MachineDAO(EntityManager em) {
        super(em, Machine.class);
    }

    @Override
    public boolean isRegistered(Machine entity) {
        return super.find(entity.getName()) != null;
    }
    
}
