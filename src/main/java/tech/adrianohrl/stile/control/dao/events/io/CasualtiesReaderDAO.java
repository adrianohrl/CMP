package tech.adrianohrl.stile.control.dao.events.io;

import tech.adrianohrl.stile.control.dao.events.CasualtyDAO;
import tech.adrianohrl.stile.model.events.io.CasualtiesReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.events.Casualty;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
        register();
    }

    @Override
    public void readFile(InputStream in) throws IOException {
        super.readFile(in);
        register();
    }
    
    private void register() {
        CasualtyDAO casualtyDAO = new CasualtyDAO(em);
        for (Casualty casualty : getReadEntities()) {
            if (!casualtyDAO.isRegistered(casualty)) {
                casualtyDAO.create(casualty);
                registeredCasualties.add(casualty);
            }
        }
    }

    public List<Casualty> getRegisteredCasualties() {
        return registeredCasualties;
    }

    @Override
    public Iterator<Casualty> iterator() {
        return registeredCasualties.iterator();
    }
    
}
