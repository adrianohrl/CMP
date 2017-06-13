/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.events;

import br.com.ceciliaprado.cmp.model.events.CasualtyEntryEvent;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class CasualtyEntryEventDAO extends EntryEventDAO<CasualtyEntryEvent> {

    public CasualtyEntryEventDAO(EntityManager em) {
        super(em, CasualtyEntryEvent.class);
    }
    
}
