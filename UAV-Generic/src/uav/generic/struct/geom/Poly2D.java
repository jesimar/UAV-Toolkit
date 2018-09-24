package uav.generic.struct.geom;

import java.util.List;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Poly2D extends Poly{
    
    private final String name;         
    private final double vetx[];
    private final double vety[];
    private final int npoints;    
    
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

    public String getName() {
        return name;
    }

    public double[] getVetx() {
        return vetx;
    }
    
    public double[] getVety() {
        return vety;
    }
    
    public int getNpoints() {
        return npoints;
    }
    
    public double getCenterX(){
        double cx = 0;
        for (int i = 0; i < vetx.length; i++){
            cx += vetx[i];
        }
        return cx/vetx.length;
    }
    
    public double getCenterY(){
        double cy = 0;
        for (int i = 0; i < vety.length; i++){
            cy += vety[i];
        }
        return cy/vety.length;
    }
    
    @Override
    public String toString() {
        return String.format("%.16g %.16g\n", getCenterX(), getCenterY());
    }
 
}
