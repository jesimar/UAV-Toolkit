package lib.uav.struct.geom;

/**
 * Concrete class that implements a 2D position.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Position2D extends Position{
    
    private double x;
    private double y;

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Position2D() {
    }    

    /**
     * Class constructor.
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     * @since version 2.0.0
     */
    public Position2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Class constructor.
     * @param position2D point with 2D coordinates.
     * @since version 2.0.0
     */
    public Position2D(Position2D position2D) {
        this.x = position2D.x;
        this.y = position2D.y;
    }

    /**
     * Gets the value of x
     * @return the value of x
     * @since version 2.0.0
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the value of x
     * @param x value of x
     * @since version 2.0.0
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Gets the value of y
     * @return the value of y
     * @since version 2.0.0
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the value of y
     * @param y value of y
     * @since version 2.0.0
     */
    public void setY(double y) {
        this.y = y;
    }  
    
    /**
     * Gets a string with values x, y
     * @return a string with value: x + ", " + y
     * @since version 2.0.0
     */
    public String string() {
        return x + ", " + y;
    }
    
    /**
     * Gets a string with values x, y
     * @return a string with value: "(x, y) = (" + x + ", " + y + ")"
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return "(x, y) = (" + x + ", " + y + ")";
    }
    
}
