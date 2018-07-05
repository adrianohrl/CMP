package tech.adrianohrl.stile.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import tech.adrianohrl.model.Archivable;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Model implements Archivable, Comparable<Model>, Serializable {
    
    @Id
    private String reference;
    private boolean archived = false;
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne(optional = false)
    private Family family;
    @ManyToOne(optional = false)
    private Collection collection;
    @ManyToOne(optional = false)
    private Chart chart;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Part> parts = new ArrayList<>();
    @ManyToMany
    private List<Variant> variants = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModelPhase> phases = new ArrayList<>();

    public Model() {
    }

    public Model(String reference, String name) {
        this.reference = reference;
        this.name = name;
    }

    public Model(String reference, String name, Family family, Collection collection, Chart chart) {
        this.reference = reference;
        this.name = name;
        this.family = family;
        this.collection = collection;
        this.chart = chart;
    }
    
    public boolean belongs(ModelPhase phase) {
        return phases.contains(phase);
    }

    @Override
    public int compareTo(Model model) {
        return reference.compareToIgnoreCase(model.reference);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Model && equals((Model) obj);
    }
    
    public boolean equals(Model model) {
        return model != null && reference.equalsIgnoreCase(model.reference);
    }
    
    public boolean equals(String modelReference) {
        return reference.equalsIgnoreCase(modelReference);
    }

    @Override
    public String toString() {
        return name + " (" + reference + ")";
    }
    
    public ModelPhase getPhase(Phase phase) {
        int index = phases.indexOf(phase);
        return index != -1 ? phases.get(index) : null;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean isArchived() {
        return archived;
    }

    @Override
    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    public List<Variant> getVariants() {
        return variants;
    }

    public void setVariants(List<Variant> variants) {
        this.variants = variants;
    }

    public List<ModelPhase> getPhases() {
        return phases;
    }

    public void setPhases(List<ModelPhase> phases) {
        this.phases = phases;
    }

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }
    
}
