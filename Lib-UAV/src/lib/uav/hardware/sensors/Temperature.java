package lib.uav.hardware.sensors;

/**
 * The class models the temperature sensor.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public class Temperature {
        
    public double temperature;//in degrees celsius

    /**
     * Class constructor.
     * @since version 3.0.0
     */
    public Temperature() {
        this.temperature = -1.0; //this value represent a error.
    }  

    /**
     * Class constructor.
     * @param temperature in degrees celsius
     * @since version 3.0.0
     */
    public Temperature(double temperature) {
        this.temperature = temperature;
    } 

    /**
     * Set the temperature
     * @param temperature the temperature value in celsius degree
     * @since version 3.0.0
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    /**
     * Set the temperature
     * @param temperature the temperature value in celsius degree
     * @since version 3.0.0
     */
    public void setTemperature(String temperature) {
        try{
            this.temperature = Double.parseDouble(temperature);
        }catch (NumberFormatException ex){
            this.temperature = -1;
        }
    }
    
    /**
     * Get if has a error in temperature sensor.
     * @return {@code true} if has error in temperature 
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean hasErrorInTemperature(){
        if (temperature == -1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Gets a string with value: "Temperature{" + "temperature=" + temperature + '}'.
     * @return a string with value: "Temperature{" + "temperature=" + temperature + '}'
     * @since version 3.0.0
     */
    @Override
    public String toString() {
        return "Temperature{" + "temperature=" + temperature + '}';
    }
    
}
