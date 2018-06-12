package tech.adrianohrl.stile.model.personnel;

import javax.persistence.Entity;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Subordinate extends Employee {
    
    private boolean available = true;

    public Subordinate() {
    }

    public Subordinate(String code, String name) {
        super(code, name);
    }

    public Subordinate(String code, String name, boolean available) {
        super(code, name);
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
}
