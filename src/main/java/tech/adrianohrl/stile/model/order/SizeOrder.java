package tech.adrianohrl.stile.model.order;

import tech.adrianohrl.stile.model.production.ChartSize;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
@Entity
public class SizeOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long code;
    @ManyToOne(optional = false)
    private ChartSize size;
    private int quantity = 0;

    public SizeOrder() {
    }

    public SizeOrder(ChartSize size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public SizeOrder(ChartSize size) {
        this(size, 0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && (obj instanceof SizeOrder && equals((SizeOrder) obj) || obj instanceof ChartSize && equals((ChartSize) obj));
    }

    public boolean equals(SizeOrder order) {
        return order != null && equals(order.size);
    }

    public boolean equals(ChartSize size) {
        return this.size.equals(size);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public ChartSize getSize() {
        return size;
    }

    public void setSize(ChartSize size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
