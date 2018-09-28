package uav.generic.hardware.aircraft;

/**
 *
 * @author Jesimar S. Arantes.
 */
public class DroneAttributes {
    
    private String nameAircraft;
    private double speedCruize; //in m/s
    private double speedMax;    //in m/s
    private double mass;        //in kg
    private double payload;     //in kg
    private double endurance;   //in seconds

    public DroneAttributes() {
    }

    public DroneAttributes(String nameAircraft, double speedCruize, 
            double speedMax, double mass, double payload, double endurance) {
        this.nameAircraft = nameAircraft;
        this.speedCruize = speedCruize;
        this.speedMax = speedMax;
        this.mass = mass;
        this.payload = payload;
        this.endurance = endurance;
    }
    
    public String getNameAircraft() {
        return nameAircraft;
    }
    
    public double getSpeedCruize() {
        return speedCruize;
    }
    
    public double getSpeedMax() {
        return speedMax;
    }    

    public double getMass() {
        return mass;
    }

    public double getPayload() {
        return payload;
    }
    
    public double getEndurance(){
        return endurance;
    }
    
}
