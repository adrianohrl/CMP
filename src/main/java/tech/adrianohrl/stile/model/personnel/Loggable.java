package tech.adrianohrl.stile.model.personnel;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
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
