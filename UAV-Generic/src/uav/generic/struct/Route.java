package uav.generic.struct;

import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 *
 * @author jesimar
 */
public class Route {
    
    private final List<Waypoint> route = new LinkedList<>();
    
    public Route(){
        
    }
    
    public void addWaypoint(Waypoint wpt){
        route.add(wpt);
    }
    
    public List<Waypoint> getRoute(){
        return route;
    }
    
    public Waypoint getWaypoint(int i){
        return route.get(i);
    }
    
    public int size(){
        return route.size();
    }
    
    public void printRoute(){
        StandardPrints.printMsgEmph("Route");
        for (Waypoint wpt: route){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
