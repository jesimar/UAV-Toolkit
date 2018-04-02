package uav.generic.struct;

import lib.color.StandardPrints;

/**
 * Classe que modela um waypoint para ser enviado em formato JSON.
 * @author Jesimar S. Arantes
 */
public class WaypointJSON {
    
    private final Waypoint waypoint;
    
    /**
     * Class constructor.
     * @param wpt waypoint
     */
    public WaypointJSON(Waypoint wpt){
        this.waypoint = wpt;
    }
    
    /**
     * Class constructor.
     * @param action command of action (TAKEOFF, GOTO, LAND, LAND_VERTICAL, RTL)
     * @param lat latitude of waypoint.
     * @param lng longitude of waypoint.
     * @param alt altitude of waypoint.
     */
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
