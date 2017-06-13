/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.production;

import br.com.ceciliaprado.cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public enum ProductionMenuOptions {
    INVALID(0, ""),
    REGISTER_PHASE(1, "register new phase"), 
    REGISTER_MODEL(2, "register new model"), 
    REGISTER_PRODUCTION_ORDER(3, "register new production order"),
    REGISTER_PHASE_PRODUCTION_ORDER(4, "register new phase production order"),
    SHOW_ALL_PHASES(5, "show all registered phases"),
    SHOW_ALL_MODELS(6, "show all registered models"),
    SHOW_ALL_PRODUCTION_ORDERS(7, "show all registered production orders"),
    SHOW_ALL_PHASE_PRODUCTION_ORDERS(8, "show all registered phase production orders"),
    SHOW_ALL_PENDENT_PHASE_PRODUCTION_ORDERS(9, "show all registered pendent phase production orders"),
    ASSIGN_NEW_PHASES(10, "assign new phases to a model"),
    QUIT(-1, "quit");
    
    private final int index;
    private final String description;
    
    private ProductionMenuOptions(int index, String description) {
        this.index = index;
        this.description = description;
    }
    
    public boolean isValid() {
        return this != INVALID;
    }
    
    public boolean quit() {
        return this == QUIT;
    }
    
    public boolean equals(int index) {
        return this.index == index || this == INVALID && (index < -1 || index > ProductionMenuOptions.size());
    }    

    @Override
    public String toString() {
        return index + ") " + description + ";";
    }
    
    public static int size() {
        return ProductionMenuOptions.values().length - 2;
    }
    
    public static ProductionMenuOptions fromIndex(int index) {
        for (ProductionMenuOptions option : ProductionMenuOptions.values()) {
            if (option.equals(index)) {
                return option;
            }
        }
        return INVALID;
    }
    
    public static void printMenu() {
        System.out.println("\nSelect one of the following options:");
        for (ProductionMenuOptions option : ProductionMenuOptions.values()) {
            if (option.isValid()) {
                System.out.println(option);
            }
        }
    }
    
    public static ProductionMenuOptions getOption() {
        Keyboard keyboard = Keyboard.getKeyboard();
        ProductionMenuOptions.printMenu();
        int index = keyboard.readInteger("Enter the desired option: ");
        return ProductionMenuOptions.fromIndex(index);
    }
}
