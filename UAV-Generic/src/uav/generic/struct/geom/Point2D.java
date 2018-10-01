package uav.generic.struct.geom;

/**
 * Concrete class that implements a 2D point.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Point2D extends Point{
    
    private double x;
    private double y;

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Point2D() {
    }

    /**
     * Class constructor.
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     * @since version 2.0.0
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Class constructor.
     * @param point2D point with 2D coordinates.
     * @since version 2.0.0
     */
    public Point2D(Point2D point2D) {
        this.x = point2D.x;
        this.y = point2D.y;
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
     * @return a string with value: "(x, y) = (" + x + ", " + y + ")"
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return "(x, y) = (" + x + ", " + y + ")";
    }

    /**
     * Calculates the distance between two points
     * @param point point to calculate distance
     * @return the distance between two points
     * @since version 2.0.0
     */
    @Override
    public double distance(Point point) {
        Point2D p = (Point2D) point;
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y));
    }
}
