package uav.generic.struct;

import uav.generic.struct.geom.PointGeo;

/**
 * The class models a waypoint.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Waypoint {
    
    private final String action;
    private final double lat;//is equivalent to y coordinate.
    private final double lng;//is equivalent to x coordinate.
    private final double alt;//is equivalent to z coordinate.
    
    /**
     * Class constructor.
     * @param action command of action (TAKEOFF, GOTO, LAND, LAND_VERTICAL, RTL)
     * @param lat latitude of waypoint in degrees.
     * @param lng longitude of waypoint in degrees.
     * @param alt altitude of waypoint in meters.
     * @since version 2.0.0
     */
    public Waypoint(String action, double lat, double lng, double alt){
        this.action = action;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    /**
     * Class constructor.
     * @param lat latitude of waypoint in degrees.
     * @param lng longitude of waypoint in degrees.
     * @param alt altitude of waypoint in meters.
     * @since version 2.0.0
     */
    public Waypoint(double lat, double lng, double alt){
        this.action = "";
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    /**
     * Class constructor.
     * @param pGeo point in geographical coordinates.
     * @since version 2.0.0
     */
    public Waypoint(PointGeo pGeo){
        this.action = "";
        this.lat = pGeo.getLat();
        this.lng = pGeo.getLng();
        this.alt = pGeo.getAlt();
    }

    /**
     * Gets a type of action.
     * @return the type of action
     * @since version 2.0.0
     */
    public String getAction() {
        return action;
    }

    /**
     * Gets a latitude.
     * @return the latitude
     * @since version 2.0.0
     */
    public double getLat() {
        return lat;
    }

    /**
     * Gets a longitude.
     * @return the longitude
     * @since version 2.0.0
     */
    public double getLng() {
        return lng;
    }

    /**
     * Gets a altitude.
     * @return the altitude
     * @since version 2.0.0
     */
    public double getAlt() {
        return alt;
    }
    
    /**
     * Gets a string with values: action + ", " + lat + ", " + lng + ", " + alt.
     * @return the string with values: action + ", " + lat + ", " + lng + ", " + alt
     * @since version 2.0.0
     */
    public String string(){
        return action + ", " + lat + ", " + lng + ", " + alt;
    }
    
    /**
     * Gets a string with values: lat + ", " + lng + ", " + alt.
     * @return the string with values: lat + ", " + lng + ", " + alt
     * @since version 2.0.0
     */
    public String string2(){
        return lat + ", " + lng + ", " + alt;
    }

    /**
     * Gets a string with values: "Waypoint: [" + action + ", " + lat + ", " + lng + ", " + alt + "]".
     * @return the string with values: "Waypoint: [" + action + ", " + lat + ", " + lng + ", " + alt + "]"
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return "Waypoint: [" + action + ", " + lat + ", " + lng + ", " + alt + "]";
    } 
    
    /**
     * Gets a string with values: "Waypoint: [" + lat + ", " + lng + ", " + alt + "]".
     * @return the string with values: "Waypoint: [" + lat + ", " + lng + ", " + alt + "]"
     * @since version 2.0.0
     */
    public String toString2() {
        return "Waypoint: [" + lat + ", " + lng + ", " + alt + "]";
    } 
}
