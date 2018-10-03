package lib.uav.module.comm;

import java.io.PrintStream;
import lib.uav.struct.Heading;
import lib.uav.struct.Parameter;
import lib.uav.struct.mission.Mission;
import lib.uav.struct.Waypoint;
import lib.uav.hardware.aircraft.Drone;

/**
 * The class models all MOSA and IFA system communication with Autopilot.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public abstract class DataAcquisition {
           
    public Drone drone;
    public PrintStream printLogOverhead;
    public boolean debug = false; 
    
    /**
     * Set the value debug
     * @param debug if {@code true} print message to debug 
     *              if {@code false} don't print message
     * @since version 4.0.0
     */
    public void setDebug(boolean debug){
        this.debug = debug;
    }
    
    public abstract boolean serverIsRunning();
        
    public abstract boolean setWaypoint(Waypoint waypoint);
    
    public abstract boolean appendWaypoint(Waypoint waypoint);
    
    public abstract boolean setMission(Mission mission);
    
    public abstract boolean setMission(String missionJson);
    
    public abstract boolean appendMission(Mission mission);
    
    public abstract boolean appendMission(String missionJson);
    
    public abstract boolean setHeading(Heading heading);
    
    public abstract boolean setMode(String mode);
    
    public abstract boolean setParameter(Parameter parameter);
    
    public abstract boolean setParameter(String key, double value);
    
    public abstract boolean setVelocity(double velocity);
    
    public abstract boolean setNavigationSpeed(double value);
    
    public abstract void getLocation();
    
    public abstract void getGPS();
    
    public abstract void getBarometer();
    
    public abstract void getBattery();
    
    public abstract void getAttitude();
    
    public abstract void getVelocity();
            
    public abstract void getHeading();
    
    public abstract void getGroundSpeed();
    
    public abstract void getAirSpeed();
    
    public abstract void getGPSInfo();
    
    public abstract void getMode();
    
    public abstract void getSystemStatus();
    
    public abstract void getArmed();
    
    public abstract void getIsArmable();

    public abstract void getEkfOk();
    
    public abstract void getNextWaypoint();
    
    public abstract void getCountWaypoint();
    
    public abstract void getDistanceToWptCurrent();
    
    public abstract void getDistanceToHome();
    
    public abstract void getHomeLocation();
    
    public abstract void getParameters();
    
    public abstract void getAllInfoSensors();
    
}
