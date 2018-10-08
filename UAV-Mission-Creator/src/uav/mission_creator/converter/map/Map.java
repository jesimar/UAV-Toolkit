package uav.mission_creator.converter.map;

import java.util.LinkedList;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Map {
    
    private final LinkedList<Poly2D> regions;

    public Map() {
        regions = new LinkedList();
    }
    
    public void addPoly(Poly2D poly){
        regions.add(poly);
    }
    
}
