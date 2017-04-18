/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.events.io;

import cmp.exceptions.IOException;
import cmp.model.events.EntryEvent;
import cmp.util.CSVReader;
import cmp.util.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author adrianohrl
 */
public class EntryEventsReader implements Iterable<EntryEvent> {
    
    /** Column Titles **/
    
    
    private final CSVReader csvReader;
    private final ArrayList<EntryEvent> entryEvents = new ArrayList<>();

    public EntryEventsReader(String fileName) throws IOException {
        ArrayList<Field> defaulFields = new ArrayList<>();
        csvReader = new CSVReader(fileName, defaulFields);
        csvReader.readColumnTitles();
        readEntryEvents();
        csvReader.close();
    }
    
    private void readEntryEvents() throws IOException {
        ArrayList<Field> fields = csvReader.fillFields();
        while (fields != null) {
            entryEvents.add(getEntryEvent(fields));
            fields = csvReader.fillFields();
        }
        Collections.sort(entryEvents);
        validate();
    }
    
    private EntryEvent getEntryEvent(ArrayList<Field> fields) {
        return null;
    }
    
    private void validate() throws IOException {
        
    }

    @Override
    public Iterator<EntryEvent> iterator() {
        return entryEvents.iterator();
    }
    
}
