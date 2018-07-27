package uav.mosa.module.path_planner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import lib.color.StandardPrints;
import uav.generic.struct.mission.Mission3D;
import uav.generic.struct.geom.PointGeo;
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
        boolean itIsOkParse  = parseRoute3DtoGeo();
        return itIsOkUpdate && itIsOkExec && itIsOkParse;
    }
    
    public boolean updateFileConfig() {
        try {
            File src_instance = new File(dir + "instance-base");
            File dst_instance = new File(dir + "instance");
            String delta = configLocal.getDeltaCCQSP4m();
            String qtdWpt = configLocal.getWaypointsCCQSP4m();
            UtilIO.copyFileModifiedMOSA(src_instance, dst_instance, 
                    delta, 189, qtdWpt, 298, qtdWpt, 299);
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: updateFileConfig()");
            return false;
        }
    }
    
    public boolean parseRoute3DtoGeo(){
        try {
            String nameFileRoute3D =  "output.txt";
            String nameFileRouteGeo = "routeGeo.txt";
            PointGeo pGeoBase = UtilGeo.getPointGeo(configGlobal.getDirFiles() + 
                    configGlobal.getFileGeoBase());
            File fileRouteGeo = new File(dir + nameFileRouteGeo);
            PrintStream printGeo = new PrintStream(fileRouteGeo);
            Scanner readRoute3D = new Scanner(new File(dir + nameFileRoute3D));
            int countLines = 0;
            readRoute3D.nextInt();
            while(readRoute3D.hasNext()){
                double x = readRoute3D.nextDouble();
                double y = readRoute3D.nextDouble();
                readRoute3D.nextInt();
                double h = configGlobal.getAltRelMission();            
                printGeo.println(UtilGeo.parseToGeo(pGeoBase, x, y, h, ";"));
                mission3D.addPosition3D(new Position3D(x, y, h));
                missionGeo.addWaypoint(new Waypoint(UtilGeo.parseToGeo1(pGeoBase, x, y, h)));
                countLines++;
            }
            if (countLines == 0){
                StandardPrints.printMsgWarning("Route-Empty");
                if (!drone.getStatusUAV().armed){
                    System.exit(0);
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
