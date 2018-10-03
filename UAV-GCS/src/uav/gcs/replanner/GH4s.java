package uav.gcs.replanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import lib.uav.struct.constants.TypeAltitudeDecay;
import lib.uav.struct.geom.PointGeo;
import lib.uav.util.UtilGeo;
import lib.uav.util.UtilIO;
import uav.gcs.struct.Drone;

/**
 * Class that runs the path replanner GH4s.
 * @author Jesimar S. Arantes
 * @since version 3.0.0
 * @see Replanner
 */
public class GH4s extends Replanner{
    
    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param dirFiles directory of main files 
     * @param fileGeoBase name of file geoBase
     * @param dirReplanner replanner directory
     * @param cmdExecReplanner command to exec the replanner
     * @param typeAltitudeDecay type of altitude decay [CONSTANT or LINEAR]
     * @param time processing time
     * @param qtdWpt amount of waypoints
     * @param delta delta parameter/risk allocation
     * @since version 3.0.0
     */
    public GH4s(Drone drone, String dirFiles, String fileGeoBase, 
            String dirReplanner, String cmdExecReplanner, String typeAltitudeDecay, 
            String time, String qtdWpt, String delta) {
        super(drone, dirFiles, fileGeoBase, dirReplanner, cmdExecReplanner, 
                typeAltitudeDecay, time, qtdWpt, delta);
    }

    /**
     * Execute the replanner
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 3.0.0
     */
    @Override
    public boolean exec() {
        boolean itIsOkUpdate = updateFileConfig();
        boolean itIsOkExec   = execMethod();
        boolean itIsOkParse  = parseRoute3DtoGeo();
        return itIsOkUpdate && itIsOkExec && itIsOkParse;
    }
    
    /**
     * Updates the configuration file used by the method.
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 3.0.0
     */
    @Override
    public boolean updateFileConfig() { 
        try {
            PointGeo pGeo = UtilGeo.getPointGeoBase(dirFiles + fileGeoBase);
            double px = UtilGeo.convertGeoToX(pGeo, drone.gps.lng);
            double py = UtilGeo.convertGeoToY(pGeo, drone.gps.lat);
            double vel = 1.5;//drone.getSensorUAV().groundspeed;
            int heading = UtilGeo.convertAngleAviationToAngleMath((int)drone.sensorUAV.heading);
            double angle = Math.toRadians(heading);//Math.atan2(vy, vx);             
            //double vx = drone.getVelocity().vx;
            //double vy = drone.getVelocity().vy;            
            //double dt = 2;
            //px = px + vx * dt;//esse tipo de projecao nao fica bom
            //py = py + vy * dt;//esse tipo de projecao nao fica bom
            
            File src = new File(dir + "config-base.sgl");
            File dst = new File(dir + "config.sgl");
            String state = px + " " + py + " " + vel + " " + angle;
            UtilIO.copyFileModifiedIFA(src, dst, state, 8, qtdWpt, 20, delta, 26);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException]: updateFileConfig()");
            return false;
        }
    }
    
    /**
     * Converts the route in Cartesian coordinates to geographic coordinates
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 3.0.0
     */
    @Override
    public boolean parseRoute3DtoGeo() {
        try{
            String nameFileRoute3D =  "route.txt";
            String nameFileRouteGeo = "routeGeo.txt";
            PointGeo pGeo = UtilGeo.getPointGeoBase(dirFiles + fileGeoBase);        
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);        
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            double h = drone.barometer.alt_rel;
            int qtdWaypoints = UtilIO.getLineNumber(new File(dir + nameFileRoute3D));
            double frac = h/qtdWaypoints;
            int countLines = 0;
            while(readRoute3D.hasNext()){                        
                double x = readRoute3D.nextDouble();
                double y = readRoute3D.nextDouble();           
                if (typeAltitudeDecay.equals(TypeAltitudeDecay.LINEAR)){
                    h = h - frac;
                }
                printGeo.println(UtilGeo.parseToGeo(pGeo, x, y, h, ";"));
                countLines++;
            }
            if (countLines == 0){
                System.out.println("Route-Empty");
            }
            readRoute3D.close();
            printGeo.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException] parseRoute3DtoGeo()");
            return false;
        } catch (IOException ex) {
            System.out.println("Warning [IOException] parseRoute3DtoGeo()");
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
        new File(dir + "route.txt").delete();
        new File(dir + "routeGeo.txt").delete();
    }         
}
