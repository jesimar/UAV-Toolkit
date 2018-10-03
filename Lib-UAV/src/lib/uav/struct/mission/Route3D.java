package lib.uav.struct.mission;

import lib.uav.struct.geom.Position3D;
import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 * The class models the drone route in cartesian  coordinates.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Route3D {
    
    private final List<Position3D> route = new LinkedList<>();
    
    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Route3D(){
        
    }
    
    /**
     * Gets the route
     * @return the route
     * @since version 2.0.0
     */
    public List<Position3D> getRoute(){
        return route;
    }
    
    /**
     * Gets the position
     * @param i the index of the i-th position
     * @return the position
     * @since version 4.0.0
     */
    public Position3D getPosition(int i){
        return route.get(i);
    }
    
    /**
     * Add a new position at the end of the route
     * @param position position to be added
     * @since version 4.0.0
     */
    public void addPosition(Position3D position){
        route.add(position);
    }
    
    /**
     * Remove the last position of the route
     * @since version 4.0.0
     */
    public void removePosition(){
        route.remove(route.size()-1);
    }
    
    /**
     * Remove the i-th position of the route
     * @param i the index of the i-th position
     * @since version 4.0.0
     */
    public void removePosition(int i){
        route.remove(i);
    }
    
    /**
     * Returns the size of route.
     * @return the size of route
     * @since version 2.0.0
     */
    public int size(){
        return route.size();
    }
    
    /**
     * Print the route
     * @since version 2.0.0
     */
    public void printRoute(){
        StandardPrints.printMsgEmph("Route3D [Size: " + route.size() + "]");
        for (Position3D wpt: route){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
