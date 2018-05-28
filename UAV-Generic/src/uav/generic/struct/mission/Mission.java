package uav.generic.struct.mission;

import uav.generic.struct.Waypoint;
import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 * Classe que modela a missão do drone em coordenadas geográficas.
 * @author Jesimar S. Arantes
 */
public class Mission {
    
    private final List<Waypoint> mission = new LinkedList<>();
    
    /**
     * Class constructor.
     */
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
    
    public void removeWaypoint(int i){
        mission.remove(i);
    }
    
    public int size(){
        return mission.size();
    }
    
    public void printMission(){
        StandardPrints.printMsgEmph("Mission");
        StandardPrints.printMsgEmph("Size: " + mission.size());
        for (Waypoint wpt: mission){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
