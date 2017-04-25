/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.production;

import cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public enum MenuOptions {
    INVALID(0, ""),
    REGISTER_PHASE(1, "register new phase"), 
    REGISTER_MODEL(2, "register new model"), 
    REGISTER_PRODUCTION_ORDER(3, "register new production order"),
    REGISTER_PHASE_PRODUCTION_ORDER(4, "register new phase production order"),
    SHOW_ALL_PHASES(5, "show all registered phases"),
    SHOW_ALL_MODELS(6, "show all registered models"),
    SHOW_ALL_PRODUCTION_ORDERS(7, "show all registered production orders"),
    SHOW_ALL_PHASE_PRODUCTION_ORDERS(8, "show all registered phase production orders"),
    ASSIGN_NEW_PHASES(9, "assign new phases to a model"),
    QUIT(-1, "quit");
    
    private final int value;
    private final String description;
    
    private MenuOptions(int value, String description) {
        this.value = value;
        this.description = description;
    }
    
    public boolean isValid() {
        return this != INVALID;
    }
    
    public boolean quit() {
        return this == QUIT;
    }
    
    public boolean equals(int value) {
        return this.value == value || this == INVALID && (value < -1 || value > MenuOptions.size());
    }    

    @Override
    public String toString() {
        return value + ") " + description + ";";
    }
    
    public static int size() {
        return MenuOptions.values().length - 2;
    }
    
    public static MenuOptions fromInteger(int value) {
        for (MenuOptions option : MenuOptions.values()) {
            if (option.equals(value)) {
                return option;
            }
        }
        return INVALID;
    }
    
    public static void printMenu() {
        System.out.println("\nSelect one of the following options:");
        for (MenuOptions option : MenuOptions.values()) {
            if (option.isValid()) {
                System.out.println(option);
            }
        }
    }
    
    public static MenuOptions getOption() {
        Keyboard keyboard = Keyboard.getKeyboard();
        MenuOptions.printMenu();
        int value = keyboard.readInteger("Enter the desired option: ");
        return MenuOptions.fromInteger(value);
    }
}
