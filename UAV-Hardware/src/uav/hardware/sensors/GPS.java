package uav.hardware.sensors;

/**
 *
 * @author jesimar
 */
public class GPS {
    
    public double lat;
    public double lng;
    public double alt_rel;  //in meters (em relacao ao nivel do lancamento)
    public double alt_abs;  //in meters (em relacao ao nivel do mar)

    public GPS() {
    }

    public GPS(double lat, double lng, double alt_rel, double alt_abs) {
        this.lat = lat;
        this.lng = lng;
        this.alt_rel = alt_rel;
        this.alt_abs = alt_abs;
    }
    
    public void parserInfoGPS(String line) {
        try{
            line = line.substring(9, line.length() - 2);
            String v[] = line.split(",");
            this.lat = Double.parseDouble(v[0]);        
            this.lng = Double.parseDouble(v[1]);
            this.alt_rel = Double.parseDouble(v[2]);
            this.alt_abs = Double.parseDouble(v[3]);
        }catch (NumberFormatException ex){
            
        }
    }
    
    public void updateGPS(String lat, String lng, String alt_rel, String alt_abs){
        try{
            this.lat = Double.parseDouble(lat);        
            this.lng = Double.parseDouble(lng);
            this.alt_rel = Double.parseDouble(alt_rel);
            this.alt_abs = Double.parseDouble(alt_abs);
        }catch (NumberFormatException ex){
            
        }
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setAltRel(double alt_rel) {
        this.alt_rel = alt_rel;
    }   
    
    public void setAltAbs(double alt_abs) {
        this.alt_abs = alt_abs;
    }   

    @Override
    public String toString() {
        return "GPS{" + "lat=" + lat + ", lng=" + lng + ", alt_rel=" + alt_rel + ", alt_abs=" + alt_abs + '}';
    }   
        
}
