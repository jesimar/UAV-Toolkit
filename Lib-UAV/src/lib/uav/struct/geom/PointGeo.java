package lib.uav.struct.geom;

/**
 * Concrete class that implements a point using geographic coordinates.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class PointGeo extends Point{
    
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
        String v[] = point.split(",");        
        this.lng = Double.parseDouble(v[0]);
        this.lat = Double.parseDouble(v[1]);
        this.alt = Double.parseDouble(v[2]);
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
        return "PointGeo{" + "lng=" + lng + ", lat=" + lat + ", alt=" + alt + '}';
    }

    /** 
     * Calculates the distance between two coordinates.
     * Note: The return of the method is not in meters, but in degrees. 
     * That way, it should be converted to meters.
     * @param pointDestine - destination point
     * @return the distance (in degrees) between the current point and the argument.
     * @since version 2.0.0
     */
    @Override
    public double distance(Point pointDestine) {
        PointGeo p = (PointGeo) pointDestine;
        return Math.sqrt((p.lng - lng)*(p.lng - lng) + (p.lat - lat)*(p.lat - lat));
    }
        
}
