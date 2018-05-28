package uav.gcs.struct;

import uav.generic.hardware.sensors.Attitude;
import uav.generic.hardware.sensors.Barometer;
import uav.generic.hardware.sensors.Battery;
import uav.generic.hardware.sensors.GPS;
import uav.generic.hardware.sensors.GPSInfo;
import uav.generic.hardware.sensors.SensorUAV;
import uav.generic.hardware.sensors.StatusUAV;
import uav.generic.hardware.sensors.Velocity;

/**
 * Classe que modela uma aeronave.
 * @author Jesimar S. Arantes
 */
public class Drone {
    
    public String userEmail;
    public String date;//yyyy/MM/dd
    public String hour;//HH:mm:ss
    public double time;//in seconds
    public int nextWaypoint;
    public int countWaypoint;
    public double distanceToHome;//in meters
    public double distanceToCurrentWaypoint;//in meters    
    public String typeFailure;
    public Battery battery; 
    public GPS gps;
    public Barometer barometer;       
    public Attitude attitude;
    public Velocity velocity;
    public GPSInfo gpsinfo;
    public SensorUAV sensorUAV;
    public StatusUAV statusUAV;

    public Drone(String userEmail) {
        this.userEmail = userEmail;
        battery = new Battery();
        gps = new GPS(); 
        barometer = new Barometer();
        attitude = new Attitude();
        velocity = new Velocity();
        gpsinfo = new GPSInfo();
        sensorUAV = new SensorUAV();
        statusUAV = new StatusUAV();
    }    

    public String title(){
        return "date;hour;time;lat;lng;alt_rel;alt_abs;voltage_bat;current_bat;"
                + "level_bat;pitch;yaw;roll;vx;vy;vz;fixtype;satellitesvisible;"
                + "eph;epv;heading;groundspeed;airspeed;next_wpt;count_wpt;"
                + "dist_to_home;dist_to_current_wpt;mode;system-status;armed;"
                + "is-armable;ekf-ok;type-failure";
    }
    
    @Override
    public String toString() {
        return String.format("%s;%s;%.1f;%.7f;%.7f;%.2f;%.2f;%.3f;%.2f;%.1f;%.4f;%.4f;%.4f;%.2f;" +
                "%.2f;%.2f;%d;%d;%d;%d;%.1f;%.2f;%.2f;%d;%d;%.2f;%.2f;%s;%s;%s;" +
                "%s;%s;%s", 
                date, hour, time, gps.lat, gps.lng, barometer.alt_rel, barometer.alt_abs,
                battery.voltage, battery.current, battery.level, attitude.pitch,
                attitude.yaw, attitude.roll, velocity.vx, velocity.vy, velocity.vz, 
                gpsinfo.fixType, gpsinfo.satellitesVisible, gpsinfo.eph, gpsinfo.epv, 
                sensorUAV.heading, sensorUAV.groundspeed, sensorUAV.airspeed, 
                nextWaypoint, countWaypoint, distanceToHome, distanceToCurrentWaypoint,
                statusUAV.mode, statusUAV.systemStatus, statusUAV.armed, 
                statusUAV.isArmable, statusUAV.ekfOk, typeFailure);
    }
}
