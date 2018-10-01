package uav.generic.struct.geom;

import java.util.List;

/**
 * Concrete class that implements a 2D line.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Line2D extends Line{
    
    private final String name;
    private final double vetx[];
    private final double vety[];
    private final int npoints;
    
    /**
     * Class constructor.
     * @param name line name.
     * @param vetx vector with coordinates x.
     * @param vety vector with coordinates y.
     */
    public Line2D(String name, double vetx[], double vety[]){
        this.name = name;
        this.vetx = vetx;
        this.vety = vety;       
        this.npoints = vetx.length;
    }
    
    /**
     * Class constructor.
     * @param name line name.
     * @param listLine2D list with 2D line points.
     */
    public Line2D(String name, List<Point2D> listLine2D){
        this.name = name;
        this.npoints = listLine2D.size();
        this.vetx = new double[npoints];
        this.vety = new double[npoints];
        for (int i = 0; i < npoints; i++){
            vetx[i] = listLine2D.get(i).getX();
            vety[i] = listLine2D.get(i).getY();
        }
    }

    /**
     * Gets the name of the line
     * @return the name of the line
     * @since version 2.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value of vector x
     * @return the value of vector x
     * @since version 2.0.0
     */
    public double[] getVetx() {
        return vetx;
    }

    /**
     * Gets the value of vector y
     * @return the value of vector y
     * @since version 2.0.0
     */
    public double[] getVety() {
        return vety;
    }
    
    /**
     * Gets the i-th point 2D
     * @param i index of the point
     * @return the i-th point of the vector.
     * @since version 2.0.0
     */
    public Point2D getPoint2D(int i){
        return new Point2D(vetx[i], vety[i]);
    }
    
    /**
     * Gets the central value on the x-axis
     * @return the central value on the x-axis
     * @since version 2.0.0
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
     * @since version 2.0.0
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
     * @since version 2.0.0
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
     * @since version 2.0.0
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
     * @return the string with value of points x, y
     * @since version 2.0.0
     */
    public String toString(int i) {
        return String.format("%.16g %.16g\n", vetx[i], vety[i]);
    }  

}
