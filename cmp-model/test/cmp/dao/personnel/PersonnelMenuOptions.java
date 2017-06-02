/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.personnel;

import cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public enum PersonnelMenuOptions {
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
    
    private final int index;
    private final String description;
    
    private PersonnelMenuOptions(int index, String description) {
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
        return this.index == index || this == INVALID && (index < -1 || index > PersonnelMenuOptions.size());
    }    

    @Override
    public String toString() {
        return index + ") " + description + ";";
    }
    
    public static int size() {
        return PersonnelMenuOptions.values().length - 2;
    }
    
    public static PersonnelMenuOptions fromIndex(int index) {
        for (PersonnelMenuOptions option : PersonnelMenuOptions.values()) {
            if (option.equals(index)) {
                return option;
            }
        }
        return INVALID;
    }
    
    public static void printMenu() {
        System.out.println("\nSelect one of the following options:");
        for (PersonnelMenuOptions option : PersonnelMenuOptions.values()) {
            if (option.isValid()) {
                System.out.println(option);
            }
        }
    }
    
    public static PersonnelMenuOptions getOption() {
        Keyboard keyboard = Keyboard.getKeyboard();
        PersonnelMenuOptions.printMenu();
        int index = keyboard.readInteger("Enter the desired option: ");
        return PersonnelMenuOptions.fromIndex(index);
    }
}
