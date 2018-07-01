package tech.adrianohrl.stile.model.personnel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import tech.adrianohrl.model.Archivable;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Sector implements Archivable, Comparable<Sector>, Iterable<Machine>, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;
    @ManyToOne(optional = false)
    private Supervisor supervisor;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Machine> machines = new ArrayList<>();

    public Sector() {
    }

    public Sector(String name, Supervisor supervisor) {
        this.name = name;
        this.supervisor = supervisor;
    }

    public Sector(String name, Supervisor supervisor, List<Machine> machines) {
        this.name = name;
        this.supervisor = supervisor;
        this.machines = machines;
    }
    
    public boolean isSupervisedBy(Supervisor supervisor) {
        return supervisor != null && supervisor.equals(this.supervisor);
    }

    @Override
    public Iterator<Machine> iterator() {
        return machines.iterator();
    }

    @Override
    public int compareTo(Sector sector) {
        return name.compareToIgnoreCase(sector.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Sector && equals((Sector) obj);
    }

    public boolean equals(Sector sector) {
        return sector != null && name.equalsIgnoreCase(sector.name);
    }
    
    public boolean equals(String sectorName) {
        return name.equalsIgnoreCase(sectorName);
    }
    
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isArchived() {
        return archived;
    }

    @Override
    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }
    
}
