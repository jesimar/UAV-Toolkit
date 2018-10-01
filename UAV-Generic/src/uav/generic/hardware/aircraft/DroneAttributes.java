package uav.generic.hardware.aircraft;

/**
 * The class synthesizes a set of drone attributes.
 * @author Jesimar S. Arantes.
 * @since version 4.0.0
 */
public class DroneAttributes {
    
    private String nameAircraft;
    private double speedCruize; //in m/s
    private double speedMax;    //in m/s
    private double mass;        //in kg
    private double payload;     //in kg
    private double endurance;   //in seconds

    /**
     * Class constructor.
     * @since 4.0.0
     */
    public DroneAttributes() {
    }

    /**
     * Class constructor.
     * @param nameAircraft the name of aircraft
     * @param speedCruize the speed cruize
     * @param speedMax the maximum speed of aircraft
     * @param mass the mass of aircraft
     * @param payload the mass of payload
     * @param endurance the endurance of vehicle
     * @since 4.0.0
     */
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
