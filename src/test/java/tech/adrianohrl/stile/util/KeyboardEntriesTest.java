/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class KeyboardEntriesTest {
    
    public static void main(String[] args) {
        System.out.println("Testing the KeyboardEntries class.");
        List<String> possibleOptions = new ArrayList<>();
        possibleOptions.add("option number 0");
        possibleOptions.add("option number 1");
        possibleOptions.add("option number 2");
        possibleOptions.add("option number 3");
        possibleOptions.add("option number 4");
        possibleOptions.add("option number 5");
        possibleOptions.add("option number 6");
        possibleOptions.add("option number 7");
        possibleOptions.add("option number 8");
        possibleOptions.add("option number 9");
        String selectedOption = KeyboardEntries.selectOne(possibleOptions, "option");
        if (selectedOption == null) {
            System.out.println("No option selected!!!");
        } else {
            System.out.println("Selected string: " + selectedOption);
        }
        List<String> selectedOptions = KeyboardEntries.selectMany(possibleOptions, "option");
        if (selectedOptions == null) {
            System.out.println("No options selected!!!");
        } else {
            System.out.println("Selected strings:");
            for (String option : selectedOptions) {
                System.out.println("\t" + option);
            }
        }
    }
    
}
