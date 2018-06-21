package tech.adrianohrl.stile.model.production;

import tech.adrianohrl.stile.model.order.VariantOrder;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import tech.adrianohrl.model.Archivable;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Variant implements Archivable, Comparable<Variant>, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;

    public Variant() {
    }

    public Variant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Variant variant) {
        return name.compareTo(variant.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Variant && equals((Variant) obj) || obj instanceof VariantOrder && equals(((VariantOrder) obj).getVariant()));
    }
    
    public boolean equals(Variant variant) {
        return variant != null && name.equals(variant.name);
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
    
}
