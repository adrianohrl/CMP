/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events.io;

import cmp.events.io.TimeClockEventsReader;
import cmp.exceptions.IOException;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventsReaderDAO extends TimeClockEventsReader {
    
    private EntityManager em;

    public TimeClockEventsReaderDAO(EntityManager em, String fileName) throws IOException {
        super(fileName);
        this.em = em;
    }
    
}
