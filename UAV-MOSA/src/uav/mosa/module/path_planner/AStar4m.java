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
 * Classe que modela o planejador de rotas AStar4m. 
 * @author Jesimar S. Arantes
 */
public class AStar4m extends Planner{
    
    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param waypointsMission waypoints of the mission
     */
    public AStar4m(Drone drone, Mission3D waypointsMission) {
        super(drone, waypointsMission);
    }   
    
    public boolean execMission(int i) {
        boolean itIsOkpathAB = definePathAB(i); 
        boolean itIsOkExec   = execMethod();
        boolean itIsOkCopy   = copyRoute3D(i);
        boolean itIsOkParse  = parseRoute3DtoGeo(i);
        return itIsOkpathAB && itIsOkExec && itIsOkCopy && itIsOkParse;
    }
    
    private boolean definePathAB(int i) {
        try {
            Position3D p1 = waypointsMission.getPosition3D(i);
            Position3D p2 = waypointsMission.getPosition3D(i+1); 
            PrintStream print = new PrintStream(new File(dir + "goals.txt"));
            print.println("#position start");
            print.println(p1.getX() + ";" + p1.getY());
            print.println("#position goal");
            print.println(p2.getX() + ";" + p2.getY());
            print.close();
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: definePathAB()");
            return false;
        } 
    }
    
    public boolean copyRoute3D(int i){
        try {
            File fileRoute3D = new File(dir + "route3D" + i + ".txt");
            PrintStream print3D = new PrintStream(fileRoute3D);
            Scanner readRoute3D = new Scanner(new File(dir + "output.txt"));
            double h = config.getAltRelMission();  
            while(readRoute3D.hasNext()){
                String line = readRoute3D.next();
                String str[] = line.split(";");
                double x = Double.parseDouble(str[0]);
                double y = Double.parseDouble(str[1]);
                print3D.println(x + ";" + y + ";" + h);
            }
            readRoute3D.close();
            print3D.close();
            new File(dir + "output.txt").delete(); 
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException] copyRoute3D()");
            return false;
        } 
    }
    
    public boolean parseRoute3DtoGeo(int i){ 
        try {
            String nameFileRoute3D =  "route3D" + i + ".txt";
            String nameFileRouteGeo = "routeGeo" + i + ".txt";
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            int countLines = 0;
            double h = config.getAltRelMission();            
            while(readRoute3D.hasNext()){
                String line = readRoute3D.next();
                String str[] = line.split(";");
                double x = Double.parseDouble(str[0]);
                double y = Double.parseDouble(str[1]);
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
