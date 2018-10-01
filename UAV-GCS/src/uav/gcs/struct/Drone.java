package uav.gcs.struct;

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
 * The class models an aircraft/drone.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public class Drone {
    
    public String userEmail;
    public String date;                                //yyyy/MM/dd
    public String hour;                                //HH:mm:ss
    public double time;                                //in seconds
    public int nextWaypoint;
    public int countWaypoint;
    public double distanceToHome;                      //in meters
    public double distanceToCurrentWaypoint;           //in meters  
    public double estimatedTimeToDoRTL;                //in seconds
    public double estimatedConsumptionBatForRTL;       //in percentage
    public double estimatedMaxDistReached;             //in meters
    public double estimatedMaxTimeFlight;              //in seconds
    public String typeFailure;
    
    public Battery battery; 
    public GPS gps;
    public Barometer barometer;       
    public Attitude attitude;
    public Velocity velocity;
    public GPSInfo gpsinfo;
    public SensorUAV sensorUAV;
    public StatusUAV statusUAV;
    public Sonar sonar;                                //in meters
    public Temperature temperature;                    //in degree celsius

    /**
     * Class constructor.
     * @param userEmail email of the use of Oracle Drone System.
     * @since version 2.0.0
     */
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
        sonar = new Sonar();
        temperature = new Temperature();
    }    

    /**
     * Gets the string with title of attributes of drone
     * @return the string with title of attributes of drone
     * @since version 2.0.0
     */
    public String title(){
        return "date;hour;time;lat;lng;alt_rel;alt_abs;voltage_bat;current_bat;"
                + "level_bat;pitch;yaw;roll;vx;vy;vz;fixtype;satellitesvisible;"
                + "eph;epv;heading;groundspeed;airspeed;next_wpt;count_wpt;"
                + "dist_to_home;dist_to_current_wpt;mode;system-status;armed;"
                + "is-armable;ekf-ok;type-failure;est-time-to-do-rtl;est-consumption-bat-rtl;"
                + "est-max-dist;est-max-time;dist-sonar;temperature-sensor";
    }
    
    /**
     * Gets the string with value of attributes of drone
     * @return the string with value of attributes of drone
     * @since version 2.0.0
     */
    @Override
    public String toString() {
        return String.format("%s;%s;%.1f;%.7f;%.7f;%.2f;%.2f;%.3f;%.2f;%.1f;%.4f;%.4f;%.4f;%.2f;" +
                "%.2f;%.2f;%d;%d;%d;%d;%.1f;%.2f;%.2f;%d;%d;%.2f;%.2f;%s;%s;%s;" +
                "%s;%s;%s;%.2f;%.2f;%.2f;%.2f;%s;%s", 
                date, hour, time, gps.lat, gps.lng, barometer.alt_rel, barometer.alt_abs,
                battery.voltage, battery.current, battery.level, attitude.pitch,
                attitude.yaw, attitude.roll, velocity.vx, velocity.vy, velocity.vz, 
                gpsinfo.fixType, gpsinfo.satellitesVisible, gpsinfo.eph, gpsinfo.epv, 
                sensorUAV.heading, sensorUAV.groundspeed, sensorUAV.airspeed, 
                nextWaypoint, countWaypoint, distanceToHome, distanceToCurrentWaypoint,
                statusUAV.mode, statusUAV.systemStatus, statusUAV.armed, 
                statusUAV.isArmable, statusUAV.ekfOk, typeFailure, estimatedTimeToDoRTL,
                estimatedConsumptionBatForRTL, estimatedMaxDistReached, 
                estimatedMaxTimeFlight, sonar.distance, temperature.temperature);
    }
    
    /**
     * Update the data of drone.
     * @param answer string with all data of autopilot.
     * @since version 4.0.0
     */
    public void parserAllDataToDrone(String answer) {
        String v[] = answer.split(";");
        date = v[0];
        hour = v[1];
        time = Double.parseDouble(v[2]);
        gps.lat = Double.parseDouble(v[3]);
        gps.lng = Double.parseDouble(v[4]);
        barometer.alt_rel = Double.parseDouble(v[5]);
        barometer.alt_abs = Double.parseDouble(v[6]);
        battery.voltage = Double.parseDouble(v[7]);
        battery.current = Double.parseDouble(v[8]);
        battery.level = Double.parseDouble(v[9]);
        attitude.pitch = Double.parseDouble(v[10]);
        attitude.yaw = Double.parseDouble(v[11]);
        attitude.roll = Double.parseDouble(v[12]);
        velocity.vx = Double.parseDouble(v[13]);
        velocity.vy = Double.parseDouble(v[14]);
        velocity.vz = Double.parseDouble(v[15]);
        gpsinfo.fixType = Integer.parseInt(v[16]);
        gpsinfo.satellitesVisible = Integer.parseInt(v[17]);
        gpsinfo.eph = Integer.parseInt(v[18]);
        gpsinfo.epv = Integer.parseInt(v[19]);
        sensorUAV.heading = Double.parseDouble(v[20]);
        sensorUAV.groundspeed = Double.parseDouble(v[21]);
        sensorUAV.airspeed = Double.parseDouble(v[22]);
        nextWaypoint = Integer.parseInt(v[23]);
        countWaypoint = Integer.parseInt(v[24]);
        distanceToHome = Double.parseDouble(v[25]);
        distanceToCurrentWaypoint = Double.parseDouble(v[26]);
        statusUAV.mode = v[27];
        statusUAV.systemStatus = v[28];
        statusUAV.armed = Boolean.parseBoolean(v[29]);
        statusUAV.isArmable = Boolean.parseBoolean(v[30]);
        statusUAV.ekfOk = Boolean.parseBoolean(v[31]);
        typeFailure = v[32];
        estimatedTimeToDoRTL = Double.parseDouble(v[33]);
        estimatedConsumptionBatForRTL = Double.parseDouble(v[34]);
        estimatedMaxDistReached = Double.parseDouble(v[35]);
        estimatedMaxTimeFlight = Double.parseDouble(v[36]);
        sonar.distance = v[37].equals("NONE") ? -1.0 : Double.parseDouble(v[37]);
        temperature.temperature = v[38].equals("NONE") ? -1.0 : Double.parseDouble(v[38]);
    }
}
