package uav.gcs.replanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import uav.gcs.struct.Drone;
import uav.generic.struct.geom.PointGeo;
import uav.generic.util.UtilGeo;
import uav.generic.util.UtilIO;
import uav.generic.struct.constants.TypeAltitudeDecay;

/**
 * Classe que modela o replanejador de rotas MPGA4s.
 * @author Jesimar S. Arantes
 */
public class MPGA4s extends Replanner{
    
    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param dirFiles
     * @param fileGeoBase
     * @param dirReplanner
     * @param cmdExecReplanner
     * @param typeAltitudeDecay
     * @param time
     * @param qtdWpt
     * @param delta
     */
    public MPGA4s(Drone drone, String dirFiles, String fileGeoBase, 
            String dirReplanner, String cmdExecReplanner, String typeAltitudeDecay, 
            String time, String qtdWpt, String delta) {
        super(drone, dirFiles, fileGeoBase, dirReplanner, cmdExecReplanner, 
                typeAltitudeDecay, time, qtdWpt, delta);
    }

    @Override
    public boolean exec() {
        boolean itIsOkUpdate = updateFileConfig();
        boolean itIsOkExec   = execMethod();
        boolean itIsOkParse  = parseRoute3DtoGeo();
        return itIsOkUpdate && itIsOkExec && itIsOkParse;
    }
    
    @Override
    public boolean updateFileConfig() { 
        try {
            PointGeo pGeo = UtilGeo.getPointGeo(dirFiles + fileGeoBase);
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
            UtilIO.copyFileMofifIFA(src, dst, state, 8, qtdWpt, 20, delta, 26);
            
            File src_mpga = new File(dir + "instance-base");
            File dst_mpga = new File(dir + "instance");
            UtilIO.copyFileMofifIFA(src_mpga, dst_mpga, time, 117);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException]: updateFileConfig()");
            return false;
        }
    }
    
    @Override
    public boolean parseRoute3DtoGeo() {
        try{
            String nameFileRoute3D =  "route.txt";
            String nameFileRouteGeo = "routeGeo.txt";
            PointGeo pGeo = UtilGeo.getPointGeo(dirFiles + fileGeoBase);        
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
    
    @Override
    public void clearLogs() {
        UtilIO.deleteFile(new File(dir), ".log");
        UtilIO.deleteFile(new File(dir), ".png");
        new File(dir + "route.txt").delete();
        new File(dir + "routeGeo.txt").delete();
    }         
}
