package uav.generic.struct;

import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Mission {
    
    private final List<Waypoint> mission = new LinkedList<>();
    
    public Mission(){
        
    }
    
    public void addWaypoint(Waypoint wpt){
        mission.add(wpt);
    }
    
    public List<Waypoint> getMission(){
        return mission;
    }
    
    public Waypoint getWaypoint(int i){
        return mission.get(i);
    }
    
    public void removeWaypoint(){
        mission.remove(mission.size()-1);
    }
    
    public int size(){
        return mission.size();
    }
    
    public void printMission(){
        StandardPrints.printMsgEmph("Mission");
        for (Waypoint wpt: mission){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
