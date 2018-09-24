package uav.generic.struct.mission;

import uav.generic.struct.Waypoint;
import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 * Classe que modela a rota do drone em coordenadas geográficas.
 * @author Jesimar S. Arantes
 */
public class Route {
    
    private final List<Waypoint> route = new LinkedList<>();
    
    /**
     * Class constructor.
     */
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
    
    public void removeWaypoint(){
        route.remove(route.size()-1);
    }
    
    public void removeWaypoint(int i){
        route.remove(i);
    }
    
    public int size(){
        return route.size();
    }
    
    public void printRoute(){
        StandardPrints.printMsgEmph("Route [Size: " + route.size() + "]");
        for (Waypoint wpt: route){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
