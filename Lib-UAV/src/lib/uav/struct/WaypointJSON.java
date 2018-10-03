package lib.uav.struct;

import lib.color.StandardPrints;

/**
 * The class models a waypoint to be transmitted in JSON format.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class WaypointJSON {
    
    private final Waypoint waypoint;
    
    /**
     * Class constructor.
     * @param waypoint the waypoint
     * @since version 2.0.0
     */
    public WaypointJSON(Waypoint waypoint){
        this.waypoint = waypoint;
    }
    
    /**
     * Class constructor.
     * @param action command of action (TAKEOFF, GOTO, LAND, LAND_VERTICAL, RTL)
     * @param lat latitude of waypoint in degrees.
     * @param lng longitude of waypoint in degrees.
     * @param alt altitude of waypoint in meters.
     * @since version 2.0.0
     */
    public WaypointJSON(String action, double lat, double lng, double alt){
        this.waypoint = new Waypoint(action, lat, lng, alt);        
    }
    
    /**
     * Gets a waypoint.
     * @return the waypoint
     * @since version 2.0.0
     */
    public Waypoint getWaypoint(){
        return this.waypoint;
    }
    
    /**
     * Print the waypoint info.
     * @since version 2.0.0
     */
    public void printWaypoint(){
        StandardPrints.printMsgEmph("Waypoint");        
        StandardPrints.printMsgEmph(waypoint.toString());
    }
}
