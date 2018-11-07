package uav.gcs.planner;

import java.io.File;
import java.io.IOException;
import lib.uav.struct.mission.Mission;
import lib.uav.struct.mission.Mission3D;
import lib.uav.util.UtilRunThread;
import uav.gcs.struct.Drone;

/**
 * Class models mission planner of drone avoiding obstacles.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 */
public abstract class Planner {
    
    final String dir;
    final Drone drone;
    final Mission3D waypointsMission;
    final Mission3D mission3D;
    final Mission missionGeo;
    
    String fileWaypointsMission;   //used in planner hga4m and astar4m
    String sizeWpt;                //used in planner hga4m
    final String dirFiles;         //global
    final String fileGeoBase;      //global
    final String cmdExecPlanner;   //local
    final String altitudeFlight;   //local
    String time;                   //local - used in planner hga4m
    String delta;                  //local - used in planner hga4m and ccqsp4m
    String maxVel;                 //local - used in planner hga4m
    String maxCtrl;                //local - used in planner hga4m
    String speedCruize;            //local - used in planner hga4m
    String typeAircraft;           //local - used in planner hga4m
    String waypoint;               //local - used in planner ccqsp4m
    String timeHorizon;            //local - used in planner ccqsp4m
    String steps;                  //local - used in planner ccqsp4m
    String stdPos;                 //local - used in planner hga4m and ccqsp4m

    /**
     * Class constructor - Used in HGA4m
     * @param drone instance of the aircraft
     * @param fileWaypointsMission
     * @param numberRoutes number of routes
     * @param dirFiles directory of main files 
     * @param fileGeoBase name of file geoBase
     * @param dirPlanner planner directory
     * @param cmdExecPlanner command to exec the planner
     * @param altitudeFlight flight altitude cruising
     * @param time processing time
     * @param delta delta parameter/risk allocation
     * @param maxVel maximum speed
     * @param maxCtrl maximum control
     * @param speedCruize cruise speed
     * @param typeAircraft type of aircraft
     * @param stdPos standard deviation of drone position
     * @since version 3.0.0
     */
    public Planner(Drone drone, String fileWaypointsMission, String numberRoutes, 
            String dirFiles, String fileGeoBase, String dirPlanner, 
            String cmdExecPlanner, String altitudeFlight, String time, String delta, 
            String maxVel, String maxCtrl, String speedCruize, String typeAircraft, 
            String stdPos) {
        this.drone = drone; 
        this.fileWaypointsMission = fileWaypointsMission;
        this.sizeWpt = numberRoutes;
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
        this.stdPos = stdPos;
        this.waypointsMission = new Mission3D();
        this.mission3D = new Mission3D();
        this.missionGeo = new Mission();
    }
    
    /**
     * Class constructor - Used in CCQSP4m
     * @param drone instance of the aircraft
     * @param dirFiles directory of main files 
     * @param fileGeoBase name of file geoBase
     * @param dirPlanner planner directory
     * @param cmdExecPlanner command to exec the planner
     * @param altitudeFlight flight altitude cruising
     * @param waypoint amount of waypoint used
     * @param timeHorizon time horizon
     * @param delta delta parameter/risk allocation
     * @param steps means waypoints used for the obstacle avoidance
     * @param stdPos standard deviation of drone position
     * @since version 3.0.0
     */
    public Planner(Drone drone, String dirFiles, String fileGeoBase, 
            String dirPlanner, String cmdExecPlanner, String altitudeFlight, 
            String waypoint, String timeHorizon, String delta, String steps, 
            String stdPos) {
        this.drone = drone;
        this.dirFiles = dirFiles;
        this.fileGeoBase = fileGeoBase;
        this.dir = dirPlanner;
        this.cmdExecPlanner = cmdExecPlanner;
        this.altitudeFlight = altitudeFlight;
        this.waypoint = waypoint;
        this.timeHorizon = timeHorizon;
        this.delta = delta;      
        this.steps = steps;
        this.stdPos = stdPos;
        this.waypointsMission = new Mission3D();
        this.mission3D = new Mission3D();
        this.missionGeo = new Mission();
    }
    
    /**
     * Class constructor - Used in AStar4m
     * @param drone instance of the aircraft
     * @param fileWaypointsMission waypoints of the mission
     * @param dirFiles directory of main files 
     * @param fileGeoBase name of file geoBase
     * @param dirPlanner planner directory
     * @param cmdExecPlanner command to exec the planner
     * @param altitudeFlight flight altitude cruising
     * @since version 4.0.0
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
    
    /**
     * Class constructor - G-Path-Planner4m
     * @param drone instance of the aircraft
     * @param dirFiles directory of main files 
     * @param fileGeoBase name of file geoBase
     * @param dirPlanner planner directory
     * @param cmdExecPlanner command to exec the planner
     * @param altitudeFlight flight altitude cruising
     * @since version 4.0.0
     */
    public Planner(Drone drone, String dirFiles, 
            String fileGeoBase, String dirPlanner, String cmdExecPlanner, 
            String altitudeFlight) {
        this.drone = drone;
        this.dirFiles = dirFiles;
        this.fileGeoBase = fileGeoBase;
        this.dir = dirPlanner;
        this.cmdExecPlanner = cmdExecPlanner;
        this.altitudeFlight = altitudeFlight;      
        this.waypointsMission = new Mission3D();
        this.mission3D = new Mission3D();
        this.missionGeo = new Mission();
    }
    
    /**
     * Clears log files generated by method
     * @since version 3.0.0
     */
    public abstract void clearLogs();
    
    /**
     * Get the mission Cartesian coordinates.
     * @return the mission3D
     * @since version 3.0.0
     */
    public Mission3D getMission3D(){
        return mission3D;
    }
    
    /**
     * Get the mission in geographical coordinates.
     * @return the missionGeo
     * @since version 3.0.0
     */
    public Mission getMissionGeo(){
        return missionGeo;
    }
    
    /**
     * Method that runs the path planner.
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 3.0.0
     */
    boolean execMethod(){
        try {
            boolean isPrint = false;
            boolean isPrintError = false;
            String cmd = cmdExecPlanner + " local";
            UtilRunThread.dualSingleThreadWaitFor(cmd, new File(dir), isPrint, isPrintError);
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
