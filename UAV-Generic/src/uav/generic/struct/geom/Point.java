package uav.generic.struct.geom;

import java.io.Serializable;

/**
 * Classe abstrata que modela um ponto.
 * @author Jesimar S. Arantes
 */
public abstract class Point implements Serializable{
    
    public abstract double distance(Point point);
    
}
