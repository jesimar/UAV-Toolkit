package uav.generic.struct;

import lib.color.StandardPrints;

/**
 *
 * @author Jesimar S. Arantes
 */
public class WaypointJSON {
    
    private final Waypoint waypoint;
    
    public WaypointJSON(Waypoint wpt){
        this.waypoint = wpt;
    }
    
    public WaypointJSON(String action, double lat, double lng, double alt){
        this.waypoint = new Waypoint(action, lat, lng, alt);        
    }
    
    public Waypoint getWaypoint(){
        return this.waypoint;
    }
    
    public void printWaypoint(){
        StandardPrints.printMsgEmph("Waypoint");        
        StandardPrints.printMsgEmph(waypoint.toString());
    }
}
