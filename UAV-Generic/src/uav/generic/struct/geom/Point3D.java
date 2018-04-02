package uav.generic.struct.geom;

/**
 * Classe concreta que implementa um ponto 3D.
 * @author Jesimar S. Arantes
 */
public class Point3D extends Point{
    
    private double x;
    private double y;
    private double z;

    /**
     * Class constructor.
     */
    public Point3D() {
    }

    /**
     * Class constructor.
     * @param x coordinate x of point.
     * @param y coordinate y of point.
     * @param z coordinate z of point.
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Class constructor.
     * @param point3D point with 3D coordinates.
     */
    public Point3D(Point3D point3D) {
        this.x = point3D.x;
        this.y = point3D.y;
        this.z = point3D.z;
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
    
    @Override
    public String toString() {
        return "(x, y, z) = (" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public double distance(Point point) {
        Point3D p = (Point3D) point;
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y) + (p.z - z)*(p.z - z));
    }        
    
}
