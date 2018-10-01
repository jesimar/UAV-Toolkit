package uav.generic.hardware.aircraft;

/**
 * The class implements a fixed-wing aircraft.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 * @see Drone
 */
public class DroneFixedWing extends Drone{     
    
    /**
     * Class constructor.
     * @param nameAircraft aircraft name
     * @since version 4.0.0
     */
    public DroneFixedWing(String nameAircraft){
        attributes = new DroneAttributes(nameAircraft, 20.0, 30.0, 2.828, 0.600, 900);
        sensors = new DroneSensors();
        info = new DroneInfo();
    }  
    
    /**
     * Class constructor.
     * @param nameAircraft aircraft name
     * @param speedCruize cruize speed
     * @param speedMax maximum speed
     * @param mass drone weight
     * @param payload maximum weight carried by drone 
     * @param endurance flight time
     * @since version 4.0.0
     */
    public DroneFixedWing(String nameAircraft, double speedCruize, double speedMax,
            double mass, double payload, double endurance){
        attributes = new DroneAttributes(nameAircraft, speedCruize, speedMax, 
                mass, payload, endurance);
        sensors = new DroneSensors();
        info = new DroneInfo();
    }
}
