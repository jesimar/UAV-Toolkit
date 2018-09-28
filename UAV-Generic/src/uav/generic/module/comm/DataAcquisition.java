package uav.generic.module.comm;

import java.io.PrintStream;
import uav.generic.struct.Heading;
import uav.generic.struct.Parameter;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.Waypoint;
import uav.generic.hardware.aircraft.Drone;

/**
 * Classe que modela toda a comunicação do sistema MOSA e IFA com o Drone.
 * @author Jesimar S. Arantes
 */
public abstract class DataAcquisition {
           
    public Drone drone;
    public PrintStream printLogOverhead;
    public boolean debug = false; 
    
    public void setDebug(boolean debug){
        this.debug = debug;
    }
    
    public abstract boolean serverIsRunning();
        
    public abstract boolean setWaypoint(Waypoint wp);
    
    public abstract boolean appendWaypoint(Waypoint wp);
    
    public abstract boolean setMission(Mission mission);
    
    public abstract boolean setMission(String missionJson);
    
    public abstract boolean appendMission(Mission mission);
    
    public abstract boolean appendMission(String missionJson);
    
    public abstract boolean setVelocity(double velocity);
    
    public abstract boolean setParameter(Parameter parameter);
    
    public abstract boolean setHeading(Heading heading);
    
    public abstract boolean setMode(String mode);
    
    public abstract boolean setParameter(String key, double value);
    
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