/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uav.mission.creator.data;

/**
 *
 * @author Jesimar S. Arantes
 */
public class LineGeo {
    
    private final String name;
    private final double vetx[];
    private final double vety[];
    private final double vetz[];
    private final int npoints;
    
    public LineGeo(String name, double vetx[], double vety[], double vetz[]){
        this.name = name;
        this.vetx = vetx;
        this.vety = vety;        
        this.vetz = vetz;
        this.npoints = vetx.length;
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
    
    public PointGeo getPointGeo(int i){
        return new PointGeo(vetx[i], vety[i], vetz[i]);
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
    
    public double getCenterZ(){
        double cz = 0;
        for (int i = 0; i < vetz.length; i++){
            cz += vetz[i];
        }
        return cz/vetz.length;
    }
    
    public String toString(int i) {
        return String.format("%.16g %.16g %.16g\n", vetx[i], vety[i], vetz[i]);
    }  
    
    public String toString(int i, double inch) {
        return String.format("%.16g %.16g %.16g\n", vetx[i], vety[i], vetz[i]+ inch);
    }  
}
