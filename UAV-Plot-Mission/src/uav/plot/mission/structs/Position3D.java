package uav.generic.struct.geom;

/**
 * Classe concreta que implementa uma posição 2D.
 * @author Jesimar S. Arantes
 */
public class Position3D extends Position{
    
    private double x;
    private double y;
    private double z;

    /**
     * Class constructor.
     */
    public Position3D() {
    }

    /**
     * Class constructor.
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     * @param z coordinate z of point.
     */
    public Position3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * Class constructor.
     * @param position3D point with 3D coordinates.
     */
    public Position3D(Position3D position3D) {
        this.x = position3D.x;
        this.y = position3D.y;
        this.z = position3D.z;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
    
    public String string() {
        return x + ", " + y + ", " + z;
    }
    
    @Override
    public String toString() {
        return "(x, y, z) = (" + x + ", " + y + ", " + z + ")";
    }
}
