package uav.gcs.planner;

import java.io.File;
import java.io.IOException;
import uav.gcs.struct.Drone;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.mission.Mission3D;
import uav.generic.util.UtilRunThread;

/**
 * Classe que modela o planejador da missão do drone evitando obstáculos.
 * @author Jesimar S. Arantes
 */
public abstract class Planner {
    
    final String dir;
    final Drone drone;
    final Mission3D waypointsMission;
    final Mission3D mission3D;
    final Mission missionGeo;
    
    String fileWaypointsMission;//used in planner hga4m and astar4m
    String sizeWpt;//used in planner hga4m
    final String dirFiles;//global
    final String fileGeoBase;//global
    final String cmdExecPlanner;//local
    final String altitudeFlight;//local
    String time;//local - used in planner hga4m
    String waypoint;//local - used in planner ccqsp4m
    String delta;//local - used in planner hga4m and ccqsp4m
    String maxVel;//local - used in planner hga4m
    String maxCtrl;//local - used in planner hga4m
    String speedCruize;//local - used in planner hga4m
    String typeAircraft;//local - used in planner hga4m

    /**
     * Class constructor - Used in HGA4m
     * @param drone instance of the aircraft
     * @param fileWaypointsMission
     * @param sizeWpt
     * @param dirFiles
     * @param fileGeoBase
     * @param dirPlanner
     * @param cmdExecPlanner
     * @param altitudeFlight
     * @param time
     * @param delta
     * @param maxVel
     * @param maxCtrl
     * @param speedCruize
     * @param typeAircraft
     */
    public Planner(Drone drone, String fileWaypointsMission, String sizeWpt, String dirFiles,
            String fileGeoBase, String dirPlanner, String cmdExecPlanner, 
            String altitudeFlight, String time, String delta, 
            String maxVel, String maxCtrl, String speedCruize, String typeAircraft) {
        this.drone = drone; 
        this.fileWaypointsMission = fileWaypointsMission;
        this.sizeWpt = sizeWpt;
        this.dirFiles = dirFiles;
        this.fileGeoBase = fileGeoBase;
        this.dir = dirPlanner;
        this.cmdExecPlanner = cmdExecPlanner;
        this.altitudeFlight = altitudeFlight;
        this.time = time;
        this.delta = delta;
        this.maxVel = maxVel;
        this.maxCtrl = maxCtrl;
        this.speedCruize = speedCruize;
        this.typeAircraft = typeAircraft;       
        this.waypointsMission = new Mission3D();
        this.mission3D = new Mission3D();
        this.missionGeo = new Mission();
    }
    
    /**
     * Class constructor - Used in CCQSP4m
     * @param drone instance of the aircraft
     * @param dirFiles
     * @param fileGeoBase
     * @param dirPlanner
     * @param cmdExecPlanner
     * @param altitudeFlight
     * @param waypoint
     * @param delta
     */
    public Planner(Drone drone, String dirFiles,
            String fileGeoBase, String dirPlanner, String cmdExecPlanner, 
            String altitudeFlight, String waypoint, String delta) {
        this.drone = drone;
        this.dirFiles = dirFiles;
        this.fileGeoBase = fileGeoBase;
        this.dir = dirPlanner;
        this.cmdExecPlanner = cmdExecPlanner;
        this.altitudeFlight = altitudeFlight;
        this.waypoint = waypoint;
        this.delta = delta;      
        this.waypointsMission = new Mission3D();
        this.mission3D = new Mission3D();
        this.missionGeo = new Mission();
    }
    
    /**
     * Class constructor - Used in AStar4m
     * @param drone instance of the aircraft
     * @param fileWaypointsMission
     * @param dirFiles
     * @param fileGeoBase
     * @param dirPlanner
     * @param cmdExecPlanner
     * @param altitudeFlight
     */
    public Planner(Drone drone, String fileWaypointsMission, String dirFiles, 
            String fileGeoBase, String dirPlanner, String cmdExecPlanner, 
            String altitudeFlight) {
        this.drone = drone; 
        this.fileWaypointsMission = fileWaypointsMission;
        this.dirFiles = dirFiles;
        this.fileGeoBase = fileGeoBase;
        this.dir = dirPlanner;
        this.cmdExecPlanner = cmdExecPlanner;
        this.altitudeFlight = altitudeFlight;      
        this.waypointsMission = new Mission3D();
        this.mission3D = new Mission3D();
        this.missionGeo = new Mission();
    }
    
    public abstract void clearLogs();
    
    public Mission3D getMission3D(){
        return mission3D;
    }
    
    public Mission getMissionGeo(){
        return missionGeo;
    }
    
    boolean execMethod(){
        try {
            boolean isPrint = false;
            boolean isPrintError = false;
            String cmd = cmdExecPlanner + " local";
            UtilRunThread.dualSingleThread(cmd, new File(dir), isPrint, isPrintError);
            return true;
        } catch (IOException ex) {
            System.err.println("Error [IOException] execMethod()");
            ex.printStackTrace();
            return false;
        } catch (InterruptedException ex) {
            System.err.println("Error [InterruptedException] execMethod()");
            ex.printStackTrace();
            return false;
        }
    }
}
