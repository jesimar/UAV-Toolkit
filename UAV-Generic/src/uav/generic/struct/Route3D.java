package uav.generic.struct;

import java.util.LinkedList;
import java.util.List;
import lib.color.StandardPrints;

/**
 *
 * @author jesimar
 */
public class Route3D {
    
    private final List<Position3D> route = new LinkedList<>();
    
    public Route3D(){
        
    }
    
    public void addPosition3D(Position3D wpt){
        route.add(wpt);
    }
    
    public List<Position3D> getRoute(){
        return route;
    }
    
    public Position3D getPosition3D(int i){
        return route.get(i);
    }
    
    public int size(){
        return route.size();
    }
    
    public void printRoute(){
        StandardPrints.printMsgEmph("Route3D");
        for (Position3D wpt: route){
            StandardPrints.printMsgEmph(wpt.toString());
        }
    }
}
