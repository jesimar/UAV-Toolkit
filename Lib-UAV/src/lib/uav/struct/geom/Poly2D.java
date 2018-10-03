package lib.uav.struct.geom;

import java.util.List;

/**
 * Concrete class that implements a 2D poly.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class Poly2D extends Poly{
    
    private final String name;         
    private final double vetx[];
    private final double vety[];
    private final int npoints;    
    
    /**
     * Class constructor.
     * @param name name of poly
     * @param listPoint2D list of poly
     * @since version 4.0.0
     */
    public Poly2D(String name, List<Point2D> listPoint2D){
        this.name = name;
        this.npoints = listPoint2D.size();
        this.vetx = new double[npoints];
        this.vety = new double[npoints];
        for (int i = 0; i < npoints; i++){
            vetx[i] = listPoint2D.get(i).getX();
            vety[i] = listPoint2D.get(i).getY();
        }
    }

    /**
     * Gets the name of the poly
     * @return the name of the poly
     * @since version 4.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of vector x
     * @return the value of vector x
     * @since version 4.0.0
     */
    public double[] getVetx() {
        return vetx;
    }
    
    /**
     * Gets the value of vector y
     * @return the value of vector y
     * @since version 4.0.0
     */
    public double[] getVety() {
        return vety;
    }
    
    /**
     * Gets the number of points
     * @return the number of points
     * @since version 4.0.0
     */
    public int getNpoints() {
        return npoints;
    }
    
    /**
     * Gets the central value on the x-axis
     * @return the central value on the x-axis
     * @since version 4.0.0
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
     * @since version 4.0.0
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
     * @since version 4.0.0
     */
    @Override
    public String toString() {
        return String.format("%.16g %.16g\n", getCenterX(), getCenterY());
    }
 
}
