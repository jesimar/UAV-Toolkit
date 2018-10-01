package uav.manager.check;

import java.util.function.Consumer;

/**
 * @author Marcio S. Arantes
 * @see version 3.0.0
 */
public interface Check<T> {
    
    public void check(Consumer<T> consumer);
    
}
