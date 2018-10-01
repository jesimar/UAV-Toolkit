package uav.mission_creator.struct.geom;

/**
 * Concrete class that implements a point using geographic coordinates.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class PointGeo {
    
    private final String name;
    private final double lng;
    private final double lat;
    private final double alt;
    
    /**
     * Class constructor.
     * @param lng coordinate longitude
     * @param lat coordinate latitude
     * @param alt coordinate altitude
     * @since version 2.0.0
     */
    public PointGeo(double lng, double lat, double alt) {
        this.name = "";
        this.lng = lng;
        this.lat = lat;
        this.alt = alt;
    }
    
    /**
     * Class constructor.
     * @param name name of the point
     * @param lng coordinate longitude
     * @param lat coordinate latitude
     * @param alt coordinate altitude
     * @since version 2.0.0
     */
    public PointGeo(String name, double lng, double lat, double alt) {
        this.name = name;
        this.lng = lng;
        this.lat = lat;
        this.alt = alt;
    }
    
    /**
     * Class constructor.
     * @param point point with coordinates longitude, latitude, altitude (lng,lat,alt).
     * @since version 2.0.0
     */
    public PointGeo(String point) {
        this.name = "";
        String v[] = point.split(",");        
        this.lng = Double.parseDouble(v[0]);
        this.lat = Double.parseDouble(v[1]);
        this.alt = Double.parseDouble(v[2]);
    }
    
    /**
     * Gets the name
     * @return the name
     * @since version 2.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value longitude
     * @return the longitude
     * @since version 2.0.0
     */
    public double getLng() {
        return lng;
    }

    /**
     * Gets the value latitude
     * @return the latitude
     * @since version 2.0.0
     */
    public double getLat() {
        return lat;
    }

    /**
     * Gets the value altitude
     * @return the altitude
     * @since version 2.0.0
     */
    public double getAlt() {
        return alt;
    }   
    
    /**
     * Gets a string with the longitude, latitude and altitude
     * @return the string with the longitude, latitude and altitude
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return String.format("%.16g %.16g %.16g\n", lng, lat, alt);
    }
    
    /**
     * Gets a string with the longitude, latitude and altitude
     * @return the string with the longitude; latitude; altitude
     * @since version 2.0.0
     */
    public String toString2() {
        return String.format("%.16g;%.16g;%.16g\n", lat, lng, alt);
    }
    
    /**
     * Gets a string with the name longitude, latitude and altitude
     * @return the string with the name longitude latitude altitude
     * @since version 2.0.0
     */
    public String toString3() {
        return String.format("%s %.16g %.16g %.16g\n", name, lng, lat, alt);
    }
}
