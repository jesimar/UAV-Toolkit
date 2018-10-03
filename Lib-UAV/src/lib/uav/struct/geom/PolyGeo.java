package lib.uav.struct.geom;

import java.util.LinkedList;

/**
 * Concrete class that implements a poly using geographic coordinates.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public class PolyGeo extends Poly{
    
    private final String name;
    private final double vetx[];
    private final double vety[];
    private final double vetz[];
    private final int npoints;    
    
    /**
     * Class constructor.
     * @param name name of poly
     * @param vetx vector with values of x
     * @param vety vector with values of y
     * @param vetz vector with values of z
     * @param npoints number of points
     * @since version 3.0.0
     */
    public PolyGeo(String name, double vetx[], double vety[], double vetz[], int npoints){
        this.name = name;        
        this.vetx = vetx;
        this.vety = vety;
        this.vetz = vetz;
        this.npoints = npoints;
    }    
    
    /**
     * Class constructor.
     * @param name name of poly
     * @param listGeo list of poly
     * @since version 3.0.0
     */
    public PolyGeo(String name, LinkedList<PointGeo> listGeo){
        this.name = name;
        this.npoints = listGeo.size();
        this.vetx = new double[listGeo.size()];
        this.vety = new double[listGeo.size()];
        this.vetz = new double[listGeo.size()];
        for (int i = 0; i < listGeo.size(); i++){
            vetx[i] = listGeo.get(i).getLng();
            vety[i] = listGeo.get(i).getLat();
            vetz[i] = listGeo.get(i).getAlt();
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
     * Gets the point geo i
     * @param i the i-th point of the vector.
     * @return the i-th point
     * @since version 3.0.0
     */
    public PointGeo getPointGeo(int i){
        return new PointGeo(vetx[i], vety[i], vetz[i]);
    }
    
    /**
     * Gets the central point of the poly
     * @return the central point of the poly
     * @since version 3.0.0
     */
    public PointGeo getPointGeoCenter(){
        return new PointGeo(getCenterX(), getCenterY(), vetz[0]);
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
