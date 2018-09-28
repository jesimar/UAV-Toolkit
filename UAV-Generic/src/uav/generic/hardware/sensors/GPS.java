package uav.generic.hardware.sensors;

/**
 * Classe que modela o GPS do drone (dados obtidos através do GPS).
 * @author jesimar
 */
public class GPS {
    
    public double lat;
    public double lng;

    /**
     * Class constructor.
     */
    public GPS() {
        
    }

    /**
     * Class constructor.
     * @param lat latitude coordinete [-90; +90] degrees
     * @param lng longitude coordinete [-180; +180] degres
     */
    public GPS(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    
    /**
     * Converts line in JSON format to latitude and longitude values.
     * @param line FORMAT: {"gps": [-22.0059726, -47.8986881]}
     */
    public void parserInfoGPS(String line) {
        try{
            line = line.substring(9, line.length() - 2);
            String v[] = line.split(",");
            this.lat = Double.parseDouble(v[0]);        
            this.lng = Double.parseDouble(v[1]);
        }catch (NumberFormatException ex){
            
        }
    }
    
    public void updateGPS(String lat, String lng){
        try{
            this.lat = Double.parseDouble(lat);        
            this.lng = Double.parseDouble(lng);
        }catch (NumberFormatException ex){
            
        }
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String string() {
        return lat + ", " + lng;
    }

    @Override
    public String toString() {
        return "GPS{" + "lat=" + lat + ", lng=" + lng + '}';
    }
    
}
