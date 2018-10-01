package uav.generic.hardware.aircraft;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The class models an aircraft/drone.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public abstract class Drone {
    
    DroneAttributes attributes;
    DroneSensors sensors;
    DroneInfo info;

    /**
     * Gets the attributes of drone
     * @return the attributes of drone
     * @since version 4.0.0
     */
    public DroneAttributes getAttributes() {
        return attributes;
    }

    /**
     * Gets the sensors of drone
     * @return the sensors of drone
     * @since version 4.0.0
     */
    public DroneSensors getSensors() {
        return sensors;
    }

    /**
     * Gets the info of drone
     * @return the info of drone
     * @since version 4.0.0
     */
    public DroneInfo getInfo() {
        return info;
    }
    
    /**
     * Gets the string with title of attributes of drone
     * @return the string with title of attributes of drone
     * @since version 2.0.0
     */
    public String title(){
        return "date;hour;time;lat;lng;alt-rel;alt-abs;voltage-bat;current-bat;"
                + "level-bat;pitch;yaw;roll;vel-x;vel-y;vel-z;fix-type;satellites-visible;"
                + "eph;epv;heading;groundspeed;airspeed;next-wpt;count-wpt;"
                + "dist-to-home;dist-to-current-wpt;mode;system-status;armed;"
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
        String dateHour = new SimpleDateFormat("yyyy/MM/dd;HH:mm:ss").format(new Date());
        String dist = sensors.sonar.distance == -1 ? "NONE" : 
                String.format("%.2f", sensors.sonar.distance);
        String temp = sensors.temperature.temperature == -1 ? "NONE" : 
                String.format("%.2f", sensors.temperature.temperature);
        return String.format("%s;%.1f;%.7f;%.7f;%.2f;%.2f;%.3f;%.2f;%.1f;%.4f;"
                + "%.4f;%.4f;%.2f;%.2f;%.2f;%d;%d;%d;%d;%.1f;%.2f;%.2f;%d;%d;"
                + "%.2f;%.2f;%s;%s;%s;%s;%s;%s;%.2f;%.2f;%.2f;%.2f;%s;%s", 
                dateHour, info.time, sensors.gps.lat, sensors.gps.lng, 
                sensors.barometer.alt_rel, sensors.barometer.alt_abs,
                sensors.battery.voltage, sensors.battery.current, 
                sensors.battery.level, sensors.attitude.pitch,
                sensors.attitude.yaw, sensors.attitude.roll, 
                sensors.velocity.vx, sensors.velocity.vy, sensors.velocity.vz, 
                sensors.gpsinfo.fixType, sensors.gpsinfo.satellitesVisible, 
                sensors.gpsinfo.eph, sensors.gpsinfo.epv, 
                sensors.sensorUAV.heading, sensors.sensorUAV.groundspeed, 
                sensors.sensorUAV.airspeed, info.nextWaypoint, info.countWaypoint, 
                info.distanceToHome, info.distanceToCurrentWaypoint,
                sensors.statusUAV.mode, sensors.statusUAV.systemStatus, 
                sensors.statusUAV.armed, sensors.statusUAV.isArmable, 
                sensors.statusUAV.ekfOk, info.typeFailure, info.estimatedTimeToDoRTL,
                info.estimatedConsumptionBatForRTL, info.estimatedMaxDistReached, 
                info.estimatedMaxTimeFlight, dist, temp);
    }
}
