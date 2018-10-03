package lib.uav.struct;

import lib.color.StandardPrints;

/**
 * The class models the location of the drone's home.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class HomeLocation {
    
    private double lat;
    private double lng;
    private double alt;

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public HomeLocation() {
        
    }        

    /**
     * Class constructor.
     * @param lat coordinate latitude.
     * @param lng coordinate longitude.
     * @param alt coordinate altitude.
     * @since version 2.0.0
     */
    public HomeLocation(double lat, double lng, double alt) {
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    /**
     * Parser the info of home location of the drone
     * @param line the line containing the coordinate latitude, longitude and altitude.
     * @since version 2.0.0
     */
    public void parserInfoHomeLocation(String line) {
        try{
            line = line.substring(19, line.length() - 2);
            String v[] = line.split(",");
            this.lat = Double.parseDouble(v[0]);        
            this.lng = Double.parseDouble(v[1]);
            this.alt = Double.parseDouble(v[2]);            
        }catch (NumberFormatException ex){
            
        }catch (StringIndexOutOfBoundsException ex){
            StandardPrints.printMsgError("Error [StringIndexOutOfBoundsException]: parserInfoHomeLocation()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Upadate/set the home location of the drone
     * @param lat coordinate latitude.
     * @param lng coordinate longitude.
     * @param alt coordinate altitude.
     * @since version 2.0.0
     */
    public void updateHomeLocation(String lat, String lng, String alt){
        try{
            this.lat = Double.parseDouble(lat);        
            this.lng = Double.parseDouble(lng);
            this.alt = Double.parseDouble(alt);
        }catch (NumberFormatException ex){
            
        }
    }
    
    /**
     * Sets the home location of the drone
     * @param lat coordinate latitude.
     * @param lng coordinate longitude.
     * @param alt coordinate altitude.
     * @since version 2.0.0
     */
    public void setHomeLocation(double lat, double lng, double alt) {
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }

    /**
     * Gets the latitude
     * @return the latitude
     * @since version 2.0.0
     */
    public double getLat() {
        return lat;
    }

    /**
     * Gets the longitude
     * @return the longitude
     * @since version 2.0.0
     */
    public double getLng() {
        return lng;
    }
    
    /**
     * Gets the altitude
     * @return the altitude
     * @since version 2.0.0
     */
    public double getAlt() {
        return alt;
    }
    
    /**
     * Gets a string with value: lat + ", " + lng + ", " + alt
     * @return the string with: lat + ", " + lng + ", " + alt
     * @since version 2.0.0
     */
    public String string() {
        return lat + ", " + lng + ", " + alt;
    }

    /**
     * Gets a string with value: "HomeLocation{" + "lat=" + lat + ", lng=" + lng + ", alt=" + alt + '}'
     * @return the string with: "HomeLocation{" + "lat=" + lat + ", lng=" + lng + ", alt=" + alt + '}'
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return "HomeLocation{" + "lat=" + lat + ", lng=" + lng + ", alt=" + alt + '}';
    }        
    
}
