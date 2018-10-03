package lib.uav.struct.geom;

/**
 * Concrete class that implements a line using geographic coordinates.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public class LineGeo extends Line{
    
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
     * @since version 3.0.0
     */
    public LineGeo(String name, double vetx[], double vety[], double vetz[]){
        this.name = name;
        this.vetx = vetx;
        this.vety = vety;        
        this.vetz = vetz;
        this.npoints = vetx.length;
    }

    /**
     * Gets the name of the line
     * @return the name of the line
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
     * Gets the central point of the line
     * @return the central point of the line
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
     * Gets the central value on the z-axis
     * @return the central value on the z-axis
     * @since version 3.0.0
     */
    public double getCenterZ(){
        double cz = 0;
        for (int i = 0; i < vetz.length; i++){
            cz += vetz[i];
        }
        return cz/vetz.length;
    }
    
    /**
     * Gets a string with the i-th point of the line
     * @param i index of the point
     * @return the string with value of points x, y, z
     * @since version 3.0.0
     */
    public String toString(int i) {
        return String.format("%.16g %.16g %.16g\n", vetx[i], vety[i], vetz[i]);
    }  
    
    /**
     * Gets a string with the i-th point of the line
     * @param i index of the point
     * @param inch displacement factor in z-axis
     * @return the string with value of points x, y, z
     * @since version 3.0.0
     */
    public String toString(int i, double inch) {
        return String.format("%.16g %.16g %.16g\n", vetx[i], vety[i], vetz[i]+ inch);
    }  
}
