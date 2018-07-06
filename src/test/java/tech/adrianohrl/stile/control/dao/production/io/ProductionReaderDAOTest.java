/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.production.io;

import tech.adrianohrl.dao.DataSource;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.production.Model;
import tech.adrianohrl.stile.model.production.Phase;
import tech.adrianohrl.util.Keyboard;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.model.production.Chart;
import tech.adrianohrl.stile.model.production.ChartSize;
import tech.adrianohrl.stile.model.production.Collection;
import tech.adrianohrl.stile.model.production.Family;
import tech.adrianohrl.stile.model.production.ModelPhase;

/**
 *
 * @author adrianohrl
 */
public class ProductionReaderDAOTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        ProductionReaderDAOTest.test(em);
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static void test(EntityManager em) {
        Keyboard keyboard = Keyboard.getKeyboard();
        CollectionsReaderDAO collectionReader = new CollectionsReaderDAO(em);
        FamiliesReaderDAO familyReader = new FamiliesReaderDAO(em);
        ChartsReaderDAO chartReader = new ChartsReaderDAO(em);
        ChartSizesReaderDAO sizeReader = new ChartSizesReaderDAO(em);
        PhasesReaderDAO phaseReader = new PhasesReaderDAO(em);
        ModelsReaderDAO modelsReader = new ModelsReaderDAO(em);
        ModelPhasesReaderDAO modelPhasesReader = new ModelPhasesReaderDAO(em);
        String fileName;
        try {
            System.out.println("Testing the CollectionsReaderDAO class ...");
            fileName = "./others/tests/ImportCollections1.csv";//keyboard.readString("Enter the file name: ");
            collectionReader.readFile(fileName);
            System.out.println("  The following collections were registered:");
            for (Collection collection : collectionReader) {
                System.out.println("\t" + collection);
            }
            System.out.println("Testing the FamiliesReaderDAO class ...");
            fileName = "./others/tests/ImportFamilies1.csv";//keyboard.readString("Enter the file name: ");
            familyReader.readFile(fileName);
            System.out.println("  The following families were registered:");
            for (Family family : familyReader) {
                System.out.println("\t" + family);
            }
            System.out.println("Testing the ChartsReaderDAO class ...");
            fileName = "./others/tests/ImportCharts1.csv";//keyboard.readString("Enter the file name: ");
            chartReader.readFile(fileName);
            System.out.println("  The following charts were registered:");
            for (Chart chart : chartReader) {
                System.out.println("\t" + chart);
            }
            System.out.println("Testing the ChartSizesReaderDAO class ...");
            fileName = "./others/tests/ImportChartSizes1.csv";//keyboard.readString("Enter the file name: ");
            sizeReader.readFile(fileName);
            System.out.println("  The following chart sizes were registered:");
            for (Chart chart : sizeReader) {
                System.out.println("\tChart: " + chart);
                for (ChartSize size : chart) {
                    System.out.println("\t\t" + size);
                }
            }
            System.out.println("Testing the PhasesReaderDAO class ...");
            fileName = "./others/tests/ImportPhases1.csv";//keyboard.readString("Enter the file name: ");
            phaseReader.readFile(fileName);
            System.out.println("  The following phases were registered:");
            for (Phase phase : phaseReader) {
                System.out.println("\t" + phase);
            }
            System.out.println("\n\nTesting the ModelsReaderDAO class ...");
            fileName = "./others/tests/ImportModels1.csv";//keyboard.readString("Enter the file name: ");
            modelsReader.readFile(fileName);
            System.out.println("  The following models were registered:");
            for (Model model : modelsReader) {
                System.out.println("\t" + model);
            }
            System.out.println("\n\nTesting the ModelPhasesReaderDAO class ...");
            fileName = "./others/tests/ImportModelPhases1.csv";//keyboard.readString("Enter the file name: ");
            modelPhasesReader.readFile(fileName);
            System.out.println("  The following models were registered:");
            for (Model model : modelPhasesReader) {
                System.out.println("\t" + model);
                for (ModelPhase phase : model.getPhases()) {
                    System.out.println("\t\t" + phase);
                }
            }
        } catch (RuntimeException | IOException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
    
}
