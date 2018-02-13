/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mission.creator.data;

import java.util.List;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Line3D {
    
    private final String name;
    private final double vetx[];
    private final double vety[];
    private final double vetz[];
    private final int npoints;
    
    public Line3D(String name, double vetx[], double vety[], double vetz[]){
        this.name = name;
        this.vetx = vetx;
        this.vety = vety;        
        this.vetz = vetz;        
        this.npoints = vetx.length;
    }
    
    public Line3D(String name, List<Point3D> listLine3D){
        this.name = name;
        this.npoints = listLine3D.size();
        this.vetx = new double[npoints];
        this.vety = new double[npoints];
        this.vetz = new double[npoints];
        for (int i = 0; i < npoints; i++){
            vetx[i] = listLine3D.get(i).getX();
            vety[i] = listLine3D.get(i).getY();
            vetz[i] = listLine3D.get(i).getH();
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
    
    public Point3D getPoint3D(int i){
        return new Point3D(vetx[i], vety[i], vetz[i]);
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
        return String.format("%.16g %.16g %.16g\n", vetx[i], vety[i], vetz[i]);
    }  
    
    public String toString(int i, double inch) {
        return String.format("%.16g %.16g %.16g\n", vetx[i], vety[i], vetz[i]+ inch);
    }  
}
