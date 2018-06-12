package tech.adrianohrl.stile.model.production;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Collection implements Comparable<Collection>, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;

    public Collection() {
    }

    public Collection(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Collection collection) {
        return name.compareTo(collection.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Collection && equals((Collection) obj);
    }
    
    public boolean equals(Collection collection) {
        return collection != null && name.equals(collection.name);
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
    
}
