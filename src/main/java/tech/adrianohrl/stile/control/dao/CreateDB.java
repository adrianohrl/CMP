package tech.adrianohrl.stile.control.dao;

import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class CreateDB {

    public static void main(String[] args) {
        EntityManager em = DataSource.createEntityManager();
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}
