/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao;

import cmp.dao.events.EventsMenuOptions;
import cmp.dao.events.EventsTest;
import cmp.dao.personal.PersonalMenuOptions;
import cmp.dao.personal.PersonalTest;
import cmp.dao.production.ProductionMenuOptions;
import cmp.dao.production.ProductionTest;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class GeneralTest {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static void main(String[] args) {
        GeneralMenuOptions option = GeneralMenuOptions.getOption();
        while (!option.quit()) {
            try {
                option = GeneralTest.process(option);
            } catch (RuntimeException e) {
                System.out.println("Exception catched: " + e.getMessage());
            }
            if (option == null) {
                System.out.println("Going back to general menu ...");
                option = GeneralMenuOptions.getOption();
            }
        }
        System.out.println("Quitting!!!");
        em.close();
        DataSource.closeEntityManagerFactory();
    }
    
    public static GeneralMenuOptions process(GeneralMenuOptions option) {
        if (option.quit()) {
            return option;
        }
        switch (option) {
            case EVENTS_MENU:
                EventsMenuOptions eventsMenuOption = EventsMenuOptions.getOption();
                if (eventsMenuOption.quit()) {
                    return null;
                } else if (!eventsMenuOption.isValid()) {
                    return GeneralMenuOptions.INVALID;
                } else {
                    EventsTest.process(eventsMenuOption);
                }
                break;
            case PERSONAL_MENU:
                PersonalMenuOptions personalMenuOption = PersonalMenuOptions.getOption();
                if (personalMenuOption.quit()) {
                    return null;
                } else if (!personalMenuOption.isValid()) {
                    return GeneralMenuOptions.INVALID;
                } else {
                    PersonalTest.process(personalMenuOption);
                }
                break;
            case PRODUCTION:
                ProductionMenuOptions productionMenuOption = ProductionMenuOptions.getOption();
                if (productionMenuOption.quit()) {
                    return null;
                } else if (!productionMenuOption.isValid()) {
                    return GeneralMenuOptions.INVALID;
                } else {
                    ProductionTest.process(productionMenuOption);
                }
                break;
            default:
                System.out.println("Invalid option!!!");
        }
        return option;
    }
    
}
