/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.util;

import br.com.ceciliaprado.cmp.exceptions.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author adrianohrl
 * @param <E>
 */
public abstract class AbstractReader<E extends Comparable> implements Iterable<E> {
    
    private final List<E> readEntities = new ArrayList<>();
    
    public void readFile(String fileName) throws IOException {
        readFile(new CSVReader(fileName, getDefaultFields()));
    }
    
    public void readFile(InputStream in) throws IOException {
        readFile(new CSVReader(in, getDefaultFields()));  
    }
    
    private void readFile(CSVReader csvReader) throws IOException {
        csvReader.readColumnTitles();
        List<Field> fields = csvReader.fillFields();
        while (fields != null) {
            E entity = build(fields);
            if (entity != null) {
                readEntities.add(entity);
            }
            fields = csvReader.fillFields();
        }
        csvReader.close();
        Collections.sort(readEntities);
    }
    
    protected abstract List<Field> getDefaultFields();
    
    protected abstract E build(List<Field> fields) throws IOException;
    
    protected void sort() {
        Collections.sort(readEntities);
    }
    
    protected void add(E entity) {
        readEntities.add(entity);
    }
    
    protected boolean contains(E entity) {
        return readEntities.contains(entity);
    }
    
    protected E get(E entity) {
        int index = readEntities.indexOf(entity);
        return index > 0 && index < readEntities.size() ? readEntities.get(index) : null;
    }
    
    public List<E> getReadEntities() {
        return readEntities;
    }
    
    @Override
    public Iterator<E> iterator() {
        return readEntities.iterator();
    }
    
}
