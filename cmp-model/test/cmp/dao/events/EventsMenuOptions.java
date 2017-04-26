/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.util.Keyboard;

/**
 *
 * @author adrianohrl
 */
public enum EventsMenuOptions {
    INVALID(0, ""),
    REGISTER_ENTRY_EVENT(1, "register new entry event"), 
    REGISTER_TIME_CLOCK_EVENT(2, "register new time clock event"), 
    REGISTER_CASUALTY_ENTRY_EVENT_PER_SUBORDINATES(3, "register new casualty entry event for subordinates"),
    REGISTER_CASUALTY_ENTRY_EVENT_PER_SUPERVISOR(4, "register new casualty entry event for all supervisor subordinates"),
    REGISTER_CASUALTY_ENTRY_EVENT_PER_SUPERVISORS(5, "register new casualty entry event for all supervisors subordinates"),
    REGISTER_CASUALTY_ENTRY_EVENT_PER_SECTOR(6, "register new casualty entry event for all sector subordinates"),
    REGISTER_CASUALTY_ENTRY_EVENT_PER_SECTORS(7, "register new casualty entry event for all sector subordinates"),
    SHOW_ALL_ENTRY_EVENTS(8, "show all registered entry events"),
    SHOW_ALL_TIME_CLOCK_EVENTS(9, "show all registered time clock events"),
    SHOW_ALL_EVENTS(10, "show all registered employee events"),
    IMPORT_ENTRY_EVENTS(11, "import entry events"),
    IMPORT_TIME_CLOCK_EVENTS(12, "import time clock events"),
    REPORT_PER_SUBORDINATE(13, "report subordinate performance"),
    REPORT_PER_SUBORDINATES(14, "report subordinates performance"),
    REPORT_PER_SUPERVISOR(15, "report all supervisor subordinates performance"),
    REPORT_PER_SUPERVISORS(16, "report all supervisors subordinates performance"),
    REPORT_PER_SECTOR(17, "report all sector subordinates performance"),
    REPORT_PER_SECTORS(18, "report all sectors subordinates performance"),
    QUIT(-1, "quit");
    
    private final int value;
    private final String description;
    
    private EventsMenuOptions(int value, String description) {
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
        return this.value == value || this == INVALID && (value < -1 || value > EventsMenuOptions.size());
    }    

    @Override
    public String toString() {
        return value + ") " + description + ";";
    }
    
    public static int size() {
        return EventsMenuOptions.values().length - 2;
    }
    
    public static EventsMenuOptions fromInteger(int value) {
        for (EventsMenuOptions option : EventsMenuOptions.values()) {
            if (option.equals(value)) {
                return option;
            }
        }
        return INVALID;
    }
    
    public static void printMenu() {
        System.out.println("\nSelect one of the following options:");
        for (EventsMenuOptions option : EventsMenuOptions.values()) {
            if (option.isValid()) {
                System.out.println(option);
            }
        }
    }
    
    public static EventsMenuOptions getOption() {
        Keyboard keyboard = Keyboard.getKeyboard();
        EventsMenuOptions.printMenu();
        int value = keyboard.readInteger("Enter the desired option: ");
        return EventsMenuOptions.fromInteger(value);
    }
}
