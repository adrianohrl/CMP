package tech.adrianohrl.stile.control.model.production.reports;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public interface SeriesType<T extends Enum<T>> extends Comparable<T> {
    
    public abstract String getName();
    
    @Override
    public abstract String toString();
    
    public abstract boolean equals(String name);
    
}
