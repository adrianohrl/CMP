/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

import cmp.exceptions.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author adrianohrl
 */
public class CSVReader implements Iterable<String> {
    
    private final FileIO fileReader;
    private final ArrayList<Field> defaultFields;

    public CSVReader(String fileName, ArrayList<Field> defaultFields) throws IOException {
        this.defaultFields = defaultFields;
        fileReader = new FileIO(fileName, ",");
    }
    
    public void readColumnTitles() throws IOException {
        int counter = 0;
        int index;
        for (String fieldTitle : this) {
            index = indexOf(fieldTitle);
            if (index == -1) {
                throw new IOException("Inexistent time clock event field!!!");
            }
            defaultFields.get(index).setColumnIndex(counter++);
        }
        for (int i = 0; i < defaultFields.size(); i++) {
            Field field = defaultFields.get(i);
            if (!field.exists()) {
                if (field.isMandatory()) {
                    throw new IOException(field.getTitle() + " column title is mandatory!!!");
                } else {
                    defaultFields.remove(field);
                }
            }
        }
        if (defaultFields.isEmpty()) {
            throw new IOException("No column titles found!!!");
        }
        Collections.sort(defaultFields);
    }
    
    private int indexOf(String fieldTitle) {
        for (int i = 0; i < defaultFields.size(); i++) {
            if (defaultFields.get(i).equals(fieldTitle)) {
                return i;
            }
        }
        return -1;
    }
    
    public ArrayList<Field> fillFields() throws IOException {
        Iterator<String> fieldValueIterator = iterator();
        if (eof()) {
            return null;
        }
        ArrayList<Field> fields = new ArrayList<>(defaultFields);
        for (Field field : fields) {
            if (!fieldValueIterator.hasNext() && field.isMandatory()) {
                throw new IOException("Expected a new value in " + field.getTitle() + " column!!!");
            }
            try {
                field.setValue(fieldValueIterator.next());
            } catch (ParseException e) {
                throw new IOException("Parse Exception catched: " + e.getMessage());
            }
        }
        return fields;
    }

    public ArrayList<Field> getDefaultFields() {
        return defaultFields;
    }
    
    public boolean eof() {
        return fileReader.eof();
    }
    
    public String readLine() {
        return fileReader.readLine();
    }
    
    public void close() {
        fileReader.close();
    }

    @Override
    public Iterator<String> iterator() {
        return fileReader.getTokens();
    }
    
}
