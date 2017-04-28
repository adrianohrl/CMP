/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

import cmp.exceptions.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class KeyboardEntries {
    
    public static <T> T selectOne(List<T> possibleOptions, String groupName) {
        if (possibleOptions.isEmpty()) {
            System.out.println("There is no " + groupName + " registered yet!!!");
            return null;
        }
        System.out.println("Select one of the following options:");
        for (int i = 0; i < possibleOptions.size(); i++) {
            System.out.println(i + ") " + possibleOptions.get(i));
        }
        System.out.println("-1) quit");
        Keyboard keyboard = Keyboard.getKeyboard();
        int option = keyboard.readInteger("Enter an option: ");
        while (option < 0 || option >= possibleOptions.size()) {
            if (option == -1) {
                System.out.println("Aborting ...");
                return null;
            }
            System.out.println("Invalid option: " + option + "!!! Try again.");
            option = keyboard.readInteger("Enter an option: ");
        }
        return possibleOptions.get(option);
    }
    
    public static <T> List<T> selectMany(List<T> possibleOptions, String groupName) {
        if (possibleOptions.isEmpty()) {
            System.out.println("There is no " + groupName + " registered yet!!!");
            return null;
        }
        System.out.println("Select many options below:");
        for (int i = 0; i < possibleOptions.size(); i++) {
            System.out.println(i + ") " + possibleOptions.get(i));
        }
        System.out.println("-1) quit");
        Keyboard keyboard = Keyboard.getKeyboard();
        List<Integer> options;
        List<T> selectedOptions = new ArrayList<>();
        while (selectedOptions.isEmpty()) {
            options = keyboard.readIntegers("Enter the desired options (use '--all' to select all options or use ' ' between each option) ", " ");
            if (options == null) {
                return possibleOptions;
            }
            for (Integer option : options) {
                if (option == -1) {
                    System.out.println("Aborting ...");
                    return null;
                } 
                if (option < 0 || option >= possibleOptions.size()) {
                    System.out.println("Ignored invalid option: " + option);
                    continue;
                }
                T selectedOption = possibleOptions.get(option);
                if (!selectedOptions.contains(selectedOption)) {
                    selectedOptions.add(selectedOption);
                } else {
                    System.out.println("Ignored duplicated option: " + option);
                }
            }
        }
        return selectedOptions;
    }
    
    public static boolean askForYesOrNo(String prompt) {
        return KeyboardEntries.askForYesOrNo(prompt, "y", "n");
    }
    
    public static boolean askForYesOrNo(String prompt, String yes, String no) {
        Keyboard keyboard = Keyboard.getKeyboard();
        String answer = keyboard.readString(prompt + " (" + yes + "/" + no + "): ");;
        while (!answer.startsWith(yes) && !answer.startsWith(no)) {
            System.out.println("Invalid option!!! Try again.");
            answer = keyboard.readString(prompt + " (" + yes + "/" + no + "): ");
        }
        return answer.startsWith(yes);
    }
    
    public static int askForPositiveInteger(String prompt) {
        Keyboard keyboard = Keyboard.getKeyboard();
        int number = keyboard.readInteger(prompt);
        while (number <= 0) {
            System.out.println("It must be positive!!! Try again.");
            number = keyboard.readInteger(prompt);
        }
        return number;
    }
    
    public static double askForPositiveDouble(String prompt) {
        Keyboard keyboard = Keyboard.getKeyboard();
        double number = keyboard.readDouble(prompt);
        while (number <= 0.0) {
            System.out.println("It must be positive!!! Try again.");
            number = keyboard.readDouble(prompt);
        }
        return number;
    }
    
    public static Calendar askForDateAndTime() {
        Keyboard keyboard = Keyboard.getKeyboard();
        String date;
        String time;
        Calendar timestamp = null;
        while (timestamp == null) {
            try {
                date = keyboard.readString("date (" + Calendars.DATE_FORMAT + ") (default: today):");
                time = keyboard.readString("time (" + Calendars.TIME_FORMAT + ") (default: now): ");
                timestamp = Calendars.sum(date, time);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
        return timestamp;
    }
    
}
