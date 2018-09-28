package uav.generic.hardware.aircraft;

import uav.generic.hardware.sensors.Attitude;
import uav.generic.hardware.sensors.Barometer;
import uav.generic.hardware.sensors.Battery;
import uav.generic.hardware.sensors.GPS;
import uav.generic.hardware.sensors.GPSInfo;
import uav.generic.hardware.sensors.SensorUAV;
import uav.generic.hardware.sensors.Sonar;
import uav.generic.hardware.sensors.StatusUAV;
import uav.generic.hardware.sensors.Temperature;
import uav.generic.hardware.sensors.Velocity;

/**
 *
 * @author Jesimar S. Arantes.
 */
public class DroneSensors {
    
    Battery battery; 
    GPS gps;
    Barometer barometer;       
    Attitude attitude;
    Velocity velocity;
    GPSInfo gpsinfo;
    SensorUAV sensorUAV;
    StatusUAV statusUAV;    
    Sonar sonar;
    Temperature temperature;

    public DroneSensors() {
        battery = new Battery();
        gps = new GPS();        
        barometer = new Barometer();  
        attitude = new Attitude();
        velocity = new Velocity();
        gpsinfo = new GPSInfo();
        sensorUAV = new SensorUAV();
        statusUAV = new StatusUAV();
        sonar = new Sonar();
        temperature = new Temperature();
    }
    
    public Battery getBattery() {
        return battery;
    }
    
    public GPS getGPS() {
        return gps;
    }
    
    public Barometer getBarometer() {
        return barometer;
    }
    
    public Attitude getAttitude(){
        return attitude;
    }
    
    public Velocity getVelocity(){
        return velocity;
    } 
    
    public GPSInfo getGPSInfo() {
        return gpsinfo;
    }

    public SensorUAV getSensorUAV() {
        return sensorUAV;
    }

    public StatusUAV getStatusUAV(){
        return statusUAV;
    }
    
    public Sonar getSonar(){
        return sonar;
    }
    
    public Temperature getTemperature(){
        return temperature;
    }
    
    public void setInfoGPS(double lat, double lng) {
        this.gps.lat = lat;
        this.gps.lng = lng;
    }
    
    public void setInfoBarometer(double alt_rel, double alt_abs) {
        this.barometer.alt_rel = alt_rel;
        this.barometer.alt_abs = alt_abs;
    }
        
    public void setInfoBattery(double voltage, double current, double level) {
        this.battery.voltage = voltage;
        this.battery.current = current;
        this.battery.level = level;
    }           
    
    public void setInfoAttitude(double pitch, double yaw, double roll) {
        this.attitude.pitch = pitch;
        this.attitude.yaw = yaw;
        this.attitude.roll = roll;
    } 
    
    public void setInfoGPSInfo(int fix, int num_sat) {
        this.gpsinfo.fixType = fix;
        this.gpsinfo.satellitesVisible = num_sat;
    }
    
}
