package uav.generic.struct.geom;

import java.util.List;

/**
 * Concrete class that implements a 3D poly.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public class Poly3D extends Poly{
    
    private final String name;         
    private final double vetx[];
    private final double vety[];
    private final double vetz[];
    private final int npoints;    
    
    /**
     * Class constructor.
     * @param name name of poly
     * @param listPoint3D list of poly
     * @since version 3.0.0
     */
    public Poly3D(String name, List<Point3D> listPoint3D){
        this.name = name;
        this.npoints = listPoint3D.size();
        this.vetx = new double[npoints];
        this.vety = new double[npoints];
        this.vetz = new double[npoints];
        for (int i = 0; i < npoints; i++){
            vetx[i] = listPoint3D.get(i).getX();
            vety[i] = listPoint3D.get(i).getY();
            vetz[i] = listPoint3D.get(i).getZ();
        }
    }

    /**
     * Gets the name of the poly
     * @return the name of the poly
     * @since version 3.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of vector x
     * @return the value of vector x
     * @since version 3.0.0
     */
    public double[] getVetx() {
        return vetx;
    }
    
    /**
     * Gets the value of vector y
     * @return the value of vector y
     * @since version 3.0.0
     */
    public double[] getVety() {
        return vety;
    }
    
    /**
     * Gets the value of vector z
     * @return the value of vector z
     * @since version 3.0.0
     */
    public double[] getVetz() {
        return vetz;
    }

    /**
     * Gets the number of points
     * @return the number of points
     * @since version 3.0.0
     */
    public int getNpoints() {
        return npoints;
    }
    
    /**
     * Gets the central value on the x-axis
     * @return the central value on the x-axis
     * @since version 3.0.0
     */
    public double getCenterX(){
        double cx = 0;
        for (int i = 0; i < vetx.length; i++){
            cx += vetx[i];
        }
        return cx/vetx.length;
    }
    
    /**
     * Gets the central value on the y-axis
     * @return the central value on the y-axis
     * @since version 3.0.0
     */
    public double getCenterY(){
        double cy = 0;
        for (int i = 0; i < vety.length; i++){
            cy += vety[i];
        }
        return cy/vety.length;
    }
    
    /**
     * Gets a string with the central point of the poly
     * @return the string with central point of the poly
     * @since version 3.0.0
     */
    @Override
    public String toString() {
        return String.format("%.16g %.16g %.16g\n", getCenterX(), getCenterY(), vetz[0]);
    }
    
    /**
     * Gets a string with the central point of the poly
     * @param height heigth value
     * @return the string with central point of the poly
     * @since version 3.0.0
     */
    public String toString(double height) {
        return String.format("%.16g %.16g %.16g\n", getCenterX(), getCenterY(), height);
    }  
    
}
