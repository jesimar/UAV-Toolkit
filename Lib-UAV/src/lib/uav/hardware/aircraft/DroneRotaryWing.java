package lib.uav.hardware.aircraft;

/**
 * The class implements a rotary-wing aircraft.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 * @see Drone
 */
public class DroneRotaryWing extends Drone{
    
    /**
     * Class constructor.
     * @param nameAircraft aircraft name
     * @since version 4.0.0
     */
    public DroneRotaryWing(String nameAircraft){
        attributes = new DroneAttributes(nameAircraft, 2.0, 10.0, 1.100, 0.400, 420);
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
    public DroneRotaryWing(String nameAircraft, double speedCruize, double speedMax,
            double mass, double payload, double endurance){
        attributes = new DroneAttributes(nameAircraft, speedCruize, speedMax, 
                mass, payload, endurance);
        sensors = new DroneSensors();
        info = new DroneInfo();       
    }
}
