/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.production;

import cmp.dao.DataSource;
import cmp.exceptions.ProductionException;
import cmp.model.production.Model;
import cmp.model.production.Phase;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionOrder;
import cmp.util.Keyboard;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ProductionTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        ProductionMenuOptions option = ProductionMenuOptions.getOption();
        while (!option.quit()) {
            try {
                ProductionTest.process(option);
            } catch (RuntimeException e) {
                System.out.println("Exception catched: " + e.getMessage());
            }
            option = ProductionMenuOptions.getOption();
        }
        System.out.println("Quitting!!!");
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void process(ProductionMenuOptions option) {
        if (option.quit()) {
            return;
        }
        switch (option) {
            case REGISTER_PHASE:
                ProductionTest.createPhase();
                break;
            case REGISTER_MODEL:
                ProductionTest.createModel();
                break;
            case REGISTER_PRODUCTION_ORDER:
                ProductionTest.createProductionOrder();
                break;
            case REGISTER_PHASE_PRODUCTION_ORDER:
                ProductionTest.createPhaseProductionOrder();
                break;
            case SHOW_ALL_PHASES:
                ProductionTest.showAllRegisteredPhases();
                break;
            case SHOW_ALL_MODELS:
                ProductionTest.showAllRegisteredModels();
                break;
            case SHOW_ALL_PRODUCTION_ORDERS:
                ProductionTest.showAllRegisteredProductionOrders();
                break;
            case SHOW_ALL_PHASE_PRODUCTION_ORDERS:
                ProductionTest.showAllRegisteredPhaseProductionOrders();
                break;
            case SHOW_ALL_PENDENT_PHASE_PRODUCTION_ORDERS:
                ProductionTest.showAllRegisteredPendentPhaseProductionOrders();
                break;
            case ASSIGN_NEW_PHASES:
                ProductionTest.assignNewPhasesToModel();
                break;
            default:
                System.out.println("Invalid option!!!");
        }
    }
    
    public static void registerPhases(Collection<Phase> phases) {
        for (Phase phase : phases) {
            ProductionTest.register(phase);
        }
    }
    
    public static void register(Phase phase) {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        phaseDAO.create(phase);
    }
    
    public static void registerModels(Collection<Model> models) {
        for (Model model : models) {
            ProductionTest.register(model);
        }
    }
    
    public static void register(Model model) {
        ModelDAO modelDAO = new ModelDAO(em);
        modelDAO.create(model);
    }
    
    public static void registerProductionOrders(Collection<ProductionOrder> productionOrders) {
        for (ProductionOrder productionOrder : productionOrders) {
            ProductionTest.register(productionOrder);
        }
    }
    
    public static void register(ProductionOrder productionOrder) {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        productionOrderDAO.create(productionOrder);
    }
    
    public static void registerPhaseProductionOrders(Collection<PhaseProductionOrder> phaseProductionOrders) {
        for (PhaseProductionOrder phaseProductionOrder : phaseProductionOrders) {
            ProductionTest.register(phaseProductionOrder);
        }
    }
    
    public static void register(PhaseProductionOrder phaseProductionOrder) {
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        phaseProductionOrderDAO.create(phaseProductionOrder);
    }
    
    public static void showAllRegisteredPhases() {
        System.out.println("Showing all registered phases ...");
        PhaseDAO phaseDAO = new PhaseDAO(em);
        for (Phase phase : phaseDAO.findAll()) {
            System.out.println("Phase: " + phase);
        }
    }
    
    public static void showAllRegisteredModels() {
        System.out.println("Showing all registered models ...");
        ModelDAO modelDAO = new ModelDAO(em);
        for (Model model : modelDAO.findAll()) {
            System.out.println("Model: " + model);
            System.out.println("\tPhases:");
            for (Phase phase : model.getPhases()) {
                System.out.println("\t\t" + phase);
            }
        }
    }
    
    public static void showAllRegisteredProductionOrders() {
        System.out.println("Showing all registered production orders ...");
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        for (ProductionOrder productionOrder : productionOrderDAO.findAll()) {
            System.out.println("Production Order: " + productionOrder);
            System.out.println("\tModel:" + productionOrder.getModel());
        }
    }
    
    public static void showAllRegisteredPhaseProductionOrders() {
        System.out.println("Showing all registered phase production orders ...");
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        for (PhaseProductionOrder phaseProductionOrder : phaseProductionOrderDAO.findAll()) {
            System.out.println("Phase Production Order: " + phaseProductionOrder);
        }
    }

    public static void showAllRegisteredPendentPhaseProductionOrders() {
        System.out.println("Showing all registered pendent phase production orders ...");
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        for (PhaseProductionOrder phaseProductionOrder : phaseProductionOrderDAO.findPendents()) {
            System.out.println("Phase Production Order: " + phaseProductionOrder);
        }
    }

    private static void createPhase() {
        System.out.println("\nRegistering a new phase ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new phase below:");
        String name = keyboard.readString("name: ");
        double expectedDuration = keyboard.readDouble("expected duration (in [min]): ");
        try {
            ProductionTest.register(new Phase(name, expectedDuration));
            System.out.println("The phase registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The phase registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }

    private static void createModel() {
        System.out.println("\nRegistering a new model ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new model below:");
        String name = keyboard.readString("name: ");
        String reference = keyboard.readString("reference: ");
        try {
            Model model = new Model(name, reference);
            ProductionTest.register(model);
            ProductionTest.assignNewPhasesToModel(model);
            System.out.println("The model registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The model registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }

    private static void createProductionOrder() {
        System.out.println("\nResgistering a new production order ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new model below:");
        String produtionOrder = keyboard.readString("P.O.: ");
        System.out.println("Enter its model:");
        Model model = ProductionKeyboardEntries.selectOneModel();
        try {
            ProductionTest.register(new ProductionOrder(produtionOrder, model));
            System.out.println("The production order registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The production order registration failed: " + e.getMessage() + "!!!");
            em.clear();
        }
    }

    private static void createPhaseProductionOrder() {
        System.out.println("\nResgistering a new phase production order ...");
        Keyboard keyboard = Keyboard.getKeyboard();
        System.out.println("Enter the info of the new phase production order below:");
        System.out.println("Enter its phase:");
        Phase phase = ProductionKeyboardEntries.selectOnePhase();
        System.out.println("Enter its production order:");
        ProductionOrder productionOrder = ProductionKeyboardEntries.selectOneProductionOrder();
        int totalQuantity = keyboard.readInteger("total quantity: ");
        try {
            ProductionTest.register(new PhaseProductionOrder(phase, productionOrder, totalQuantity));
            System.out.println("The phase production order registration succeeded!!!");
        } catch (RuntimeException e) {
            System.out.println("The phase production order registration failed: " + e.getMessage() + "!!!");
            em.clear();
        } catch (ProductionException pe) {
            System.out.println("The phase production order registration failed: " + pe.getMessage());
        }
    }

    private static void assignNewPhasesToModel() {
        System.out.println("\nAssigning new phases to a model ...");
        System.out.println("Select one model:");
        Model model = ProductionKeyboardEntries.selectOneModel();
        assignNewPhasesToModel(model);
    }

    private static void assignNewPhasesToModel(Model model) {
        List<Phase> phases = ProductionKeyboardEntries.selectManyPhases(model);
        if (phases == null) {
            return;
        }
        model.getPhases().addAll(phases);
        ModelDAO modelDAO = new ModelDAO(em);
        try {
            modelDAO.update(model);
        } catch (RuntimeException e) {
            System.out.println("Exception catched: " + e.getMessage());
            em.clear();
        } 
    }
    
}
