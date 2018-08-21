package uav.ifa.module.path_replanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import lib.color.StandardPrints;
import uav.generic.util.UtilGeo;
import uav.generic.util.UtilIO;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.struct.constants.TypeAltitudeDecay;

/**
 * Classe que modela o replanejador de rotas GA4s.
 * @author Jesimar S. Arantes
 */
public class GA4s extends Replanner{
    
    /**
     * Class constructor
     * @param drone instance of the aircraft
     */
    public GA4s(Drone drone) {
        super(drone);
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
            double px = UtilGeo.convertGeoToX(pointGeo, drone.getGPS().lng);
            double py = UtilGeo.convertGeoToY(pointGeo, drone.getGPS().lat);
            double vel = 1.5;//drone.getSensorUAV().groundspeed;
            int head = (int)drone.getSensorUAV().heading;
            int heading = UtilGeo.convertAngleAviationToAngleMath(head);
            double angle = Math.toRadians(heading);//Math.atan2(vy, vx);             
            //double vx = drone.getVelocity().vx;
            //double vy = drone.getVelocity().vy;            
            //double dt = 2;
            //px = px + vx * dt;//esse tipo de projecao nao fica bom
            //py = py + vy * dt;//esse tipo de projecao nao fica bom
            
            File src = new File(dir + "config-base.sgl");
            File dst = new File(dir + "config.sgl");
            String state = px + " " + py + " " + vel + " " + angle;
            String qtdWpt = config.getNumberWaypointsReplanner();
            String delta = config.getDeltaReplanner();
            UtilIO.copyFileModifiedIFA(src, dst, state, 8, qtdWpt, 20, delta, 26);
            
            File src_ga = new File(dir + "instance-base");
            File dst_ga = new File(dir + "instance");
            String time = config.getTimeExecReplanner();
            UtilIO.copyFileModifiedIFA(src_ga, dst_ga, time, 117);
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: updateFileConfig()");
            return false;
        }
    }   

    @Override
    public boolean parseRoute3DtoGeo() {
        try {
            String nameFileRoute3D =  "route.txt";
            String nameFileRouteGeo = "routeGeo.txt";     
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);        
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            double h = drone.getBarometer().alt_rel;
            int qtdWpt = UtilIO.getLineNumber(new File(dir + nameFileRoute3D));
            double frac = h/qtdWpt;
            int countLines = 0;
            while(readRoute3D.hasNext()){                        
                double x = readRoute3D.nextDouble();
                double y = readRoute3D.nextDouble();           
                if (config.getTypeAltitudeDecayReplanner().equals(TypeAltitudeDecay.LINEAR)){
                    h = h - frac;
                }
                printGeo.println(UtilGeo.parseToGeo(pointGeo, x, y, h, ";"));   
                countLines++;
            }
            if (countLines == 0){
                StandardPrints.printMsgWarning("Route-Empty");
            }
            readRoute3D.close();
            printGeo.close();
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException] parseRoute3DtoGeo()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] parseRoute3DtoGeo()");
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
