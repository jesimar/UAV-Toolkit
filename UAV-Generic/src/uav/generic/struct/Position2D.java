package uav.generic.struct;

/**
 *
 * @author jesimar
 */
public class Position2D extends Position{
    
    private double x;
    private double y;

    public Position2D() {
    }    

    public Position2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
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
    
    @Override
    public String toString() {
        return "(x, y) = (" + x + ", " + y + ")";
    }
    
}
