/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.personal;

import java.util.ArrayList;

/**
 *
 * @author adrianohrl
 */
public class Manager extends Loggable {
    
    private ArrayList<Supervisor> supervisor = new ArrayList<>();

    public Manager() {
    }

    public Manager(String login, String password, String code, String name) {
        super(login, password, code, name);
    }

    public ArrayList<Supervisor> getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(ArrayList<Supervisor> supervisor) {
        this.supervisor = supervisor;
    }
}
