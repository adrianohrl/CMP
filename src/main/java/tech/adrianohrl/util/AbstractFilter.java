package tech.adrianohrl.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public abstract class AbstractFilter<T extends Comparable> implements Command<T>, Iterable<T> {
    
    private boolean sorted = false;
    private final List<T> items = new ArrayList<>();
    
    public void clear() {
        items.clear();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    protected void add(T item) {
        sorted = false;
        items.add(item);
    }

    @Override
    public Iterator<T> iterator() {
        if (!sorted) {
            Collections.sort(items);
            sorted = true;
        }
        return items.iterator();
    }

    public List<T> getItems() {
        if (!sorted) {
            Collections.sort(items);
            sorted = true;
        }
        return items;
    }
    
}
