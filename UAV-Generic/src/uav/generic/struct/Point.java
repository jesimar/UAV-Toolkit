package uav.generic.struct;

import java.io.Serializable;

/**
 *
 * @author jesimar
 */
public abstract class Point implements Serializable{
    
    public abstract double distance(Point point);
    
}
