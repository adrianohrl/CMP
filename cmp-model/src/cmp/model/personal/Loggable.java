/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmp.model.personal;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author adrianohrl
 */
@Entity
public abstract class Loggable extends Employee {
    
    @Column(unique = true)
    private String login;
    private String password;

    public Loggable() {
    }

    public Loggable(String login, String password, String code, String name) {
        super(code, name);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
