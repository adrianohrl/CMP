/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production;

import tech.adrianohrl.stile.control.dao.order.PhaseProductionOrderDAO;
import tech.adrianohrl.stile.control.dao.order.ProductionOrderDAO;
import tech.adrianohrl.stile.control.dao.DataSource;
import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.exceptions.ProductionException;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.ModelPhase;
import tech.adrianohrl.stile.model.production.Phase;
import tech.adrianohrl.stile.model.order.PhaseProductionOrder;
import tech.adrianohrl.stile.model.order.ProductionOrder;
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
        ProductionDAOsTest.test(em);
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void test(EntityManager em) {
        try {
            ProductionDAOsTest.createPhases();
            ProductionDAOsTest.createModels();
            ProductionDAOsTest.createProductionOrders();
            ProductionDAOsTest.createPhaseProductionOrders();
            ProductionTest.registerPhases(phases.values());
            ProductionTest.registerModels(models.values());
            ProductionTest.registerProductionOrders(productionOrders.values());
            ProductionTest.registerPhaseProductionOrders(phaseProductionOrders);
            ProductionTest.showAllRegisteredPhases();
            ProductionTest.showAllRegisteredModels();
            ProductionTest.showAllRegisteredProductionOrders();
            ProductionTest.showAllRegisteredPhaseProductionOrders();
            ProductionTest.showAllRegisteredPendentPhaseProductionOrders();
        } catch (RuntimeException | ProductionException e) {
            System.out.println(e.getClass().getSimpleName() + " caught: " + e.getMessage());
        }    
        
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        SectorDAO sectorDAO = new SectorDAO(em);
        Sector sector = sectorDAO.find("Costura");
        if (sector != null) {
            System.out.println("\n" + sector.getName() + "'s pendent phase production orders:");
            for (PhaseProductionOrder phaseProductionOrder : phaseProductionOrderDAO.findPendents(sector)) {
                System.out.println("\t" + phaseProductionOrder);
            }
        }
        
        String subordinateName = "Joana";
        PhaseProductionOrder phaseProductionOrder = phaseProductionOrderDAO.findCurrent(subordinateName);
        System.out.println("\n" + subordinateName + "'s current work is: " + phaseProductionOrder);
        
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        ProductionOrder productionOrder =  productionOrderDAO.find("Blusa G");
        if (productionOrder != null) {
            System.out.println("\n" + productionOrder.getReference() + "'s pendent phase production orders:");
            for (PhaseProductionOrder ppo : phaseProductionOrderDAO.findPendents(productionOrder)) {
                System.out.println("\t" + ppo);
            }
        }
        
        ModelDAO modelDAO = new ModelDAO(em);
        System.out.println("Registered models:");
        for (Model model : modelDAO.findAll()) {
            System.out.println("\t" + model);
        }
        
        System.out.println("\nTesting ModelDAO find method:");
        Model model = models.get("Blusa 2017");
        System.out.println("Searching " + model + " by reference (" + model.getReference() + "): " + (modelDAO.find(model.getReference()) != null ? "ok" : "not ok"));
        System.out.println("Searching " + model + " by name (" + model.getName() + "): " + (modelDAO.find(model.getName()) != null ? "ok" : "not ok"));
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
        Model model = new Model("Blusa 2017", "Blusinha 2017");
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
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Overloque")), productionOrders.get("Cacharrel GG"), 25));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Travete")), productionOrders.get("Cacharrel G"), 45));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Travete")), productionOrders.get("Cacharrel GG"), 25));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Reta")), productionOrders.get("Cacharrel G"), 70));
        phaseProductionOrders.add(new PhaseProductionOrder(model.getPhase(phases.get("Reta")), productionOrders.get("Cacharrel GG"), 20));
    }
    
}
