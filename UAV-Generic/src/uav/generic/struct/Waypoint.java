package uav.generic.struct;

import uav.generic.struct.geom.PointGeo;

/**
 *
 * @author Jesimar S. Arantes
 */
public class Waypoint {
    
    private final String action;
    private final double lat;//is equivalent to y coordinate.
    private final double lng;//is equivalent to x coordinate.
    private final double alt;//is equivalent to z coordinate.
    
    public Waypoint(String action, double lat, double lng, double alt){
        this.action = action;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    public Waypoint(double lat, double lng, double alt){
        this.action = "";
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    public Waypoint(PointGeo pGeo){
        this.action = "";
        this.lat = pGeo.getLat();
        this.lng = pGeo.getLng();
        this.alt = pGeo.getAlt();
    }

    public String getAction() {
        return action;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getAlt() {
        return alt;
    }
    
    public String string(){
        return action + ", " + lat + ", " + lng + ", " + alt;
    }
    
    public String string2(){
        return lat + ", " + lng + ", " + alt;
    }

    @Override
    public String toString() {
        return "Waypoint: [" + action + ", " + lat + ", " + lng + ", " + alt + "]";
    } 
    
    public String toString2() {
        return "Waypoint: [" + lat + ", " + lng + ", " + alt + "]";
    } 
}
