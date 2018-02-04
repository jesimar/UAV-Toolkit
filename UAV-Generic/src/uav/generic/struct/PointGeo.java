package uav.generic.struct;

/**
 *
 * @author jesimar
 */
public class PointGeo {
    
    private final String name;
    private final double lng;
    private final double lat;
    private final double alt;
    
    public PointGeo(double lng, double lat, double alt) {
        this.name = "";
        this.lng = lng;
        this.lat = lat;
        this.alt = alt;
    }
    
    public PointGeo(String name, double lng, double lat, double alt) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.alt = alt;
    }
    
    public PointGeo(String point) {
        this.name = "";
        String v[] = point.split(",");        
        this.lng = Double.parseDouble(v[0]);
        this.lat = Double.parseDouble(v[1]);
        this.alt = Double.parseDouble(v[2]);
    }
    
    public String getName() {
        return name;
    }

    public double getLongitude() {
        return lng;
    }

    public double getLatitude() {
        return lat;
    }

    public double getAltitude() {
        return alt;
    }

    @Override
    public String toString() {
        return "PointGeo{" + "name=" + name + ", lng=" + lng + ", lat=" + lat + ", alt=" + alt + '}';
    }
    
    public String toString2() {
        return "PointGeo{" + "lng=" + lng + ", lat=" + lat + ", alt=" + alt + '}';
    }
        
}
