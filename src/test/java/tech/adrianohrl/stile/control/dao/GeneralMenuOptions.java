/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao;

import tech.adrianohrl.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public enum GeneralMenuOptions {
    INVALID(0, ""),
    EVENTS_MENU(1, "events related operations"),
    PERSONAL_MENU(2, "personal related operations"),
    PRODUCTION(3, "production related operations"),
    QUIT(-1, "quit");
    
    private final int value;
    private final String description;
    
    private GeneralMenuOptions(int value, String description) {
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
        return this.value == value || this == INVALID && (value < -1 || value > GeneralMenuOptions.size());
    }    

    @Override
    public String toString() {
        return value + ") " + description + ";";
    }
    
    public static int size() {
        return GeneralMenuOptions.values().length - 2;
    }
    
    public static GeneralMenuOptions fromInteger(int value) {
        for (GeneralMenuOptions option : GeneralMenuOptions.values()) {
            if (option.equals(value)) {
                return option;
            }
        }
        return INVALID;
    }
    
    public static void printMenu() {
        System.out.println("\nSelect one of the following options:");
        for (GeneralMenuOptions option : GeneralMenuOptions.values()) {
            if (option.isValid()) {
                System.out.println(option);
            }
        }
    }
    
    public static GeneralMenuOptions getOption() {
        Keyboard keyboard = Keyboard.getKeyboard();
        GeneralMenuOptions.printMenu();
        int value = keyboard.readInteger("Enter the desired option: ");
        return GeneralMenuOptions.fromInteger(value);
    }
}
