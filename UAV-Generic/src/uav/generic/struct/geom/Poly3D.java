package uav.generic.struct.geom;

import java.util.List;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Poly3D {
    
    private final String name;         
    private final double vetx[];
    private final double vety[];
    private final double vetz[];
    private final int npoints;    
    
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

    public String getName() {
        return name;
    }

    public double[] getVetx() {
        return vetx;
    }
    
    public double[] getVety() {
        return vety;
    }
    
    public double[] getVetz() {
        return vetz;
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
        return String.format("%.16g %.16g %.16g\n", getCenterX(), getCenterY(), vetz[0]);
    }
    
    public String toString(double height) {
        return String.format("%.16g %.16g %.16g\n", getCenterX(), getCenterY(), height);
    }  
    
}
