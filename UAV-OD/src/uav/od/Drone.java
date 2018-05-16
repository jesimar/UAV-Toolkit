package uav.od;

/**
 * Classe que modela os atributos do drone.
 * @author Jesimar S. Arantes
 */
public class Drone {
    
    public String date;             //yyyy/MM/dd
    public String hour;             //HH:mm:ss
    public double time;             //in seconds
    public int nextWaypoint;
    public int countWaypoint;
    public double distToHome;       //in meters
    public double distToCurrentWpt; //in meters    
    public String typeFailure;
    public double voltage;          //in millivolts
    public double current;          //in 10 * milliamperes
    public double level;            //in percentage (%)
    public double lat;
    public double lng;
    public double alt_rel;          //in meters (in relation to launch level)
    public double alt_abs;          //in meters (in relation to sea level)
    public double pitch;            //in radians
    public double yaw;              //in radians
    public double roll;             //in radians
    public double vx;               //velocity in m/s
    public double vy;               //velocity in m/s
    public double vz;               //velocity in m/s
    public int fixType;             //0-1: no fix, 2: 2D fix, 3: 3D fix
    public int satellitesVisible;   //number of visible satellites
    public int eph;                 //GPS horizontal dilution of position (HDOP)
    public int epv;                 //GPS vertical   dilution of position (VDOP)
    public double heading;          //in degrees (0 ... 360)
    public double groundspeed;      //in metres/second
    public double airspeed;         //in metres/second
    public String mode;
    public String systemStatus;
    public boolean armed;
    public boolean isArmable;    
    public boolean ekfOk;

    public Drone() {

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
        return String.format("%s;%s;%.1f;%.7f;%.7f;%.2f;%.2f;%.3f;%.2f;%.1f;%.4f;" + 
                "%.4f;%.4f;%.2f;%.2f;%.2f;%d;%d;%d;%d;%.1f;%.2f;%.2f;%d;%d;%.2f;" + 
                "%.2f;%s;%s;%s;%s;%s;%s", 
                date, hour, time, lat, lng, alt_rel, alt_abs, voltage, current, 
                level, pitch, yaw, roll, vx, vy, vz, fixType, satellitesVisible, 
                eph, epv, heading, groundspeed, airspeed, nextWaypoint, 
                countWaypoint, distToHome, distToCurrentWpt, mode, 
                systemStatus, armed, isArmable, ekfOk, typeFailure);
    }
}
