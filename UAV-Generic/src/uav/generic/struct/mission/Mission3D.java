package uav.generic.struct.mission;

import uav.generic.struct.geom.Position3D;
import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 * Classe que modela a missão do drone em coordenadas cartesianas.
 * @author Jesimar S. Arantes
 */
public class Mission3D {
    
    private final List<Position3D> mission = new LinkedList<>();
    
    /**
     * Class constructor.
     */
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
    
    public void removePosition3D(){
        mission.remove(mission.size()-1);
    }
    
    public void removePosition3D(int i){
        mission.remove(i);
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
