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
    REGISTER_CASUALTY(1, "register new casualty"),
    REGISTER_ENTRY_EVENT(2, "register new entry event"), 
    REGISTER_TIME_CLOCK_EVENT(3, "register new time clock event"), 
    REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SUBORDINATES(4,"register new collective entry event (restart/pause) for subordinates"),
    REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SUPERVISOR(5, "register new collective entry event (restart/pause) for all supervisor subordinates"),
    REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SUPERVISORS(6, "register new collective entry event (restart/pause) for all supervisors subordinates"),
    REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SECTOR(7, "register new collective entry event (restart/pause) for all sector subordinates"),
    REGISTER_COLLECTIVE_ENTRY_EVENT_PER_SECTORS(8, "register new collective entry event (restart/pause) for all sector subordinates"),
    SHOW_ALL_CASUALTIES(9, "show all registered casualties"),
    SHOW_ALL_COLLECTIVE_CASUALTIES(10, "show all registered collective casualties"),
    SHOW_ALL_NON_COLLECTIVE_CASUALTIES(11, "show all registered non collective casualties"),
    SHOW_ALL_ENTRY_EVENTS(12, "show all registered entry events"),
    SHOW_ALL_TIME_CLOCK_EVENTS(13, "show all registered time clock events"),
    SHOW_ALL_EVENTS(14, "show all registered employee events"),
    IMPORT_ENTRY_EVENTS(15, "import entry events"),
    IMPORT_TIME_CLOCK_EVENTS(16, "import time clock events"),
    REPORT_PER_SUBORDINATE(17, "report subordinate performance"),
    REPORT_PER_SUBORDINATES(18, "report subordinates performance"),
    REPORT_PER_SUPERVISOR(19, "report all supervisor subordinates performance"),
    REPORT_PER_SUPERVISORS(20, "report all supervisors subordinates performance"),
    REPORT_PER_SECTOR(21, "report all sector subordinates performance"),
    REPORT_PER_SECTORS(22, "report all sectors subordinates performance"),
    QUIT(-1, "quit");
    
    private final int index;
    private final String description;
    
    private EventsMenuOptions(int index, String description) {
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
        return this.index == index || this == INVALID && (index < -1 || index > EventsMenuOptions.size());
    }    

    @Override
    public String toString() {
        return index + ") " + description + ";";
    }
    
    public static int size() {
        return EventsMenuOptions.values().length - 2;
    }
    
    public static EventsMenuOptions fromIndex(int index) {
        for (EventsMenuOptions option : EventsMenuOptions.values()) {
            if (option.equals(index)) {
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
        int index = keyboard.readInteger("Enter the desired option: ");
        return EventsMenuOptions.fromIndex(index);
    }
}
