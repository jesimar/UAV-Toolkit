package uav.generic.struct;

/**
 *
 * @author Jesimar S. Arantes
 */
public class HomeLocation {
    
    private double lat;
    private double lng;
    private double alt;

    public HomeLocation() {
        
    }        

    public HomeLocation(double lat, double lng, double alt) {
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }
    
    public void parserInfoHomeLocation(String line) {
        try{
            line = line.substring(19, line.length() - 2);
            String v[] = line.split(",");
            this.lat = Double.parseDouble(v[0]);        
            this.lng = Double.parseDouble(v[1]);
            this.alt = Double.parseDouble(v[2]);            
        }catch (NumberFormatException ex){
            
        }
    }
    
    public void updateHomeLocation(String lat, String lng, String alt){
        try{
            this.lat = Double.parseDouble(lat);        
            this.lng = Double.parseDouble(lng);
            this.alt = Double.parseDouble(alt);
        }catch (NumberFormatException ex){
            
        }
    }
    
    public void setHomeLocation(double lat, double lng, double alt) {
        this.lat = lat;
        this.lng = lng;
        this.alt = alt;
    }

    public double getAlt() {
        return alt;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
    
    public String string() {
        return lat + ", " + lng + ", " + alt;
    }

    @Override
    public String toString() {
        return "HomeLocation{" + "lat=" + lat + ", lng=" + lng + ", alt=" + alt + '}';
    }        
    
}
