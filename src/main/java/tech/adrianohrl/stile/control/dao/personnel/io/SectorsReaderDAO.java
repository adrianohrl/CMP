package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.control.dao.personnel.SupervisorDAO;
import tech.adrianohrl.stile.control.model.personnel.io.SectorsReader;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Sector;
import tech.adrianohrl.stile.model.personnel.Supervisor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class SectorsReaderDAO extends SectorsReader {
    
    private final EntityManager em;
    private final List<Sector> registeredSectors = new ArrayList<>();

    public SectorsReaderDAO(EntityManager em) {
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
        SectorDAO sectorDAO = new SectorDAO(em);
        for (Sector sector : getReadEntities()) {
            if (!sectorDAO.isRegistered(sector)) {
                sectorDAO.create(sector);
                registeredSectors.add(sector);
            }
        }
    }
    
    @Override
    protected Supervisor getSupervisor(String supervisorName) throws IOException {
        SupervisorDAO supervisorDAO = new SupervisorDAO(em);
        return supervisorDAO.find(supervisorName);
    }

    public List<Sector> getRegisteredSectors() {
        return registeredSectors;
    }

    @Override
    public Iterator<Sector> iterator() {
        return registeredSectors.iterator();
    }
    
}
