/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.personal;

import cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public enum MenuOptions {
    INVALID(0, ""),
    REGISTER_SUBORDINATE(1, "register new subordinate"), 
    REGISTER_SUPERVISOR(2, "register new supervisor"), 
    REGISTER_MANAGER(3, "register new manager"),
    REGISTER_SECTOR(4, "register new sector"),
    SHOW_ALL_SUBORDINATES(5, "show all registered subordinates"),
    SHOW_ALL_SUPERVISORS(6, "show all registered supervisors"),
    SHOW_ALL_MANAGERS(7, "show all registered managers"),
    SHOW_ALL_SECTORS(8, "show all registered sectors"),
    ASSIGN_NEW_SUBORDINATES(9, "assign new subordinates to a supervisor"),
    ASSIGN_NEW_SUPERVISORS(10, "assign new supervisors to a manager"),
    UPGRADE_EMPLOYEE(11, "upgrade employee"),
    DOWNGRADE_EMPLOYEE(12, "downgrade employee"),
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
