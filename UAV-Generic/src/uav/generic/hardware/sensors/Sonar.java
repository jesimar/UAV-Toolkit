package uav.generic.hardware.sensors;

/**
 * The class models sonar sensor
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public class Sonar {
        
    public double distance = -1.0;//in meters

    /**
     * Class constructor.
     * @since version 3.0.0
     */
    public Sonar() {
        
    }  

    /**
     * Class constructor.
     * @param distance in meters
     * @since version 3.0.0
     */
    public Sonar(double distance) {
        this.distance = distance;
    } 

    /**
     * Set the distance
     * @param distance the distance value in meters
     * @since version 3.0.0
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    /**
     * Set the distance
     * @param distance the distance value in meters
     * @since version 3.0.0
     */
    public void setDistance(String distance) {
        try{
            this.distance = Double.parseDouble(distance);
        }catch (NumberFormatException ex){
            this.distance = -1;
        }
    }
    
    /**
     * Get if has a error in temperature sensor.
     * @return {@code true} if has error in temperature 
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean hasErrorInDistance(){
        if (distance == -1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Gets a string with value: "Sonar{" + "distance=" + distance + '}'.
     * @return a string with value: "Sonar{" + "distance=" + distance + '}'
     * @since version 3.0.0
     */
    @Override
    public String toString() {
        return "Sonar{" + "distance=" + distance + '}';
    }
    
}
