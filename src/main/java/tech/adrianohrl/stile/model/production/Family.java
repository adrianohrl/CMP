package tech.adrianohrl.stile.model.production;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Family implements Comparable<Family>, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;
    @ManyToOne(optional = false)
    private Collection collection;

    public Family() {
    }

    public Family(String name, Collection collection) {
        this.name = name;
        this.collection = collection;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Family family) {
        return name.compareTo(family.name);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Family && equals((Family) obj);
    }
    
    public boolean equals(Family family) {
        return family != null && name.equals(family.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
    
}
