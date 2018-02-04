package uav.generic.struct;

import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 *
 * @author jesimar
 */
public class Mission3D {
    
    private final List<Position3D> mission = new LinkedList<>();
    
    public Mission3D(){
        
    }
    
    public void addPosition3D(Position3D wpt){
        mission.add(wpt);
    }
    
    public List<Position3D> getMission(){
        return mission;
    }
    
    public Position3D getPosition3D(int i){
        return mission.get(i);
    }
    
    public int size(){
        return mission.size();
    }
    
    public void printMission(){
        StandardPrints.printMsgEmph("Mission3D");
        for (Position3D wpt: mission){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
