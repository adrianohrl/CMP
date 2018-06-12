package tech.adrianohrl.stile.model.personnel;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Supervisor extends Loggable {
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Supervisor_Subordinates", 
        joinColumns = @JoinColumn(name = "supervisor_name"),
        inverseJoinColumns = @JoinColumn(name = "subordinate_name"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"supervisor_name", "subordinate_name"})
    )
    private List<Subordinate> subordinates = new ArrayList<>();

    public Supervisor() {
    }

    public Supervisor(String login, String password, String code, String name) {
        super(login, password, code, name);
    }

    public List<Subordinate> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Subordinate> subordinates) {
        this.subordinates = subordinates;
    }
    
}
