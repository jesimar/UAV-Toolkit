package uav.mission_creator.struct.geom;

/**
 *
 * @author Jesimar S. Arantes
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
        return String.format("%.16g %.16g %.16g\n", lng, lat, alt);
    }
    
    public String toString2() {
        return String.format("%.16g;%.16g;%.16g\n", lat, lng, alt);
    }
    
    public String toString3() {
        return String.format("%s %.16g %.16g %.16g\n", name, lng, lat, alt);
    }
}
