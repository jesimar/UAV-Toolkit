package uav.manager.check;

/**
 * @author Marcio S. Arantes
 * @see version 3.0.0
 */
public interface TriConsumer<T, U, V> {
    void accept(T t, U u, V v);
}
