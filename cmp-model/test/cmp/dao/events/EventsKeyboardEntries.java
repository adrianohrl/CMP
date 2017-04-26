/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.dao.events;

import cmp.dao.DataSource;
import cmp.model.events.Casualty;
import cmp.util.KeyboardEntries;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class EventsKeyboardEntries {
    
    private static EntityManager em = DataSource.createEntityManager();
    
    public static Casualty selectOneCasualty() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        List<Casualty> casualties = casualtyDAO.findAll();
        return KeyboardEntries.selectOne(casualties, "casualty");
    }
    
    public static List<Casualty> selectManyCasualties() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        List<Casualty> casualties = casualtyDAO.findAll();
        return KeyboardEntries.selectMany(casualties, "casualty");
    }
    
}
