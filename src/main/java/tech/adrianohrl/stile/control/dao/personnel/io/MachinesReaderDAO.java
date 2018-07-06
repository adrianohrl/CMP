package tech.adrianohrl.stile.control.dao.personnel.io;

import tech.adrianohrl.stile.control.dao.personnel.SectorDAO;
import tech.adrianohrl.stile.model.personnel.Sector;
import javax.persistence.EntityManager;
import tech.adrianohrl.dao.ReaderDAO;
import tech.adrianohrl.stile.control.dao.personnel.MachineDAO;
import tech.adrianohrl.stile.model.personnel.io.MachinesReader;
import tech.adrianohrl.stile.model.personnel.Machine;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public class MachinesReaderDAO extends ReaderDAO<Sector, SectorDAO> {
    
    private final MachinesReader machinesReader;

    public MachinesReaderDAO(EntityManager em) {
        super(em, new SectorDAO(em));
        this.machinesReader = new Reader();
        super.setReader(machinesReader);
    }
    
    @Override
    protected void register() {
        MachineDAO machineDAO = new MachineDAO(em);
        for (Sector sector : machinesReader) {
            if (dao.isRegistered(sector)) {
                for (Machine machine : sector) {
                    machineDAO.create(machine);
                }
                dao.update(sector);
                registeredEntities.add(sector);
            }
        }
    }
    
    private class Reader extends MachinesReader {
        
        @Override
        protected Sector getSector(String name) {
            SectorDAO sectorDAO = new SectorDAO(em);
            return sectorDAO.find(name);
        }

    }
    
}
