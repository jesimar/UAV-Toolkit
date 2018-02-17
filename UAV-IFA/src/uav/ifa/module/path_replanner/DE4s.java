package uav.ifa.module.path_replanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import lib.color.StandardPrints;
import uav.generic.struct.Constants;
import uav.generic.struct.PointGeo;
import uav.generic.util.UtilGeo;
import uav.generic.util.UtilIO;
import uav.hardware.aircraft.Drone;

/**
 *
 * @author jesimar
 */
public class DE4s extends Replanner{
    
    public DE4s(Drone drone) {
        super(drone);
    }

    @Override
    public boolean exec() {
        boolean itIsOkUpdate = updateFileConfig();
        boolean itIsOkExec   = execMethod();
        boolean itIsOkParse  = parseRoute3DtoGeo();
        return itIsOkUpdate && itIsOkExec && itIsOkParse;
    }
    
    //melhorar
    @Override
    public boolean updateFileConfig() { 
        try {
            PointGeo pGeo = UtilGeo.getPointGeo(dir + config.getFileGeoBase());
            double px = UtilGeo.convertGeoToX(pGeo, drone.getGPS().lng);
            double py = UtilGeo.convertGeoToY(pGeo, drone.getGPS().lat);
            double vel = 1.5;//drone.getSensorUAV().groundspeed;
            double angle = Math.toRadians(drone.getSensorUAV().heading);//Math.atan2(vy, vx);            
//            double vx = drone.getVelocity().vx;
//            double vy = drone.getVelocity().vy;            
//            double dt = 2;
//            px = px + vx * dt;
//            py = py + vy * dt;
            
            File src = new File(dir + "config-base.sgl");
            File dst = new File(dir + "config.sgl");
            String state = px + " " + py + " " + vel + " " + angle;
            String qtdWpt = config.getQtdWaypoints();
            String delta = config.getDelta();
            UtilIO.copyFileMofifIfa(src, dst, state, 8, qtdWpt, 20, delta, 26);
            
            File src_ga = new File(dir + "instance-base");
            File dst_ga = new File(dir + "instance");
            String time = config.getTimeExec();
            UtilIO.copyFileMofifIfa(src_ga, dst_ga, time, 117);
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
            PointGeo pGeo = UtilGeo.getPointGeo(dir + config.getFileGeoBase());
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            double h = drone.getGPS().alt_rel;
            int qtdWpt = UtilIO.getLineNumber(new File(dir + nameFileRoute3D));
            double frac = h/qtdWpt;
            while(readRoute3D.hasNext()){
                double x = readRoute3D.nextDouble();
                double y = readRoute3D.nextDouble();
                if (config.getTypeAltitudeDecay().equals(Constants.TYPE_ALTITUDE_DECAY_LINEAR)){
                    h = h - frac;
                }else if (config.getTypeAltitudeDecay().equals(Constants.TYPE_ALTITUDE_DECAY_LOG)){
                    h = h - h/qtdWpt;//Falta Terminar
                }
                printGeo.println(UtilGeo.parseToGeo(pGeo, x, y, h, ";"));
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
