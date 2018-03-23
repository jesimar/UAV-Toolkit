package uav.mission_creator.struct.geom;

import java.io.Serializable;

/**
 *
 * @author Jesimar S. Arantes
 */
public abstract class Point implements Serializable{
    
    public abstract double distance(Point point);
    
}
