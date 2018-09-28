package uav.generic.hardware.aircraft;

/**
 * Classe que implementa uma aeronave de asa rotativa.
 * @author Jesimar S. Arantes
 */
public class DroneRotaryWing extends Drone{
    
    /**
     * Class constructor.
     * @param nameAircraft aircraft name
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
     */
    public DroneRotaryWing(String nameAircraft, double speedCruize, double speedMax,
            double mass, double payload, double endurance){
        attributes = new DroneAttributes(nameAircraft, speedCruize, speedMax, 
                mass, payload, endurance);
        sensors = new DroneSensors();
        info = new DroneInfo();       
    }
}
