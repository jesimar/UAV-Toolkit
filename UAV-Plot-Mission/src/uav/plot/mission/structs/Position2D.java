package uav.generic.struct.geom;

/**
 * Classe concreta que implementa uma posição 2D.
 * @author Jesimar S. Arantes
 */
public class Position2D extends Position{
    
    private double x;
    private double y;

    /**
     * Class constructor.
     */
    public Position2D() {
    }    

    /**
     * Class constructor.
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     */
    public Position2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Class constructor.
     * @param position2D point with 2D coordinates.
     */
    public Position2D(Position2D position2D) {
        this.x = position2D.x;
        this.y = position2D.y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }  
    
    public String string() {
        return x + ", " + y;
    }
    
    @Override
    public String toString() {
        return "(x, y) = (" + x + ", " + y + ")";
    }
    
}
