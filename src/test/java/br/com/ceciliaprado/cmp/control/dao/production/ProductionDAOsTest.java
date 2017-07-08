/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production;

import br.com.ceciliaprado.cmp.control.dao.DataSource;
import br.com.ceciliaprado.cmp.control.dao.personnel.SectorDAO;
import br.com.ceciliaprado.cmp.exceptions.ProductionException;
import br.com.ceciliaprado.cmp.model.personnel.Sector;
import br.com.ceciliaprado.cmp.model.production.Model;
import br.com.ceciliaprado.cmp.model.production.ModelPhase;
import br.com.ceciliaprado.cmp.model.production.Phase;
import br.com.ceciliaprado.cmp.model.production.PhaseProductionOrder;
import br.com.ceciliaprado.cmp.model.production.ProductionOrder;
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
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        String sectorName = "Costura";
        System.out.println(sectorName + "'s pendent phase production orders:");
        for (PhaseProductionOrder phaseProductionOrder : phaseProductionOrderDAO.findPendents(sectorName)) {
            System.out.println("\t" + phaseProductionOrder);
        }
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
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }

    private static void createPhases() {
        SectorDAO sectorDAO = new SectorDAO(em);
        Sector costura = sectorDAO.find("Costura");
        phases = new HashMap<>();
        phases.put("Overloque", new Phase("Overloque", costura));
        phases.put("Reta", new Phase("Reta", costura));
        phases.put("Travete", new Phase("Travete", costura));
        phases.put("Crochê", new Phase("Crochê", costura));
        phases.put("Galoneira", new Phase("Galoneira", costura));
        phases.put("Zíper", new Phase("Zíper", costura));
        phases.put("Etiqueta", new Phase("Etiqueta", costura));
    }

    private static void createModels() {
        models = new HashMap<>();
        Model model = new Model("Blusa 2017", "Blusa 2017");
        model.getPhases().add(new ModelPhase(phases.get("Overloque"), 3.8));
        models.put(model.getReference(), model);
        model = new Model("Cacharrel 2017", "Cacharrel 2017");
        model.getPhases().add(new ModelPhase(phases.get("Overloque"), 3.5));
        model.getPhases().add(new ModelPhase(phases.get("Reta"), 0.9));
        model.getPhases().add(new ModelPhase(phases.get("Travete"), 2.4));
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
        Model model = models.get("Blusa 2017");
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Overloque")), productionOrders.get("Blusa M"), 20));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Overloque")), productionOrders.get("Blusa G"), 10));
        model = models.get("Cacharrel 2017");
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Overloque")), productionOrders.get("Cacharrel G"), 15));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Overloque")), productionOrders.get("Cacharrel G"), 25));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Travete")), productionOrders.get("Cacharrel G"), 45));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Travete")), productionOrders.get("Cacharrel G"), 25));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Reta")), productionOrders.get("Cacharrel G"), 70));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Reta")), productionOrders.get("Cacharrel G"), 20));
    }
    
}
