package lib.uav.struct.geom;

import java.util.List;

/**
 * Concrete class that implements a 3D line.
 * @author Jesimar S. Arantes
 * @since version 1.0.0
 */
public class Line3D extends Line{
    
    private final String name;
    private final double vetx[];
    private final double vety[];
    private final double vetz[];
    private final int npoints;
    
    /**
     * Class constructor.
     * @param name line name.
     * @param vetx vector with coordinates x.
     * @param vety vector with coordinates y.
     * @param vetz vector with coordinates z.
     * @since version 1.0.0
     */
    public Line3D(String name, double vetx[], double vety[], double vetz[]){
        this.name = name;
        this.vetx = vetx;
        this.vety = vety;        
        this.vetz = vetz;        
        this.npoints = vetx.length;
    }
    
    /**
     * Class constructor.
     * @param name line name.
     * @param listLine3D list with 3D line points.
     * @since version 1.0.0
     */
    public Line3D(String name, List<Point3D> listLine3D){
        this.name = name;
        this.npoints = listLine3D.size();
        this.vetx = new double[npoints];
        this.vety = new double[npoints];
        this.vetz = new double[npoints];
        for (int i = 0; i < npoints; i++){
            vetx[i] = listLine3D.get(i).getX();
            vety[i] = listLine3D.get(i).getY();
            vetz[i] = listLine3D.get(i).getZ();
        }
    }

    /**
     * Gets the name of the line
     * @return the name of the line
     * @since version 1.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of vector x
     * @return the value of vector x
     * @since version 1.0.0
     */
    public double[] getVetx() {
        return vetx;
    }

    /**
     * Gets the value of vector y
     * @return the value of vector y
     * @since version 1.0.0
     */
    public double[] getVety() {
        return vety;
    }

    /**
     * Gets the value of vector z
     * @return the value of vector z
     * @since version 1.0.0
     */
    public double[] getVetz() {
        return vetz;
    }
    
    /**
     * Gets the i-th point 3D
     * @param i index of the point
     * @return the i-th point of the vector.
     * @since version 1.0.0
     */
    public Point3D getPoint3D(int i){
        return new Point3D(vetx[i], vety[i], vetz[i]);
    }
    
    /**
     * Gets the central value on the x-axis
     * @return the central value on the x-axis
     * @since version 1.0.0
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
     * @since version 1.0.0
     */
    public double getCenterY(){
        double cy = 0;
        for (int i = 0; i < vety.length; i++){
            cy += vety[i];
        }
        return cy/vety.length;
    }
    
    /**
     * Converts the vector x into a string of a line
     * @return line with vector x 
     * @since version 1.0.0
     */
    public String toStringVetX() {
        String s = "";
        for (int i = 0; i < vetx.length - 1; i++){
            s = s + vetx[i] + ",";
        }
        s = s + vetx[vetx.length-1];
        return s;
    } 
    
    /**
     * Converts the vector y into a string of a line
     * @return line with vector y
     * @since version 1.0.0
     */
    public String toStringVetY() {
        String s = "";
        for (int i = 0; i < vety.length - 1; i++){
            s = s + vety[i] + ",";
        }
        s = s + vety[vety.length-1];
        return s;
    } 
    
    /**
     * Gets a string with the i-th point of the line
     * @param i index of the point
     * @return the string with value of points x, y, z
     * @since version 1.0.0
     */
    public String toString(int i) {
        return String.format("%.16g %.16g %.16g\n", vetx[i], vety[i], vetz[i]);
    }  
    
    /**
     * Gets a string with the i-th point of the line
     * @param i index of the point
     * @param inch displacement factor in z-axis
     * @return the string with value of points x, y, z
     * @since version 1.0.0
     */
    public String toString(int i, double inch) {
        return String.format("%.16g %.16g %.16g\n", vetx[i], vety[i], vetz[i]+ inch);
    }  
}
