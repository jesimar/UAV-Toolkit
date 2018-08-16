package uav.gcs.planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import uav.gcs.struct.Drone;
import uav.generic.struct.geom.PointGeo;
import uav.generic.struct.geom.Position3D;
import uav.generic.struct.Waypoint;
import uav.generic.util.UtilGeo;
import uav.generic.util.UtilIO;

/**
 * Classe que modela o planejador de rotas CCQSP4m. 
 * @author Jesimar S. Arantes
 */
public class CCQSP4m extends Planner{ 
    
    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param dirFiles
     * @param fileGeoBase
     * @param dirPlanner
     * @param cmdExecPlanner
     * @param altitudeFlight
     * @param waypoint
     * @param delta
     */ 
    public CCQSP4m(Drone drone, String dirFiles, String fileGeoBase, 
            String dirPlanner, String cmdExecPlanner, String altitudeFlight, 
            String waypoint, String delta) {
        super(drone, dirFiles, fileGeoBase, dirPlanner, cmdExecPlanner, 
                altitudeFlight, waypoint, delta);
    }
    
    public boolean execMission() {
        boolean itIsOkUpdate = updateFileConfig();     
        boolean itIsOkExec   = execMethod();
        boolean itIsOkParse  = parseRoute3DtoGeo();
        return itIsOkUpdate && itIsOkExec && itIsOkParse;
    }
    
    public boolean updateFileConfig() {
        try {
            File src_instance = new File(dir + "instance-base");
            File dst_instance = new File(dir + "instance");
            UtilIO.copyFileModifiedMOSA(src_instance, dst_instance, delta, 189, 
                    waypoint, 298, waypoint, 299);
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException]: updateFileConfig()");
            return false;
        }
    }
    
    public boolean parseRoute3DtoGeo(){
        try {
            String nameFileRoute3D =  "output.txt";
            String nameFileRouteGeo = "routeGeo.txt";
            PointGeo pGeoBase = UtilGeo.getPointGeo(dirFiles + fileGeoBase);
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
                mission3D.addPosition3D(new Position3D(x, y, h));
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
    
    @Override
    public void clearLogs() {
        UtilIO.deleteFile(new File(dir), ".log");
        UtilIO.deleteFile(new File(dir), ".png");
        UtilIO.deleteFile(new File(dir), ".err");
        new File(dir + "log_error.txt").delete(); 
    }

}
