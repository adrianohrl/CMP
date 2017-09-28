/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.util;

import br.com.ceciliaprado.cmp.exceptions.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author adrianohrl
 */
public class CSVReader implements Iterable<String> {
    
    private final FileIO fileReader;
    private final List<Field> defaultFields;

    public CSVReader(String fileName, List<Field> defaultFields) throws IOException {
        this.defaultFields = defaultFields;
        fileReader = new FileIO(fileName, ",");
    }

    public CSVReader(InputStream in, List<Field> defaultFields) throws IOException {
        this.defaultFields = defaultFields;
        fileReader = new FileIO(in, ",");
    }
    
    public void readColumnTitles() throws IOException {
        int counter = 0, index;
        for (String fieldTitle : this) {
            index = indexOf(fieldTitle);
            if (index == -1) {
                throw new IOException("Inexistent field!!!");
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
                    i--;
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
    
    public List<Field> fillFields() throws IOException {
        Iterator<String> fieldValueIterator = iterator();
        if (eof()) {
            return null;
        }
        List<Field> fields = new ArrayList<>(defaultFields);
        for (Field field : fields) {
            if (fieldValueIterator.hasNext()) {
                try {
                    field.setValue(fieldValueIterator.next());
                } catch (ParseException e) {
                    throw new IOException("Parse Exception caught: " + e.getMessage());
                }
            } else if (field.isMandatory()) {
                throw new IOException("Expected a new value in " + field.getTitle() + " column!!!");                
            }
        }
        return fields;
    }

    public List<Field> getDefaultFields() {
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
