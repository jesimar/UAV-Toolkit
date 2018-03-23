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
 *
 * @author Jesimar S. Arantes
 */
public class FixedWing extends Drone{     
    
    public FixedWing(String nameAircraft){
        this.nameAircraft = nameAircraft;
        
        SPEED_MAX = 30.0;//in m/s
        SPEED_CRUIZE = 20.0;//in m/s
        MASS = 2.828;//in kg
        PAYLOAD = 0.600;//in kg
        ENDURANCE = 900.0;//in seconds
        
        time = 0.0;//in seconds
        nextWaypoint = 0;
        countWaypoint = 0;
        distanceToHome = 0.0;//in meters
        distanceToCurrentWaypoint = 0.0;//in meters
        
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
