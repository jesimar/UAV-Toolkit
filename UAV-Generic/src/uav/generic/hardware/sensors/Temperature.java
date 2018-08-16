package uav.generic.hardware.sensors;

/**
 * Classe que modela o sensor de temperatura do drone.
 * @author Jesimar S. Arantes
 */
public class Temperature {
        
    public double temperature = -1.0;//in degrees celsius

    /**
     * Class constructor.
     */
    public Temperature() {
        
    }  

    /**
     * Class constructor.
     * @param temperature in degrees celsius
     */
    public Temperature(double temperature) {
        this.temperature = temperature;
    } 

    public void setDistance(double temperature) {
        this.temperature = temperature;
    }
    
    public void setDistance(String temperature) {
        this.temperature = Double.parseDouble(temperature);
    }

    @Override
    public String toString() {
        return "Temperature{" + "temperature=" + temperature + '}';
    }
    
}
