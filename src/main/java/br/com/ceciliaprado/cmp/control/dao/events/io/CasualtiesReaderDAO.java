/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ceciliaprado.cmp.control.dao.events.io;

import br.com.ceciliaprado.cmp.control.dao.events.CasualtyDAO;
import br.com.ceciliaprado.cmp.control.model.events.io.CasualtiesReader;
import br.com.ceciliaprado.cmp.exceptions.IOException;
import br.com.ceciliaprado.cmp.model.events.Casualty;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author adrianohrl
 */
public class CasualtiesReaderDAO extends CasualtiesReader {
    
    private final EntityManager em;
    private final List<Casualty> registeredCasualties = new ArrayList<>();

    public CasualtiesReaderDAO(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void readFile(String fileName) throws IOException {
        super.readFile(fileName);
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        for (Casualty casualty : getReadEntities()) {
            if (!casualtyDAO.isRegistered(casualty)) {
                casualtyDAO.create(casualty);
                registeredCasualties.add(casualty);
            }
        }
    }

    @Override
    public Iterator<Casualty> iterator() {
        return registeredCasualties.iterator();
    }
    
}
