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
public class Supervisor extends Loggable {
    
    private ArrayList<Subordinate> subordinates = new ArrayList<>();

    public Supervisor() {
    }

    public Supervisor(String login, String password, String code, String name) {
        super(login, password, code, name);
    }

    public ArrayList<Subordinate> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(ArrayList<Subordinate> subordinates) {
        this.subordinates = subordinates;
    }
    
}
