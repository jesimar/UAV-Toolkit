package uav.gcs.planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import lib.uav.struct.Waypoint;
import lib.uav.struct.geom.PointGeo;
import lib.uav.struct.geom.Position3D;
import lib.uav.util.UtilGeo;
import lib.uav.util.UtilIO;
import uav.gcs.struct.Drone;

/**
 * The class models the path planner CCQSP4m.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 * @see Planner
 */
public class CCQSP4m extends Planner{ 
    
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
    public CCQSP4m(Drone drone, String dirFiles, String fileGeoBase, 
            String dirPlanner, String cmdExecPlanner, String altitudeFlight, 
            String waypoint, String timeHorizon, String delta, String steps, 
            String stdPos) {
        super(drone, dirFiles, fileGeoBase, dirPlanner, cmdExecPlanner, 
                altitudeFlight, waypoint, timeHorizon, delta, steps, stdPos);
    }
    
    /**
     * Execute the mission
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 3.0.0
     */
    public boolean execMission() {
        boolean itIsOkUpdate = updateFileConfig();     
        boolean itIsOkExec   = execMethod();
        boolean itIsOkCopy   = copyRoute3D();
        boolean itIsOkParse  = parseRoute3DtoGeo();
        return itIsOkUpdate && itIsOkExec && itIsOkCopy && itIsOkParse;
    }
    
    /**
     * Updates the configuration file used by the method.
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 3.0.0
     */
    public boolean updateFileConfig() {
        try {
            File src_instance = new File(dir + "instance-base");
            File dst_instance = new File(dir + "instance");
            UtilIO.copyFileModifiedMOSA(src_instance, dst_instance, steps, 177,
                    delta, 189, stdPos, 234, waypoint, 298, timeHorizon, 299);
            File src_mission = new File(dir + "mission-ccqsp.sgl");
            File dst_mission = new File(dir + "mission.sgl");
            UtilIO.copyFileModifiedMOSA(src_mission, dst_mission,
                    delta, 5, timeHorizon, 10);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException]: updateFileConfig() " + ex);
            return false;
        }
    }
    
    /**
     * Copy the route file
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 3.0.0
     */
    public boolean copyRoute3D(){
        try {
            File fileRoute3D = new File(dir + "route3D.txt");
            PrintStream print3D = new PrintStream(fileRoute3D);
            Scanner readRoute3D = new Scanner(new File(dir + "output.txt"));
            readRoute3D.nextInt();
            double h = Double.parseDouble(altitudeFlight);
            while(readRoute3D.hasNext()){
                double x = readRoute3D.nextDouble();
                double y = readRoute3D.nextDouble();
                readRoute3D.nextInt();
                print3D.println(x + ";" + y + ";" + h);
            }
            readRoute3D.close();
            print3D.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException] copyRoute3D()");
            return false;
        } 
    }
    
    /**
     * Converts the route in Cartesian coordinates to geographic coordinates 
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 3.0.0
     */
    public boolean parseRoute3DtoGeo(){
        try {
            String nameFileRoute3D =  "output.txt";
            String nameFileRouteGeo = "routeGeo.txt";
            PointGeo pGeoBase = UtilGeo.getPointGeoBase(dirFiles + fileGeoBase);
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            int countLines = 0;
            readRoute3D.nextInt();
            double h = Double.parseDouble(altitudeFlight);
            while(readRoute3D.hasNext()){
                double x = readRoute3D.nextDouble();
                double y = readRoute3D.nextDouble();
                readRoute3D.nextInt();
                printGeo.println(UtilGeo.parseToGeo(pGeoBase, x, y, h, ";"));
                mission3D.addPosition(new Position3D(x, y, h));
                missionGeo.addWaypoint(new Waypoint(UtilGeo.parseToGeo1(pGeoBase, x, y, h)));
                countLines++;
            }
            if (countLines == 0){
                System.out.println("Route-Empty");
                if (!drone.statusUAV.armed){
                    System.exit(1);
                }
            }
            readRoute3D.close();
            printGeo.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException] parseRoute3DtoGeo()");
            return false;
        } 
    }
    
    /**
     * Clears log files generated by method
     * @since version 3.0.0
     */
    @Override
    public void clearLogs() {
        UtilIO.deleteFile(new File(dir), ".log");
        UtilIO.deleteFile(new File(dir), ".png");
        UtilIO.deleteFile(new File(dir), ".err");
        new File(dir + "log_error.txt").delete(); 
    }

}
