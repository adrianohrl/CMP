/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production;

import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.DataSource;
import tech.adrianohrl.stile.model.production.Model;

/**
 *
 * @author adrianohrl
 */
public class ModelDAOTest {
    
    public static void main(String[] args) {
        EntityManager em = DataSource.createEntityManager();
        ModelDAO dao = new ModelDAO(em);
        List<Model> models = dao.findAll();
        System.out.println("Models:");
        for (Model model : models) {
            System.out.println("\t" + model);
        }        
        models = dao.findAllArchived();
        System.out.println("Archived Models:");
        for (Model model : models) {
            System.out.println("\t" + model);
        }
        models = dao.findAllNonArchived();
        System.out.println("Non Archived Models:");
        for (Model model : models) {
            System.out.println("\t" + model);
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}
