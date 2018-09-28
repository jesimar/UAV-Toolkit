package uav.gcs.planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import lib.color.StandardPrints;
import uav.gcs.struct.Drone;
import uav.generic.struct.geom.Position3D;
import uav.generic.struct.Waypoint;
import uav.generic.util.UtilGeo;
import uav.generic.util.UtilIO;
import uav.generic.reader.ReaderFileMission;
import uav.generic.struct.geom.PointGeo;

/**
 * Classe que modela o planejador de rotas AStar4m. 
 * @author Jesimar S. Arantes
 */
public class AStar4m extends Planner{
    
    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param fileWaypointsMission
     * @param dirFiles
     * @param fileGeoBase
     * @param dirPlanner
     * @param cmdExecPlanner
     * @param altitudeFlight
     */
    public AStar4m(Drone drone, String fileWaypointsMission, String dirFiles, 
            String fileGeoBase, String dirPlanner, String cmdExecPlanner, 
            String altitudeFlight) {
        super(drone, fileWaypointsMission, dirFiles, fileGeoBase, dirPlanner, 
                cmdExecPlanner, altitudeFlight);
        readMission3D();
    }   
    
    private void readMission3D(){
        try {
            String path = dir + fileWaypointsMission;
            ReaderFileMission.mission3D(new File(path), waypointsMission);
        } catch (FileNotFoundException ex) {
            System.err.println("Warning [FileNotFoundException] readMission()");
            ex.printStackTrace();
        }
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
            double h = Double.parseDouble(altitudeFlight);
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
            PointGeo pGeoBase = UtilGeo.getPointGeo(dirFiles + fileGeoBase);
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            int countLines = 0;
            double h = Double.parseDouble(altitudeFlight);          
            while(readRoute3D.hasNext()){
                String line = readRoute3D.next();
                String str[] = line.split(";");
                double x = Double.parseDouble(str[0]);
                double y = Double.parseDouble(str[1]);
                printGeo.println(UtilGeo.parseToGeo(pGeoBase, x, y, h, ";"));
                mission3D.addPosition3D(new Position3D(x, y, h));
                missionGeo.addWaypoint(new Waypoint(UtilGeo.parseToGeo1(pGeoBase, x, y, h)));
                countLines++;
            }
            if (countLines == 0){
                StandardPrints.printMsgWarning("Route-Empty");
                if (!drone.statusUAV.armed){
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
