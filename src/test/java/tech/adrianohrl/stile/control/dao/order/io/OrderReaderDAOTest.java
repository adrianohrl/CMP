package tech.adrianohrl.stile.control.dao.order.io;

import tech.adrianohrl.dao.DataSource;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.order.PhaseProductionOrder;
import tech.adrianohrl.stile.model.order.ProductionOrder;
import tech.adrianohrl.util.Keyboard;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class OrderReaderDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        OrderReaderDAOTest.test(em);
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void test(EntityManager em) {
        Keyboard keyboard = Keyboard.getKeyboard();
        ProductionOrdersReaderDAO productionOrdersReader = new ProductionOrdersReaderDAO(em);
        PhaseProductionOrdersReaderDAO phaseProductionOrdersReader = new PhaseProductionOrdersReaderDAO(em);
        String fileName;
        try {
            System.out.println("\n\nTesting the ProductionOrdersReaderDAO class ...");
            fileName = "./others/tests/ImportProductionOrders1.csv";//keyboard.readString("Enter the file name: ");
            productionOrdersReader.readFile(fileName);
            System.out.println("  The following production orders were registered:");
            for (ProductionOrder productionOrder : productionOrdersReader) {
                System.out.println("\t" + productionOrder);
            }
            System.out.println("\n\nTesting the PhaseProductionOrdersReaderDAO class ...");
            fileName = "./others/tests/ImportPhaseProductionOrders1.csv";//keyboard.readString("Enter the file name: ");
            phaseProductionOrdersReader.readFile(fileName);
            System.out.println("  The following phase production orders were registered:");
            for (PhaseProductionOrder phaseProductionOrder : phaseProductionOrdersReader) {
                System.out.println("\t" + phaseProductionOrder);
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
    
}
