package uav.generic.struct.geom;

import java.io.Serializable;

/**
 * Abstract class modeling a point.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public abstract class Point implements Serializable{
    
    /**
     * Calculates the distance between two points
     * @param point point to calculate distance
     * @return the distance between two points
     * @since version 2.0.0
     */
    public abstract double distance(Point point);
    
}
