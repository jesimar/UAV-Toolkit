package uav.generic.hardware.sensors;

/**
 * Classe que modela o sonar do drone.
 * @author Jesimar S. Arantes
 */
public class Sonar {
        
    public double distance = -1.0;//in meters

    /**
     * Class constructor.
     */
    public Sonar() {
        
    }  

    /**
     * Class constructor.
     * @param distance in meters
     */
    public Sonar(double distance) {
        this.distance = distance;
    } 

    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public void setDistance(String distance) {
        this.distance = Double.parseDouble(distance);
    }

    @Override
    public String toString() {
        return "Sonar{" + "distance=" + distance + '}';
    }
    
}
