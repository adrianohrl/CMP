package tech.adrianohrl.stile.model.personnel;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public abstract class Employee implements Comparable<Employee>, Serializable {
    
    @Column(nullable = false, unique = true)
    private String code;
    private boolean archived = false;
    @Id
    private String name;

    public Employee() {
    }

    public Employee(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public int compareTo(Employee employee) {
        return name.compareToIgnoreCase(employee.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Employee && equals((Employee) obj);
    }

    public boolean equals(Employee employee) {
        return employee != null && name.equalsIgnoreCase(employee.name);
    }
    
    public boolean equals(String employeeName) {
        return name.equalsIgnoreCase(employeeName);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
