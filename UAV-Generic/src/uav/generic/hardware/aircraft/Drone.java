package uav.generic.hardware.aircraft;

import java.text.SimpleDateFormat;
import java.util.Date;
import uav.generic.struct.HomeLocation;
import uav.generic.struct.ListParameters;
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
 * Classe que modela uma aeronave.
 * @author Jesimar S. Arantes
 */
public abstract class Drone {
    
    String nameAircraft;

    double speedCruize;//in m/s
    double speedMax;//in m/s
    double mass;//in kg
    double payload;//in kg
    double endurance;//in seconds
    
    double time;//in seconds
    int nextWaypoint;
    int countWaypoint;
    double distanceToHome;//in meters
    double distanceToCurrentWaypoint;//in meters
    
    String typeFailure;
    
    HomeLocation homeLocation;
    ListParameters listParameters;
    
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
    
    public double getTime() {
        return time;
    }
    
    public int getNextWaypoint() {
        return nextWaypoint;
    }
    
    public int getCountWaypoint() {
        return countWaypoint;
    }

    public double getDistanceToHome() {
        return distanceToHome;
    }
    
    public double getDistanceToCurrentWaypoint() {
        return distanceToCurrentWaypoint;
    }
    
    public void setTime(double time){
        this.time = time;
    }
    
    public void setNextWaypoint(String next) {
        this.nextWaypoint = Integer.parseInt(next);
    } 
    
    public void setCountWaypoint(String count) {
        this.countWaypoint = Integer.parseInt(count);
    }
    
    public void setDistanceToHome(String dist) {
        try{
            this.distanceToHome = Double.parseDouble(dist);
        }catch (NumberFormatException ex){
            this.distanceToHome = -1;
        }
    }
    
    public void setDistanceToCurrentWaypoint(String dist) {
        try{
            this.distanceToCurrentWaypoint = Double.parseDouble(dist);
        }catch (NumberFormatException ex){
            this.distanceToCurrentWaypoint = -1;
        }
    }
    
    /**
     * Converts line in JSON format to NextWaypoint values.
     * @param line FORMAT: {"next-waypoint": 5}
     */
    public void parserNextWaypoint(String line) {
        line = line.substring(18, line.length() - 1);
        this.nextWaypoint = Integer.parseInt(line);
    }
    
    /**
     * Converts line in JSON format to CountWaypoint values.
     * @param line FORMAT: {"count-waypoint": 28}
     */
    public void parserCountWaypoint(String line) {
        line = line.substring(19, line.length() - 1);
        this.countWaypoint = Integer.parseInt(line);
    }
    
    /**
     * Converts line in JSON format to DistanceToCurrentWaypoint values.
     * @param line FORMAT: {"distance-to-wpt-current": 4.32}
     */
    public void parserDistanceToCurrentWaypoint(String line) {
        line = line.substring(28, line.length() - 1);
        this.distanceToCurrentWaypoint = Double.parseDouble(line);
    }

    /**
     * Converts line in JSON format to DistanceToHome values.
     * @param line FORMAT: {"distance-to-home": 20.50}
     */
    public void parserDistanceToHome(String line) {
        line = line.substring(21, line.length() - 1);
        this.distanceToHome = Double.parseDouble(line);
    }

    public void setTypeFailure(String typeFailure) {
        this.typeFailure = typeFailure;
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
    
    public Barometer getBarometer() {
        return barometer;
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
    
    public Sonar getSonar(){
        return sonar;
    }
    
    public Temperature getTemperature(){
        return temperature;
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
    
    public String title(){
        return "date;hour;time;lat;lng;alt-rel;alt-abs;voltage-bat;current-bat;"
                + "level-bat;pitch;yaw;roll;vel-x;vel-y;vel-z;fix-type;satellites-visible;"
                + "eph;epv;heading;groundspeed;airspeed;next-wpt;count-wpt;"
                + "dist-to-home;dist-to-current-wpt;mode;system-status;armed;"
                + "is-armable;ekf-ok;type-failure;dist-sonar;temperature-sensor";
    }
    
    @Override
    public String toString() {
        String dateHour = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss").format(new Date());
        String dist = sonar.distance == -1 ? "NONE" : String.format("%.2f", sonar.distance);
        String temp = temperature.temperature == -1 ? "NONE" : String.format("%.2f", temperature.temperature);
        return String.format("%s;%.1f;%.7f;%.7f;%.2f;%.2f;%.3f;%.2f;%.1f;%.4f;%.4f;%.4f;%.2f;" +
                "%.2f;%.2f;%d;%d;%d;%d;%.1f;%.2f;%.2f;%d;%d;%.2f;%.2f;%s;%s;%s;" +
                "%s;%s;%s;%s;%s", 
                dateHour, time, gps.lat, gps.lng, barometer.alt_rel, barometer.alt_abs,
                battery.voltage, battery.current, battery.level, attitude.pitch,
                attitude.yaw, attitude.roll, velocity.vx, velocity.vy, velocity.vz, 
                gpsinfo.fixType, gpsinfo.satellitesVisible, gpsinfo.eph, gpsinfo.epv, 
                sensorUAV.heading, sensorUAV.groundspeed, sensorUAV.airspeed, 
                nextWaypoint, countWaypoint, distanceToHome, distanceToCurrentWaypoint,
                statusUAV.mode, statusUAV.systemStatus, statusUAV.armed, 
                statusUAV.isArmable, statusUAV.ekfOk, typeFailure, dist, temp);
    }
}
