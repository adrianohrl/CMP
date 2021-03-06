package tech.adrianohrl.stile.model.production;

import tech.adrianohrl.stile.model.order.SizeOrder;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class ChartSize implements Comparable<ChartSize>, Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    private String name;
    private int cardinal;

    public ChartSize() {
    }

    public ChartSize(String name, int cardinal) {
        this.name = name;
        this.cardinal = cardinal;
    }

    @Override
    public int compareTo(ChartSize size) {
        return cardinal - size.cardinal;
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof ChartSize && equals((ChartSize) obj) || obj instanceof SizeOrder && equals(((SizeOrder) obj).getSize()));
    }
    
    public boolean equals(ChartSize size) {
        return size != null && name.equals(size.name);
    }
    
    @Override
    public String toString() {
        return name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCardinal() {
        return cardinal;
    }

    public void setCardinal(int cardinal) {
        this.cardinal = cardinal;
    }
    
}
