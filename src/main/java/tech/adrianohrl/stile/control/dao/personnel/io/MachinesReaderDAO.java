package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.exceptions.IOException;
import tech.adrianohrl.stile.model.personnel.Sector;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import tech.adrianohrl.stile.control.dao.personnel.MachineDAO;
import tech.adrianohrl.stile.model.personnel.io.MachinesReader;
import tech.adrianohrl.stile.model.personnel.Machine;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class MachinesReaderDAO extends MachinesReader {
    
    private final EntityManager em;
    private final List<Sector> registeredSectors = new ArrayList<>();

    public MachinesReaderDAO(EntityManager em) {
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
            sectorDAO.update(sector);
            registeredSectors.add(sector);
        }
    }

    @Override
    protected Machine getMachine(String machineName) {
        MachineDAO machineDAO = new MachineDAO(em);
        Machine machine = new Machine(machineName);
        if (!machineDAO.isRegistered(machine)) {
            machineDAO.create(machine);
            return machine;
        } 
        return machineDAO.find(machineName);
    }

    @Override
    protected Sector getSector(String sectorName) {
        SectorDAO sectorDAO = new SectorDAO(em);
        return sectorDAO.find(sectorName);
    }

    public List<Sector> getRegisteredSectors() {
        return registeredSectors;
    }

    @Override
    public Iterator<Sector> iterator() {
        return registeredSectors.iterator();
    }
    
}
