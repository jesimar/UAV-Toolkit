package uav.generic.struct.geom;

/**
 *
 * @author Jesimar S. Arantes
 */
public class PointGeo {
    
    private final double lng;
    private final double lat;
    private final double alt;
    
    public PointGeo(double lng, double lat, double alt) {
        this.lng = lng;
        this.lat = lat;
        this.alt = alt;
    }
    
    public PointGeo(String point) {
        String v[] = point.split(",");        
        this.lng = Double.parseDouble(v[0]);
        this.lat = Double.parseDouble(v[1]);
        this.alt = Double.parseDouble(v[2]);
    }

    public double getLng() {
        return lng;
    }

    public double getLat() {
        return lat;
    }

    public double getAlt() {
        return alt;
    }
    
    @Override
    public String toString() {
        return "PointGeo{" + "lng=" + lng + ", lat=" + lat + ", alt=" + alt + '}';
    }
        
}
