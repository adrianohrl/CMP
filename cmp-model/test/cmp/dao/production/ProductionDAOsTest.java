/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.production;

import cmp.control.dao.DataSource;
import cmp.exceptions.ProductionException;
import cmp.model.production.Model;
import cmp.model.production.Phase;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ProductionDAOsTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    private static Map<String, Phase> phases;
    private static Map<String, Model> models;
    private static Map<String, ProductionOrder> productionOrders;
    private static List<PhaseProductionOrder> phaseProductionOrders;
    
    public static void main(String[] args) {
        try {
            ProductionDAOsTest.createPhases();
            ProductionTest.registerPhases(phases.values());
            ProductionDAOsTest.createModels();
            ProductionTest.registerModels(models.values());
            ProductionDAOsTest.createProductionOrders();
            ProductionTest.registerProductionOrders(productionOrders.values());
            ProductionDAOsTest.createPhaseProductionOrders();
            ProductionTest.registerPhaseProductionOrders(phaseProductionOrders);
            ProductionTest.showAllRegisteredPhases();
            ProductionTest.showAllRegisteredModels();
            ProductionTest.showAllRegisteredProductionOrders();
            ProductionTest.showAllRegisteredPhaseProductionOrders();
            ProductionTest.showAllRegisteredPendentPhaseProductionOrders();
        } catch (RuntimeException e) {
            System.out.println("Exception catched: " + e.getMessage());
        } catch (ProductionException pe) {
            System.out.println("Production exception catched: " + pe.getMessage());
        } finally {
            em.close();
            DataSource.closeEntityManagerFactory();
        }
    }

    private static void createPhases() {
        phases = new HashMap<>();
        phases.put("Overloque", new Phase("Overloque", 3.8));
        phases.put("Reta", new Phase("Reta", 0.9));
        phases.put("Travete", new Phase("Travete", 2.4));
        phases.put("Crochê", new Phase("Crochê", 30.7));
        phases.put("Galoneira", new Phase("Galoneira", 4.3));
        phases.put("Zíper", new Phase("Zíper", 1.9));
        phases.put("Etiqueta", new Phase("Etiqueta", 1.6));
    }

    private static void createModels() {
        models = new HashMap<>();
        Model model = new Model("Blusa 2017", "Blusa 2017");
        model.getPhases().add(phases.get("Overloque"));
        models.put(model.getReference(), model);
        model = new Model("Cacharrel 2017", "Cacharrel 2017");
        model.getPhases().add(phases.get("Overloque"));
        model.getPhases().add(phases.get("Reta"));
        model.getPhases().add(phases.get("Travete"));
        models.put(model.getReference(), model);
    }

    private static void createProductionOrders() {
        productionOrders = new HashMap<>();
        productionOrders.put("Blusa PP", new ProductionOrder("Blusa PP", models.get("Blusa 2017")));
        productionOrders.put("Blusa P", new ProductionOrder("Blusa P", models.get("Blusa 2017")));
        productionOrders.put("Blusa M", new ProductionOrder("Blusa M", models.get("Blusa 2017")));
        productionOrders.put("Blusa G", new ProductionOrder("Blusa G", models.get("Blusa 2017")));
        productionOrders.put("Cacharrel P", new ProductionOrder("Cacharrel P", models.get("Cacharrel 2017")));
        productionOrders.put("Cacharrel M", new ProductionOrder("Cacharrel M", models.get("Cacharrel 2017")));
        productionOrders.put("Cacharrel G", new ProductionOrder("Cacharrel G", models.get("Cacharrel 2017")));
        productionOrders.put("Cacharrel GG", new ProductionOrder("Cacharrel GG", models.get("Cacharrel 2017")));
    }

    private static void createPhaseProductionOrders() throws ProductionException {
        phaseProductionOrders = new ArrayList<>();
        phaseProductionOrders.add(new PhaseProductionOrder(phases.get("Overloque"), productionOrders.get("Blusa M"), 20));
        phaseProductionOrders.add(new PhaseProductionOrder(phases.get("Overloque"), productionOrders.get("Blusa G"), 10));
        phaseProductionOrders.add(new PhaseProductionOrder(phases.get("Overloque"), productionOrders.get("Cacharrel G"), 15));
        phaseProductionOrders.add(new PhaseProductionOrder(phases.get("Overloque"), productionOrders.get("Cacharrel G"), 25));
        phaseProductionOrders.add(new PhaseProductionOrder(phases.get("Travete"), productionOrders.get("Cacharrel G"), 45));
        phaseProductionOrders.add(new PhaseProductionOrder(phases.get("Travete"), productionOrders.get("Cacharrel G"), 25));
        phaseProductionOrders.add(new PhaseProductionOrder(phases.get("Reta"), productionOrders.get("Cacharrel G"), 70));
        phaseProductionOrders.add(new PhaseProductionOrder(phases.get("Reta"), productionOrders.get("Cacharrel G"), 20));
    }
    
}
