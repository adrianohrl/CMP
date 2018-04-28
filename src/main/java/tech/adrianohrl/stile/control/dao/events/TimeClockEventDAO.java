/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.model.events.TimeClockEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class TimeClockEventDAO extends AbstractEmployeeRelatedEventDAO<TimeClockEvent> {

    public TimeClockEventDAO(EntityManager em) {
        super(em, TimeClockEvent.class);
    }
    
}
