package tech.adrianohrl.stile.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class Chart implements Comparable<Chart>, Iterable<ChartSize>, Serializable {
    
    @Id
    private String name;
    private boolean archived = false;
    private String abbreviation;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChartSize> sizes = new ArrayList<>();

    public Chart() {
    }

    public Chart(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public Chart(String name, String abbreviation, List<ChartSize> sizes) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.sizes = sizes;
        Collections.sort(this.sizes);
    }
    
    public ChartSize getSize(String sizeName) {
        for (ChartSize size : this) {
            if (size.getName().equalsIgnoreCase(sizeName)) {
                return size;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Chart chart) {
        return abbreviation.compareTo(chart.abbreviation);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Chart && equals((Chart) obj);
    }
    
    public boolean equals(Chart chart) {
        return chart != null && name.equals(chart.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public List<ChartSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<ChartSize> sizes) {
        this.sizes = sizes;
    }

    @Override
    public Iterator<ChartSize> iterator() {
        return sizes.iterator();
    }
    
}
