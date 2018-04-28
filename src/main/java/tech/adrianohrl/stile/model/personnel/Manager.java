/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author adrianohrl
 */
@Entity
public class Manager extends Loggable {
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Manager_Supervisors", 
        joinColumns = @JoinColumn(name = "manager_name"),
        inverseJoinColumns = @JoinColumn(name = "supervisor_name"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"manager_name", "supervisor_name"})
    )
    private List<Supervisor> supervisors = new ArrayList<>();

    public Manager() {
    }

    public Manager(String login, String password, String code, String name) {
        super(login, password, code, name);
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public void setSupervisor(List<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }
}
