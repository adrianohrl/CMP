package tech.adrianohrl.stile.model.personnel;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import tech.adrianohrl.model.Archivable;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Machine implements Archivable, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;

    public Machine() {
    }

    public Machine(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Machine && equals((Machine) obj);
    }
    
    public boolean equals(Machine machine) {
        return machine != null && name.equals(machine.name);
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
