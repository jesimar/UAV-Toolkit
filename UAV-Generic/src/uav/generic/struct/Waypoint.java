package uav.generic.struct;

/**
 *
 * @author jesimar
 */
public class Waypoint {
    
    private final String action;
    private final double lat;//equivale a coordenada y.
    private final double lng;//equivale a coordenada x.
    private final double alt;//equivale a coordenada z.
    
    public Waypoint(double lat, double lng, double alt){
        this.action = "";
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
            
    public Waypoint(String action, double lat, double lng, double alt){
        this.action = action;
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    public Waypoint(PointGeo pGeo){
        this.action = pGeo.getName();
        this.lat = pGeo.getLatitude();
        this.lng = pGeo.getLongitude();
        this.alt = pGeo.getAltitude();
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
    
    public String getString(){
        return action + ", " + lat + ", " + lng + ", " + alt;
    }
    
    public String getString2(){
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
