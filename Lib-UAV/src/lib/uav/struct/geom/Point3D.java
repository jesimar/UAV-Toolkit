package lib.uav.struct.geom;

/**
 * Concrete class that implements a 3D point.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Point3D extends Point{
    
    private double x;
    private double y;
    private double z;

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Point3D() {
    }

    /**
     * Class constructor.
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     * @param z coordinate z of point.
     * @since version 2.0.0
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Class constructor.
     * @param point3D point with 3D coordinates.
     * @since version 2.0.0
     */
    public Point3D(Point3D point3D) {
        this.x = point3D.x;
        this.y = point3D.y;
        this.z = point3D.z;
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
     * Gets the value of z
     * @return the value of z
     * @since version 2.0.0
     */
    public double getZ() {
        return z;
    }

    /**
     * Sets the value of z
     * @param z value of z
     * @since version 2.0.0
     */
    public void setZ(double z) {
        this.z = z;
    } 
    
    /**
     * Gets a string with values x, y, z
     * @return a string with value: "(x, y, z) = (" + x + ", " + y + ", " + z + ")"
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return "(x, y, z) = (" + x + ", " + y + ", " + z + ")";
    }

    /**
     * Calculates the distance between two points
     * @param point point to calculate distance
     * @return the distance between two points
     * @since version 2.0.0
     */
    @Override
    public double distance(Point point) {
        Point3D p = (Point3D) point;
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.z - z)*(p.z - z));
    }        
    
}
