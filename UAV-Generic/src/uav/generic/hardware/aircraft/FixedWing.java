package uav.generic.hardware.aircraft;

import uav.generic.struct.HomeLocation;
import uav.generic.struct.ListParameters;
import uav.generic.hardware.sensors.Attitude;
import uav.generic.hardware.sensors.Barometer;
import uav.generic.hardware.sensors.Battery;
import uav.generic.hardware.sensors.GPS;
import uav.generic.hardware.sensors.GPSInfo;
import uav.generic.hardware.sensors.SensorUAV;
import uav.generic.hardware.sensors.StatusUAV;
import uav.generic.hardware.sensors.Velocity;

/**
 * Classe que implementa uma aeronave de asa fixa.
 * @author Jesimar S. Arantes
 */
public class FixedWing extends Drone{     
    
    /**
     * Class constructor.
     * @param nameAircraft aircraft name
     */
    public FixedWing(String nameAircraft){
        this.nameAircraft = nameAircraft;
        
        speedCruize = 20.0;//in m/s
        speedMax = 30.0;//in m/s
        mass = 2.828;//in kg
        payload = 0.600;//in kg
        endurance = 900.0;//in seconds
        
        time = 0.0;//in seconds
        nextWaypoint = 0;
        countWaypoint = 0;
        distanceToHome = 0.0;//in meters
        distanceToCurrentWaypoint = 0.0;//in meters
        
        typeFailure = "NONE";
        
        homeLocation = new HomeLocation();
        listParameters = new ListParameters();
        
        battery = new Battery();
        gps = new GPS(); 
        barometer = new Barometer();
        attitude = new Attitude();
        velocity = new Velocity();
        gpsinfo = new GPSInfo();
        sensorUAV = new SensorUAV();
        statusUAV = new StatusUAV();
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
    public FixedWing(String nameAircraft, double speedCruize, double speedMax,
            double mass, double payload, double endurance){
        this.nameAircraft = nameAircraft;
        this.speedCruize = speedCruize;//in m/s
        this.speedMax = speedMax;//in m/s
        this.mass = mass;//in kg
        this.payload = payload;//in kg
        this.endurance = endurance;//in seconds
        
        time = 0.0;//in seconds
        nextWaypoint = 0;
        countWaypoint = 0;
        distanceToHome = 0.0;//in meters
        distanceToCurrentWaypoint = 0.0;//in meters
        
        typeFailure = "NONE";
        
        homeLocation = new HomeLocation();
        listParameters = new ListParameters();
        
        battery = new Battery();
        gps = new GPS();        
        barometer = new Barometer();  
        attitude = new Attitude();
        velocity = new Velocity();
        gpsinfo = new GPSInfo();
        sensorUAV = new SensorUAV();
        statusUAV = new StatusUAV();
    }
}
