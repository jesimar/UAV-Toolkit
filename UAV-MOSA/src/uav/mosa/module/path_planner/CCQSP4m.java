package uav.mosa.module.path_planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import lib.color.StandardPrints;
import uav.generic.struct.mission.Mission3D;
import uav.generic.struct.geom.Position3D;
import uav.generic.struct.Waypoint;
import uav.generic.util.UtilGeo;
import uav.generic.util.UtilIO;
import uav.generic.hardware.aircraft.Drone;

/**
 * Classe que modela o planejador de rotas CCQSP4m. 
 * @author Jesimar S. Arantes
 */
public class CCQSP4m extends Planner{
    
    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param waypointsMission waypoints of the mission
     */
    public CCQSP4m(Drone drone, Mission3D waypointsMission) {
        super(drone, waypointsMission);
    }   
    
    public boolean execMission() {
        boolean itIsOkUpdate = updateFileConfig();     
        boolean itIsOkExec   = execMethod();
        boolean itIsOkCopy   = copyRoute3D();
        boolean itIsOkParse  = parseRoute3DtoGeo();
        return itIsOkUpdate && itIsOkExec && itIsOkCopy && itIsOkParse;
    }
    
    public boolean updateFileConfig() {
        try {
            File src_instance = new File(dir + "instance-base");
            File dst_instance = new File(dir + "instance");
            String delta = config.getDeltaPlannerCCQSP4m();
            String qtdWpt = config.getWaypointsPlannerCCQSP4m();
            UtilIO.copyFileModifiedMOSA(src_instance, dst_instance, delta, 189, 
                    qtdWpt, 298, qtdWpt, 299);
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: updateFileConfig()");
            return false;
        }
    }
    
    public boolean copyRoute3D(){
        try {
            File fileRoute3D = new File(dir + "route3D.txt");
            PrintStream print3D = new PrintStream(fileRoute3D);
            Scanner readRoute3D = new Scanner(new File(dir + "output.txt"));
            readRoute3D.nextInt();
            double h = config.getAltRelMission();  
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
            StandardPrints.printMsgWarning("Warning [FileNotFoundException] copyRoute3D()");
            return false;
        } 
    }
    
    public boolean parseRoute3DtoGeo(){
        try {
            String nameFileRoute3D =  "output.txt";
            String nameFileRouteGeo = "routeGeo.txt";
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            int countLines = 0;
            readRoute3D.nextInt();
            double h = config.getAltRelMission();            
            while(readRoute3D.hasNext()){
                double x = readRoute3D.nextDouble();
                double y = readRoute3D.nextDouble();
                readRoute3D.nextInt();
                printGeo.println(UtilGeo.parseToGeo(pointGeo, x, y, h, ";"));
                mission3D.addPosition3D(new Position3D(x, y, h));
                missionGeo.addWaypoint(new Waypoint(UtilGeo.parseToGeo1(pointGeo, x, y, h)));
                countLines++;
            }
            if (countLines == 0){
                StandardPrints.printMsgWarning("Route-Empty");
                if (!drone.getSensors().getStatusUAV().armed){
                    System.exit(1);
                }
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
        UtilIO.deleteFile(new File(dir), ".err");
        new File(dir + "log_error.txt").delete(); 
    }

}
