package uav.generic.struct.geom;

import java.util.List;

/**
 * Classe concreta que implementa uma linha 2D.
 * @author Jesimar S. Arantes
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

    public String getName() {
        return name;
    }

    public double[] getVetx() {
        return vetx;
    }

    public double[] getVety() {
        return vety;
    }
    
    public Point2D getPoint2D(int i){
        return new Point2D(vetx[i], vety[i]);
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
    
    public String toStringVetX() {
        String s = "";
        for (int i = 0; i < vetx.length - 1; i++){
            s = s + vetx[i] + ",";
        }
        s = s + vetx[vetx.length-1];
        return s;
    } 
    
    public String toStringVetY() {
        String s = "";
        for (int i = 0; i < vety.length - 1; i++){
            s = s + vety[i] + ",";
        }
        s = s + vety[vety.length-1];
        return s;
    } 
    
    public String toString(int i) {
        return String.format("%.16g %.16g\n", vetx[i], vety[i]);
    }  
  
}
