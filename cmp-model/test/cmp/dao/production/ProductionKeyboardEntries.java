/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.production;

import cmp.control.dao.production.PhaseDAO;
import cmp.control.dao.production.ProductionOrderDAO;
import cmp.control.dao.production.ModelDAO;
import cmp.control.dao.production.PhaseProductionOrderDAO;
import cmp.control.dao.DataSource;
import cmp.exceptions.ProductionException;
import cmp.model.production.Model;
import cmp.model.production.Phase;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionOrder;
import cmp.model.production.ProductionStates;
import cmp.util.KeyboardEntries;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ProductionKeyboardEntries {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static Phase selectOnePhase() {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        List<Phase> phases = phaseDAO.findAll();
        return KeyboardEntries.selectOne(phases, "phase");
    }
    
    public static Phase selectOnePhase(Model model) {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        List<Phase> phases = phaseDAO.findAll();
        if (phases.isEmpty()) {
            System.out.println("There is no phase registered yet!!!");
            return null;
        }
        phases.removeAll(model.getPhases());
        return KeyboardEntries.selectOne(phases, "phase that does not belongs to this model");
    }
    
    public static Phase selectOnePhaseOfModel(String modelName) {
        ModelDAO modelDAO = new ModelDAO(em);
        Model model = modelDAO.find(modelName);
        if (model == null) {
            System.out.println("There is no model named " + modelName + " registered yet!!!");
            return null;
        }
        return KeyboardEntries.selectOne(model.getPhases(), "phase of this model");
    }
    
    public static Model selectOneModel() {
        ModelDAO modelDAO = new ModelDAO(em);
        List<Model> models = modelDAO.findAll();
        return KeyboardEntries.selectOne(models, "model");
    }
    
    public static ProductionOrder selectOneProductionOrder() {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        List<ProductionOrder> productionOrders = productionOrderDAO.findAll();
        return KeyboardEntries.selectOne(productionOrders, "production order");
    }
    
    public static PhaseProductionOrder selectOnePhaseProductionOrder() {
        PhaseProductionOrderDAO phaseProductionOrderDAO = new PhaseProductionOrderDAO(em);
        boolean brandNew = KeyboardEntries.askForYesOrNo("Is this a new phase production order?");
        if (!brandNew) {
            List<PhaseProductionOrder> phaseProductionOrders = phaseProductionOrderDAO.findPendents();
            return KeyboardEntries.selectOne(phaseProductionOrders, "pendent phase production order");
        }
        ProductionOrder productionOrder = ProductionKeyboardEntries.selectOneProductionOrder();
        Phase phase = ProductionKeyboardEntries.selectOnePhaseOfModel(productionOrder.getModel().getName());
        PhaseProductionOrder phaseProductionOrder = phaseProductionOrderDAO.find(phase, productionOrder);
        if (phaseProductionOrder == null) {
            int totalQuantity = KeyboardEntries.askForPositiveInteger("total quantity: ");
            try {
                phaseProductionOrder = new PhaseProductionOrder(phase, productionOrder, totalQuantity);
            } catch (ProductionException pe) {
                System.out.println("Exception catched: " + pe.getMessage());
            }
        } else if (!phaseProductionOrder.isPendent()) {
            System.out.println("This phase production order already exists, but it is not pendent anymore!!!");
            return null;
        }
        return phaseProductionOrder;
    }
    
    public static ProductionStates selectOneRestartedOrPausedState() {
        List<ProductionStates> states = new ArrayList<>();
        states.add(ProductionStates.RESTARTED);
        states.add(ProductionStates.PAUSED);
        return KeyboardEntries.selectOne(states, "production state");
    }
    
    public static List<Phase> selectManyPhases() {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        List<Phase> phases = phaseDAO.findAll();
        return KeyboardEntries.selectMany(phases, "phase");
    }
    
    public static List<Phase> selectManyPhases(Model model) {
        PhaseDAO phaseDAO = new PhaseDAO(em);
        List<Phase> phases = phaseDAO.findAll();
        if (phases.isEmpty()) {
            System.out.println("There is no phase registered yet!!!");
            return null;
        }
        phases.removeAll(model.getPhases());
        return KeyboardEntries.selectMany(phases, "phase that does not belongs to this model");
    }
    
    public static List<Phase> selectManyPhasesOfModel(String modelName) {
        ModelDAO modelDAO = new ModelDAO(em);
        Model model = modelDAO.find(modelName);
        if (model == null) {
            System.out.println("There is no model named " + modelName + " registered yet!!!");
            return null;
        }
        return KeyboardEntries.selectMany(model.getPhases(), "phase of this model");
    }
    
    public static List<Model> selectManyModels() {
        ModelDAO modelDAO = new ModelDAO(em);
        List<Model> models = modelDAO.findAll();
        return KeyboardEntries.selectMany(models, "model");
    }
    
    public static List<ProductionOrder> selectManyProductionOrder() {
        ProductionOrderDAO productionOrderDAO = new ProductionOrderDAO(em);
        List<ProductionOrder> productionOrders = productionOrderDAO.findAll();
        return KeyboardEntries.selectMany(productionOrders, "production order");
    }
    
}
