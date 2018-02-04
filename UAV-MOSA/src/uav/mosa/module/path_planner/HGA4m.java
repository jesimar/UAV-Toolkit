package uav.mosa.module.path_planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import lib.color.StandardPrints;
import uav.generic.struct.Mission3D;
import uav.generic.struct.PointGeo;
import uav.generic.util.UtilGeo;
import uav.generic.util.UtilIO;
import uav.hardware.aircraft.Drone;

/**
 *
 * @author jesimar
 */
public class HGA4m extends Planner{
    
    public HGA4m(Drone drone, Mission3D pointsMission) {
        super(drone, pointsMission);
    }   
    
    @Override
    public boolean execMission(int i) {
        boolean itIsOkUpdate = updateFileConfig(i);
        boolean itIsOkpathAB = definePathAB(i);        
        boolean itIsOkExec   = execMethod();
        boolean itIsOkRoute  = createFileFinalRoute(i);
        boolean itIsOkParse  = parseRoute3DtoGeo(i);
        return itIsOkUpdate && itIsOkpathAB && itIsOkExec && itIsOkRoute && itIsOkParse;
    }
    
    @Override
    public boolean updateFileConfig(int i) {
        try {
            double px1 = pointsMission.getPosition3D(i).getX();
            double py1 = pointsMission.getPosition3D(i).getY();
            double px2 = pointsMission.getPosition3D(i+1).getX();
            double py2 = pointsMission.getPosition3D(i+1).getY();
            double dx = px2 - px1;
            double dy = py2 - py1;
            //distancia mais uma margem de seguranca
            double dist = Math.sqrt(dx*dx+dy*dy)+2;
            
            File src_ga = new File(dir + "ga-config-base");
            File dst_ga = new File(dir + "ga-config");
            String time = config.getTimeExec();
            String timeH = String.format("%d", (int)(dist));
            String qtdWpt = String.format("%d", (int)(dist));
            String delta = config.getDelta();
            String maxVel = config.getMaxVelocity();
            String maxCtrl = config.getMaxControl();
            UtilIO.copyFileMofifMosa(src_ga, dst_ga, time, 207, delta, 304,
                    qtdWpt, 425, timeH, 426, maxVel, 427, maxCtrl, 428);
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: updateFileConfig()");
            return false;
        }
    }
    
    private double vx1 = 0.0;
    private double vy1 = 0.0;
    private boolean definePathAB(int i) {
        try {
            double px1 = pointsMission.getPosition3D(i).getX();
            double py1 = pointsMission.getPosition3D(i).getY();
            double px2 = pointsMission.getPosition3D(i+1).getX();
            double py2 = pointsMission.getPosition3D(i+1).getY();
            double vx2 = 0;
            double vy2 = 0;
            if (i < pointsMission.size() - 2){
                double px3 = pointsMission.getPosition3D(i+2).getX();
                double py3 = pointsMission.getPosition3D(i+2).getY();
                double dx = px3 - px1;
                double dy = py3 - py1;
                double norm = Math.sqrt(dx*dx+dy*dy);
                double vc = drone.getSpeedCruize();
                vx2 = dx * vc/norm;
                vy2 = dy * vc/norm;
            }   
            
            PrintStream print = new PrintStream(new File(dir + "mission-config.sgl"));
            print.println("----------- start state (px py vx vy) -----------");
            if (config.isDynBetweenWpts()){
                print.println(px1 + "," + py1 + "," + vx1 + "," + vy1);
            } else {
                print.println(px1 + "," + py1 + ",0.0,0.0");
            }
            print.println("--------------- end point (px py)---------------");
            if (config.isDynBetweenWpts()){
                print.println(px2 + "," + py2 + "," + vx2 + "," + vy2);
            } else {
                print.println(px2 + "," + py2 + ",0.0,0.0");
            }
            print.println();
            print.println("<TrueName>");
            print.println("Config2D-2.sgl");
            print.close();
            vx1 = vx2;
            vy1 = vy2;
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: definePathAB()");
            return false;
        } 
    }  
    
    private boolean createFileFinalRoute(int i) {
        try {
            File src = new File(dir + "output-simulation.log");
            File dst = new File(dir + "out-sim"+i+".log");
            UtilIO.copyFile(src, dst);
            File route = new File(dir + "route3D"+i+".txt");
            
            Scanner sc = new Scanner(dst);
            PrintStream print = new PrintStream(route);
            boolean test1 = false;
            boolean test2 = false;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!test1 && !test2){
                    if (line.equals("------------------------ [ states ] ------------------------")){
                        test1 = true;
                    }
                }else if (test1 && !test2){
                    if (!line.equals("------------------------ [ controls ] ------------------------")){
                        print.println(line);
                    }else{
                        test2 = true;
                    }
                }
            }
            sc.close();
            print.close();
            return true;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException]: createFileFinalRoute()");
            return false;
        }
    }
    
    @Override
    public boolean parseRoute3DtoGeo(int i){      
        try {
            String nameFileRoute3D =  "route3D"  + i + ".txt";
            String nameFileRouteGeo = "routeGeo" + i + ".txt";
            PointGeo pGeo = UtilGeo.getPointGeo(dir + config.getFileGeoBase());
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            while(readRoute3D.hasNext()){
                double x = readRoute3D.nextDouble();
                double y = readRoute3D.nextDouble();
                readRoute3D.nextDouble();
                readRoute3D.nextDouble();
                double h = config.getAltitudeRelative();            
                printGeo.println(UtilGeo.parseToGeo(pGeo, x, y, h, ";"));
            }
            readRoute3D.close();
            printGeo.close();
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException] parseRoute3DtoGeo()");
            return false;
        } 
    }
    
    @Override
    public void clearLogs() {
        UtilIO.deleteFile(new File(dir), ".log");
        UtilIO.deleteFile(new File(dir), ".png");
        new File(dir + "log_error.txt").delete(); 
    }

}
