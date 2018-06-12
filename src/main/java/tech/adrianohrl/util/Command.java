package tech.adrianohrl.util;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 * @param <T>
 */
public interface Command<T> {
    
    public abstract void execute(T item);
    
}
