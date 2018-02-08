package uav.hardware.aircraft;

import uav.hardware.data.HomeLocation;
import uav.hardware.data.ListParameters;
import uav.hardware.sensors.Attitude;
import uav.hardware.sensors.Battery;
import uav.hardware.sensors.GPS;
import uav.hardware.sensors.GPSInfo;
import uav.hardware.sensors.SensorUAV;
import uav.hardware.sensors.StatusUAV;
import uav.hardware.sensors.Velocity;

/**
 *
 * @author jesimar
 */
public abstract class Drone {

    double SPEED_MAX;//m/s
    double SPEED_CRUIZE;//m/s
    double MASS;//kg
    double PAYLOAD;//kg
    double ENDURANCE;//segundos
    double time;//segundos
    double distanceToHome;//in meters
    HomeLocation homeLocation;
    ListParameters listParameters;
    GPS gps;
    Battery battery;    
    Attitude attitude;
    Velocity velocity;
    GPSInfo gpsinfo;
    SensorUAV sensorUAV;
    StatusUAV statusUAV;
        
    public double getSpeedCruize() {
        return SPEED_CRUIZE;
    }
    
    public double getSpeedMax() {
        return SPEED_MAX;
    }    

    public double getMass() {
        return MASS;
    }

    public double getPayload() {
        return PAYLOAD;
    }
    
    public double getEndurance(){
        return ENDURANCE;
    }
    
    public void incrementTime(double i){
        time += i/1000.0;
    }
    
    public double getTime() {
        return time;
    }

    public double getDistanceToHome() {
        return distanceToHome;
    }

    public void setDistanceToHome(String distanceToHome) {
        distanceToHome = distanceToHome.substring(21, distanceToHome.length() - 1);
        this.distanceToHome = Double.parseDouble(distanceToHome);
    }        

    public HomeLocation getHomeLocation() {
        return homeLocation;
    }  
    
    public ListParameters getListParameters() {
        return listParameters;
    }

    public Battery getBattery() {
        return battery;
    }
    
    public GPS getGPS() {
        return gps;
    }
    
    public Attitude getAttitude(){
        return attitude;
    }
    
    public Velocity getVelocity(){
        return velocity;
    } 

    public SensorUAV getSensorUAV() {
        return sensorUAV;
    }

    public GPSInfo getGPSInfo() {
        return gpsinfo;
    }
    
    public StatusUAV getStatusUAV(){
        return statusUAV;
    }
    
    public void defineHomeLocation(double lat, double lng, double alt) {
        homeLocation.setHomeLocation(lat, lng, alt);
    }
    
    public void defineHomeLocation(String home) {
        homeLocation.parserInfoHomeLocation(home);
    }
    
    public void defineListParameters(String parameters) {
        listParameters.parseInfoParameters(parameters);
    }
    
    public void setInfoGPS(double lat, double lng, double alt) {
        this.gps.lat = lat;
        this.gps.lng = lng;
        this.gps.alt_rel = alt;
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
    
    public String title(){
        return "time;lat;lng;alt_rel;alt_abs;voltage_bat;current_bat;level_bat;pitch;yaw;roll;vx;vy;vz;"
                + "fixtype;satellitesvisible;eph;epv;heading;groundspeed;airspeed;mode;"
                + "system-status;armed;is-armable;ekf-ok";
    }     
        
    @Override
    public String toString() {
        return time + ";" + gps.lat + ";" + gps.lng + ";" + gps.alt_rel + ";" + 
               gps.alt_abs + ";" + battery.voltage + ";" + battery.current + ";" + battery.level +  ";" + 
               attitude.pitch + ";" + attitude.yaw + ";" + attitude.roll + ";" +
               velocity.vx + ";" + velocity.vy + ";" + velocity.vz + ";" +
               gpsinfo.fixType + ";" + gpsinfo.satellitesVisible + ";" + 
               gpsinfo.eph + ";" + gpsinfo.epv + ";" +
               sensorUAV.heading + ";" + sensorUAV.groundspeed + ";" + 
               sensorUAV.airspeed + ";" + statusUAV.mode + ";" + 
               statusUAV.systemStatus + ";" + statusUAV.armed + ";" + 
               statusUAV.isArmable + ";" + statusUAV.ekfOk;
    }
}
