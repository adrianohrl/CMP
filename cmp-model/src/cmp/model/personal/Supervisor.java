/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.personal;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author adrianohrl
 */
@Entity
public class Supervisor extends Loggable {
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Supervisor_Subordinates", 
        joinColumns = @JoinColumn(name = "supervisor_name"),
        inverseJoinColumns = @JoinColumn(name = "subordinate_name")
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

    public void setSubordinates(ArrayList<Subordinate> subordinates) {
        this.subordinates = subordinates;
    }
    
}
