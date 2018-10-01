package uav.generic.hardware.sensors;

/**
 * The class models some drone sensors (heading, groundspeed, airspeed).
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class SensorUAV {
    
    public double heading;    //in degrees (0 ... 360)
    public double groundspeed;//in metres/second
    public double airspeed;   //in metres/second

    /**
     * Class constructor.
     * @since version 2.0.0
     */
    public SensorUAV() {
        
    }

    /**
     * Class constructor.
     * @param heading angle of aircraft (in degrees) (range 0 to 360)
     * @param groundspeed velocity in m/s
     * @param airspeed velocity in m/s
     * @since version 2.0.0
     */
    public SensorUAV(double heading, double groundspeed, double airspeed) {
        this.heading = heading;
        this.groundspeed = groundspeed;
        this.airspeed = airspeed;
    }
    
    /**
     * Converts line in JSON format to heading values.
     * @param line FORMAT: {"heading": 110}
     * @since version 2.0.0
     */
    public void parserInfoHeading(String line) {
        try{
            line = line.substring(12, line.length() - 1);        
            this.heading = Double.parseDouble(line);
        }catch (NumberFormatException ex){
            
        }
    }
    
    /**
     * Converts line in JSON format to groundspeed values.
     * @param line FORMAT: {"groundspeed": 2.21}
     * @since version 2.0.0
     */
    public void parserInfoGroundSpeed(String line) {
        try{
            line = line.substring(16, line.length() - 1);        
            this.groundspeed = Double.parseDouble(line);
        }catch (NumberFormatException ex){
            
        }
    }
    
    /**
     * Converts line in JSON format to airspeed values.
     * @param line FORMAT: {"airspeed": 1.53}
     * @since version 2.0.0
     */
    public void parserInfoAirSpeed(String line) {
        try{
            line = line.substring(13, line.length() - 1);        
            this.airspeed = Double.parseDouble(line);
        }catch (NumberFormatException ex){
            
        }
    }
    
    /**
     * Set the heading
     * @param heading the heading
     * @since version 2.0.0
     */
    public void setHeading(String heading) {
        try{
            this.heading = Double.parseDouble(heading);
        }catch (NumberFormatException ex){
            
        }        
    }

    /**
     * Set the heading
     * @param heading the heading
     * @since version 2.0.0
     */
    public void setHeading(double heading) {
        this.heading = heading;
    }
    
    /**
     * Set the groundspeed
     * @param groundspeed the groundspeed
     * @since version 2.0.0
     */
    public void setGroundspeed(String groundspeed) {
        try{
            this.groundspeed = Double.parseDouble(groundspeed);
        }catch (NumberFormatException ex){
            
        }
    }
    
    /**
     * Set the groundspeed
     * @param groundspeed the groundspeed
     * @since version 2.0.0
     */
    public void setGroundspeed(double groundspeed) {
        this.groundspeed = groundspeed;
    }
    
    /**
     * Set the airspeed
     * @param airspeed the airspeed
     * @since version 2.0.0
     */
    public void setAirspeed(String airspeed) {
        try{
            this.airspeed = Double.parseDouble(airspeed);
        }catch (NumberFormatException ex){
            
        }        
    } 

    /**
     * Set the airspeed
     * @param airspeed the airspeed
     * @since version 2.0.0
     */
    public void setAirspeed(double airspeed) {
        this.airspeed = airspeed;
    }        

    @Override
    public String toString() {
        return "SensorUAV{" + "heading=" + heading + ", groundspeed=" + groundspeed + ", airspeed=" + airspeed + '}';
    }        
        
}
