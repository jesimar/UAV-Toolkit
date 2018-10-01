package uav.generic.struct.mission;

import uav.generic.struct.Waypoint;
import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 * The class models the drone route in geographic coordinates.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Route {
    
    private final List<Waypoint> route = new LinkedList<>();
    
    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Route(){
        
    }
    
    /**
     * Gets the route
     * @return the route
     * @since version 2.0.0
     */
    public List<Waypoint> getRoute(){
        return route;
    }
    
    /**
     * Gets the waypoint
     * @param i the index of the i-th waypoint
     * @return the waypoint
     * @since version 2.0.0
     */
    public Waypoint getWaypoint(int i){
        return route.get(i);
    }
    
    /**
     * Add a new waypoint at the end of the route
     * @param waypoint waypoint to be added
     * @since version 2.0.0
     */
    public void addWaypoint(Waypoint waypoint){
        route.add(waypoint);
    }
    
    /**
     * Remove the last waypoint of the route
     * @since version 2.0.0
     */
    public void removeWaypoint(){
        route.remove(route.size()-1);
    }
    
    /**
     * Remove the i-th waypoint of the route
     * @param i the index of the i-th waypoint
     * @since version 2.0.0
     */
    public void removeWaypoint(int i){
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
        StandardPrints.printMsgEmph("Route [Size: " + route.size() + "]");
        for (Waypoint wpt: route){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
