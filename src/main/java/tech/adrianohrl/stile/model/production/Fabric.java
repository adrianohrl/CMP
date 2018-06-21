package tech.adrianohrl.stile.model.production;

import tech.adrianohrl.stile.model.personnel.Machine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.UniqueConstraint;
import tech.adrianohrl.model.Archivable;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Fabric implements Archivable, Comparable<Fabric>, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;
    private String observation = "";
    @ManyToOne(optional = false)
    private Collection collection;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Fabric_Machines", 
        joinColumns = @JoinColumn(name = "fabric_name"),
        inverseJoinColumns = @JoinColumn(name = "machine_name"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"fabric_name", "machine_name"})
    )
    private List<Machine> machines = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Fabric_Variants", 
        joinColumns = @JoinColumn(name = "fabric_name"),
        inverseJoinColumns = @JoinColumn(name = "variant_name"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"fabric_name", "variant_name"})
    )
    private List<Variant> variants = new ArrayList<>();

    public Fabric() {
    }
    
    public Fabric(String name, String observation, Collection collection) {
        this.name = name;
        this.observation = observation;
        this.collection = collection;
    }

    public Fabric(String name, String observation, Collection collection, List<Machine> machines, List<Variant> variants) {
        this(name, observation, collection);
        this.machines = machines;
        this.variants = variants;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Fabric fabric) {
        return name.compareTo(fabric.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Fabric && equals((Fabric) obj);
    }
    
    public boolean equals(Fabric fabric) {
        return fabric != null && name.equals(fabric.name);
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

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }
    
}
