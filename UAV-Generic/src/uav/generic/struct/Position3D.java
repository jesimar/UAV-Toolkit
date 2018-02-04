package uav.generic.struct;

/**
 *
 * @author jesimar
 */
public class Position3D extends Position{
    
    private double x;
    private double y;
    private double z;

    public Position3D() {
    }

    public Position3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
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
    
    @Override
    public String toString() {
        return "(x, y, z) = (" + x + ", " + y + ", " + z + ")";
    }
}
