package tech.adrianohrl.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import tech.adrianohrl.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class DataSource {
    
    private static final String pu = PropertyUtil.getPersistenceUnitName();
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory(pu);

    private DataSource() {
    }

    public static EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
    
    public static void closeEntityManagerFactory() {
        emf.close();
    }
    
}
