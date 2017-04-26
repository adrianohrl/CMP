/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *  A class to simplify keyboard input.
 *  Abstracts the Scanner class provided in the Java SDK.
 * @author adrianohrl
 */
public class Keyboard {
    private final static Keyboard kb = new Keyboard();

    /** The SDK provided Scanner object, used to obtain keyboard input */
    private final Scanner scan;

    private Keyboard() {
        scan = new Scanner(System.in);
    }

    public static Keyboard getKeyboard() {
        return kb;
    }

    /** 
     *  Reads an integer from the keyboard and returns it. <br>
     *  Uses the provided prompt to request an integer from the user.
     * @param prompt
     * @return 
     */
    public int readInteger(String prompt) {
        System.out.print(prompt);
        int number;
        try {
            number = scan.nextInt();
            readString("");  //clear the buffer
        } catch (InputMismatchException ime) { //wrong type inputted 
            readString("");  //clear the buffer
            number = 0;
        } catch (NoSuchElementException nsee) { //break out of program generates an exception
            readString("");  //clear the buffer
            number = 0;
        }
        return number;
    }

    /**
     *  Reads a double from the keyboard and returns it. <br>
     *  Uses the provided prompt to request a double from the user.
      * @param prompt
      * @return 
     */
    public double readDouble(String prompt) {
        System.out.print(prompt);
        double number;
        try {
            number = scan.nextDouble();
            readString("");  //clear the buffer
        } catch (InputMismatchException ime) {
            readString("");  //clear the buffer
            number = 0;
        } catch (java.util.NoSuchElementException nsee) {
            readString("");  //clear the buffer
            number = 0;
        }
        return number;
    }

    /**
     *  Reads a line of text from the keyboard and returns it as a String. <br>
     *  Uses the provided prompt to request a line of text from the user.
      * @param prompt
      * @return 
     */
    public String readString(String prompt)
    {
        System.out.print(prompt);
        String str;
        try {
            str = scan.nextLine();
        } catch (NoSuchElementException nsee) {
            readString("");  //clear the buffer
            str = "";
        }
        return str;
    }

    /**
     * 
     * @param prompt
     * @param separator
     * @return 
     */
    public List<String> readStrings(String prompt, String separator) {
        String line = readString(prompt);
        return Arrays.asList(line.split(separator));
    }

    /**
     * 
     * @param prompt
     * @param separator
     * @return 
     */
    public List<Integer> readIntegers(String prompt, String separator) {
        List<String> integerStrings = readStrings(prompt, separator);
        List<Integer> integers = new ArrayList<>();
        for (String integerString : integerStrings) {
            try {
                integers.add(new Integer(integerString));
            } catch (RuntimeException e) {}
        }
        return integers;
    }

    /**
     * 
     * @param prompt
     * @param separator
     * @return 
     */
    public List<Double> readDoubles(String prompt, String separator) {
        List<String> doubleStrings = readStrings(prompt, separator);
        List<Double> doubles = new ArrayList<>();
        for (String doubleString : doubleStrings) {
            try {
                doubles.add(new Double(doubleString));
            } catch (RuntimeException e) {}
        }
        return doubles;
    }

}