package uav.mission_creator.converter.map;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Poly2D {
    
    private final String nameRegion;
    private final TypeRegion typeRegion;
    private final Point2D[] coordenates;

    public Poly2D(String nameRegion, TypeRegion typeRegion, Point2D[] coordenates) {
        this.nameRegion = nameRegion;
        this.typeRegion = typeRegion;
        this.coordenates = coordenates;
    }
    
    public String getNameRegion() {
        return nameRegion;
    }

    public TypeRegion getTypeRegion() {
        return typeRegion;
    }

    public Point2D[] getCoordenates() {
        return coordenates;
    }
    
}
