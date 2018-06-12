/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.adrianohrl.stile.control.dao.events;

import tech.adrianohrl.stile.control.dao.DataSource;
import tech.adrianohrl.stile.model.events.Casualty;
import tech.adrianohrl.util.KeyboardEntries;
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
    
    public static Casualty selectOneCollectiveCasualty() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        List<Casualty> casualties = casualtyDAO.findCollectives();
        return KeyboardEntries.selectOne(casualties, "collective casualty");
    }
    
    public static Casualty selectOneNonCollectiveCasualty() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        List<Casualty> casualties = casualtyDAO.findNonCollectives();
        return KeyboardEntries.selectOne(casualties, "non collective casualty");
    }
    
    public static List<Casualty> selectManyCasualties() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        List<Casualty> casualties = casualtyDAO.findAll();
        return KeyboardEntries.selectMany(casualties, "casualty");
    }
    
    public static List<Casualty> selectManyCollectiveCasualties() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        List<Casualty> casualties = casualtyDAO.findCollectives();
        return KeyboardEntries.selectMany(casualties, "collective casualty");
    }
    
    public static List<Casualty> selectManyNonCollectiveCasualties() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        List<Casualty> casualties = casualtyDAO.findNonCollectives();
        return KeyboardEntries.selectMany(casualties, "non collective casualty");
    }
    
}
