package lib.uav.hardware.sensors;

/**
 * The class models the drone barometer (relative altitude, absolute altitude).
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Barometer {
    
    public double alt_rel;  //in meters (in relation to launch level)
    public double alt_abs;  //in meters (in relation to sea level)

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public Barometer() {
        
    }

    /**
     * Class constructor.
     * @param alt_rel relative altitude 
     * @param alt_abs absolute altitude
     * @since version 2.0.0
     */
    public Barometer(double alt_rel, double alt_abs) {
        this.alt_rel = alt_rel;
        this.alt_abs = alt_abs;
    }
    
    /**
     * Converts line in JSON format to relative altitude and absolute altitude values.
     * @param line FORMAT: {"barometer": [0.0, 870.0]}
     * @since version 2.0.0
     */
    public void parserInfoBarometer(String line) {
        try{
            line = line.substring(15, line.length() - 2);
            String v[] = line.split(",");
            this.alt_rel = Double.parseDouble(v[0]);
            this.alt_abs = Double.parseDouble(v[1]);
        }catch (NumberFormatException ex){
            
        }
    }
    
    /**
     * Update the info the relative altitude and absolute altitude.
     * @param alt_rel relative altitude 
     * @param alt_abs absolute altitude
     * @since version 2.0.0
     */
    public void updateBarometer(String alt_rel, String alt_abs){
        try{
            this.alt_rel = Double.parseDouble(alt_rel);
            this.alt_abs = Double.parseDouble(alt_abs);
        }catch (NumberFormatException ex){
            
        }
    }

    /**
     * Set the relative altitude
     * @param alt_rel the relative altitude
     * @since version 2.0.0
     */
    public void setAltRel(double alt_rel) {
        this.alt_rel = alt_rel;
    }   
    
    /**
     * Set the absolute altitude
     * @param alt_abs the absolute altitude
     * @since version 2.0.0
     */
    public void setAltAbs(double alt_abs) {
        this.alt_abs = alt_abs;
    } 

    @Override
    public String toString() {
        return "Barometer{" + "alt_rel=" + alt_rel + ", alt_abs=" + alt_abs + '}';
    }
    
}
