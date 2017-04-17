/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.util;

import java.util.Iterator;

/**
 *
 * @author adrianohrl
 */
public class CSVReader implements Iterable<String> {
    
    private final FileIO fileReader;

    public CSVReader(String fileName) {
        fileReader = new FileIO(fileName, ",");
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
