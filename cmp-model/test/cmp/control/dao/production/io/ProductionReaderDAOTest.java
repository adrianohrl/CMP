/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.control.dao.production.io;

import cmp.control.dao.DataSource;
import cmp.exceptions.IOException;
import cmp.model.production.Model;
import cmp.model.production.Phase;
import cmp.model.production.PhaseProductionOrder;
import cmp.model.production.ProductionOrder;
import cmp.util.Keyboard;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class ProductionReaderDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        Keyboard keyboard = Keyboard.getKeyboard();
        PhasesReaderDAO phaseReader = new PhasesReaderDAO(em);
        ModelsReaderDAO modelsReader = new ModelsReaderDAO(em);
        ProductionOrdersReaderDAO productionOrdersReader = new ProductionOrdersReaderDAO(em);
        PhaseProductionOrdersReaderDAO phaseProductionOrdersReader = new PhaseProductionOrdersReaderDAO(em);
        String fileName;
        try {
            System.out.println("Testing the PhasesReaderDAO class ...");
            fileName = "../others/tests/ImportPhases1.csv";//keyboard.readString("Enter the file name: ");
            phaseReader.readFile(fileName);
            System.out.println("  The following phases were registered:");
            for (Phase phase : phaseReader) {
                System.out.println("\t" + phase);
            }
            System.out.println("\n\nTesting the ModelsReaderDAO class ...");
            fileName = "../others/tests/ImportModels1.csv";//keyboard.readString("Enter the file name: ");
            modelsReader.readFile(fileName);
            System.out.println("  The following models were registered:");
            for (Model model : modelsReader) {
                System.out.println("\t" + model);
            }
            System.out.println("\n\nTesting the ProductionOrdersReaderDAO class ...");
            fileName = "../others/tests/ImportProductionOrders1.csv";//keyboard.readString("Enter the file name: ");
            productionOrdersReader.readFile(fileName);
            System.out.println("  The following production orders were registered:");
            for (ProductionOrder productionOrder : productionOrdersReader) {
                System.out.println("\t" + productionOrder);
            }
            System.out.println("\n\nTesting the PhaseProductionOrdersReaderDAO class ...");
            fileName = "../others/tests/ImportPhaseProductionOrders1.csv";//keyboard.readString("Enter the file name: ");
            phaseProductionOrdersReader.readFile(fileName);
            System.out.println("  The following phase production orders were registered:");
            for (PhaseProductionOrder phaseProductionOrder : phaseProductionOrdersReader) {
                System.out.println("\t" + phaseProductionOrder);
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Exception catched: " + e.getMessage());
        }
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
}
