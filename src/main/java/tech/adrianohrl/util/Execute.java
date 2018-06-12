package tech.adrianohrl.util;

/**
 *
 * @author Adriano Henrique Rossette Leite (contact@adrianohrl.tech)
 */
public interface Execute<T> {
    
    public abstract void execute(Command<T> command);
    
}
