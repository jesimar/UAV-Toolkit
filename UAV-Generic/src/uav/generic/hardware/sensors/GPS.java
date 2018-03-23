package uav.generic.hardware.sensors;

/**
 *
 * @author jesimar
 */
public class GPS {
    
    public double lat;
    public double lng;

    public GPS() {
    }

    public GPS(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
    
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

    @Override
    public String toString() {
        return "GPS{" + "lat=" + lat + ", lng=" + lng + '}';
    }
    
}
