package tech.adrianohrl.dao;

import javax.persistence.EntityManager;
import tech.adrianohrl.util.PropertyUtil;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CreateDB {

    public static void main(String[] args) {
        System.out.println("persistence.unit.name=" + PropertyUtil.getPersistenceUnitName());
        EntityManager em = DataSource.createEntityManager();
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}
