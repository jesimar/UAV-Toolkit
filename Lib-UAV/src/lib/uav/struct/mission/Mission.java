package lib.uav.struct.mission;

import lib.uav.struct.Waypoint;
import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 * A class models drone mission/route in geographic coordinates.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Mission {
    
    private final List<Waypoint> mission = new LinkedList<>();
    
    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Mission(){
        
    }
    
    /**
     * Gets the mission
     * @return the mission
     * @since version 2.0.0
     */
    public List<Waypoint> getMission(){
        return mission;
    }
    
    /**
     * Gets the waypoint
     * @param i the index of the i-th waypoint
     * @return the waypoint
     * @since version 2.0.0
     */
    public Waypoint getWaypoint(int i){
        return mission.get(i);
    }
    
    /**
     * Add a new waypoint at the end of the mission
     * @param waypoint waypoint to be added
     * @since version 2.0.0
     */
    public void addWaypoint(Waypoint waypoint){
        mission.add(waypoint);
    }
    
    /**
     * Remove the last waypoint of the mission
     * @since version 2.0.0
     */
    public void removeWaypoint(){
        mission.remove(mission.size()-1);
    }
    
    /**
     * Remove the i-th waypoint of the mission
     * @param i the index of the i-th waypoint
     * @since version 2.0.0
     */
    public void removeWaypoint(int i){
        mission.remove(i);
    }
    
    /**
     * Returns the size of mission.
     * @return the size of mission
     * @since version 2.0.0
     */
    public int size(){
        return mission.size();
    }
    
    /**
     * Print the mission
     * @since version 2.0.0
     */
    public void printMission(){
        StandardPrints.printMsgEmph("Mission [Size: " + mission.size() + "]");
        for (Waypoint wpt: mission){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
