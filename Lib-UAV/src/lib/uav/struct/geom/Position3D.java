package lib.uav.struct.geom;

/**
 * Concrete class that implements a 3D position.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Position3D extends Position{
    
    private double x;
    private double y;
    private double z;

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Position3D() {
    }

    /**
     * Class constructor.
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     * @param z coordinate z of point.
     * @since version 2.0.0
     */
    public Position3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Class constructor.
     * @param position3D point with 3D coordinates.
     * @since version 2.0.0
     */
    public Position3D(Position3D position3D) {
        this.x = position3D.x;
        this.y = position3D.y;
        this.z = position3D.z;
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
     * @return a string with value: x + ", " + y + ", " + z
     * @since version 2.0.0
     */
    public String string() {
        return x + ", " + y + ", " + z;
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
}
