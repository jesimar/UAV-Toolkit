/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mission.creator.data;

import java.util.LinkedList;

/**
 *
 * @author Jesimar S. Arantes
 */
public class PolyGeo {
    
    private final String name;
    private final double vetx[];
    private final double vety[];
    private final double vetz[];
    private final int npoints;    
    
    public PolyGeo(String name, double vetx[], double vety[], double vetz[], int npoints){
        this.name = name;        
        this.vetx = vetx;
        this.vety = vety;
        this.vetz = vetz;
        this.npoints = npoints;
    }    
    
    public PolyGeo(String name, LinkedList<PointGeo> listGeo){
        this.name = name;
        this.npoints = listGeo.size();
        this.vetx = new double[listGeo.size()];
        this.vety = new double[listGeo.size()];
        this.vetz = new double[listGeo.size()];
        for (int i = 0; i < listGeo.size(); i++){
            vetx[i] = listGeo.get(i).getLongitude();
            vety[i] = listGeo.get(i).getLatitude();
            vetz[i] = listGeo.get(i).getAltitude();
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
    
    public PointGeo getPointGeo(int i){
        return new PointGeo(vetx[i], vety[i], vetz[i]);
    }
    
    public PointGeo getPointGeoCenter(){
        return new PointGeo(getCenterX(), getCenterY(), vetz[0]);
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
