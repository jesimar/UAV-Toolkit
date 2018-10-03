package lib.uav.struct.mission;

import lib.uav.struct.geom.Position3D;
import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 * A class models drone mission/route in cartesian coordinates.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Mission3D {
    
    private final List<Position3D> mission = new LinkedList<>();
    
    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Mission3D(){
        
    }
    
    /**
     * Gets the mission
     * @return the mission
     * @since version 2.0.0
     */
    public List<Position3D> getMission(){
        return mission;
    }
    
    /**
     * Gets the position
     * @param i the index of the i-th position
     * @return the position
     * @since version 4.0.0
     */
    public Position3D getPosition(int i){
        return mission.get(i);
    }
    
    /**
     * Add a new position at the end of the mission
     * @param position position to be added
     * @since version 4.0.0
     */
    public void addPosition(Position3D position){
        mission.add(position);
    }
    
    /**
     * Remove the last position of the mission
     * @since version 4.0.0
     */
    public void removePosition(){
        mission.remove(mission.size()-1);
    }
    
    /**
     * Remove the i-th position of the mission
     * @param i the index of the i-th position
     * @since version 4.0.0
     */
    public void removePosition(int i){
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
        StandardPrints.printMsgEmph("Mission3D [Size: " + mission.size() + "]");
        for (Position3D wpt: mission){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
