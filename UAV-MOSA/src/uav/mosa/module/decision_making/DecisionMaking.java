package uav.mosa.module.decision_making;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import lib.color.StandardPrints;
import uav.generic.module.data_communication.DataCommunication;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.hardware.aircraft.FixedWing;
import uav.generic.struct.constants.TypeWaypoint;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.mission.Mission3D;
import uav.generic.struct.Waypoint;
import uav.generic.struct.constants.LocalCalcMission;
import uav.generic.struct.constants.TypeSystemExecMOSA;
import uav.generic.struct.constants.TypePlanner;
import uav.generic.struct.reader.ReaderFileConfigGlobal;
import uav.generic.struct.states.StatePlanning;
import uav.generic.util.UtilString;
import uav.mosa.module.communication_control.CommunicationGCS;
import uav.mosa.module.path_planner.CCQSP4m;
import uav.mosa.module.path_planner.HGA4m;
import uav.mosa.struct.ReaderFileConfigMOSA;
import uav.mosa.module.path_planner.Planner;

/**
 * Classe que faz as tomadas de decisão do sistema MOSA.
 * @author Jesimar S. Arantes
 */
public class DecisionMaking {

    private final Drone drone;
    private final DataCommunication dataAcquisition;
    private final ReaderFileConfigMOSA config;
    private final ReaderFileConfigGlobal configGlobal;
    private final Mission3D wptsMission3D;
    private Planner planner;
    private StatePlanning statePlanning;    
    
    private final int TIME_TO_SLEEP_NEXT_FIXED_ROUTE = 20000;//in milliseconds
    
    /**
     * Class constructor.
     * @param drone instance of the aircraft
     * @param dataAcquisition object to send commands to drone
     * @param wptsMission3D waypoints of the mission 3D
     */
    public DecisionMaking(Drone drone, DataCommunication dataAcquisition, 
            Mission3D wptsMission3D) {
        this.config = ReaderFileConfigMOSA.getInstance();
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;       
        this.wptsMission3D = wptsMission3D;
        this.statePlanning = StatePlanning.WAITING;       
    }
    
    public void actionToDoSomething() {
        statePlanning = StatePlanning.PLANNING;
        
        if (config.getSystemExec().equals(TypeSystemExecMOSA.PLANNER)){
            if (config.getTypePlanner().equals(TypePlanner.HGA4M)){
                boolean respM = false;
                if (config.getMissionProcessingLocationHGA4m().equals(LocalCalcMission.GROUND)){
                    respM = sendMissionsToDroneCalcGround();
                }else if (config.getMissionProcessingLocationHGA4m().equals(LocalCalcMission.GROUND_AND_AIR)) {
                    respM = sendMissionsToDroneCalcGroundAndAir();
                }else if (config.getMissionProcessingLocationHGA4m().equals(LocalCalcMission.AIR)) {
                    respM = sendMissionsToDroneCalcAir();
                }
                if (respM){
                    statePlanning = StatePlanning.READY;
                    StandardPrints.printMsgEmph("send mission to drone with success");
                }else {
                    statePlanning = StatePlanning.DISABLED;
                    StandardPrints.printMsgWarning("send mission to drone failure");
                }
            }else if (config.getTypePlanner().equals(TypePlanner.CCQSP4M)){
                boolean respM = sendMissionsToDroneCalcGroundCCQSP4m();
                if (respM){
                    statePlanning = StatePlanning.READY;
                    StandardPrints.printMsgEmph("send mission to drone with success");
                }else {
                    statePlanning = StatePlanning.DISABLED;
                    StandardPrints.printMsgWarning("send mission to drone failure");
                }
            }
        } else if (config.getSystemExec().equals(TypeSystemExecMOSA.FIXED_ROUTE)){
            boolean respF = sendFixedMissionToDrone();
            if (respF){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send fixed mission with success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgWarning("send fixed mission to drone failure");
            }
        } 
    }   
    
    public void actionToDoSomethingOffboard(CommunicationGCS communicationGSC){
        statePlanning = StatePlanning.PLANNING;        
        if (config.getSystemExec().equals(TypeSystemExecMOSA.PLANNER)){
            boolean respM = false;
            if (config.getMissionProcessingLocationHGA4m().equals(LocalCalcMission.GROUND)){
                respM = sendMissionsToDroneCalcGroundOffboard(communicationGSC);
            }else if (config.getMissionProcessingLocationHGA4m().equals(LocalCalcMission.GROUND_AND_AIR)) {
                respM = sendMissionsToDroneCalcGroundAndAir();
            }else if (config.getMissionProcessingLocationHGA4m().equals(LocalCalcMission.AIR)) {
                respM = sendMissionsToDroneCalcAir();
            }
            if (respM){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send mission to drone with success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgWarning("send mission to drone failure");
            }
        }
    }
    
    private boolean sendMissionsToDroneCalcGround() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc ground");
        if (config.getTypePlanner().equals(TypePlanner.HGA4M)){
            planner = new HGA4m(drone, wptsMission3D);
        }
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean respMission = ((HGA4m)(planner)).execMission(nRoute);
            if (!respMission){
                return false;
            }
            statePlanning = StatePlanning.READY;
            nRoute++;
            if (nRoute < wptsMission3D.size() - 1){
                statePlanning = StatePlanning.WAITING;
            }
            long timeFinal2 = System.currentTimeMillis();
            long time1 = timeFinal2 - timeInit2;
            StandardPrints.printMsgEmph("Time in Route (ms): " + time1);
        }
        
        Mission mission = new Mission();
        nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1){
            String path = config.getDirPlanner() + "routeGeo" + nRoute + ".txt";                
            boolean respFile = readFileRoute(mission, path, nRoute);
            if (!respFile){
                return false;
            }
            nRoute++;
        }
        mission.printMission();
        if (mission.getMission().size() > 0){
            dataAcquisition.setMission(mission);
        }
        
        long timeFinal1 = System.currentTimeMillis();
        long time1 = timeFinal1 - timeInit1;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time1);
        return true;
    }
    
    private boolean sendMissionsToDroneCalcAir() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc air");
        if (config.getTypePlanner().equals(TypePlanner.HGA4M)){
            planner = new HGA4m(drone, wptsMission3D);
        }
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean respMission = ((HGA4m)(planner)).execMission(nRoute);
            if (!respMission){
                return false;
            }
            
            String path = config.getDirPlanner() + "routeGeo" + nRoute + ".txt";
            Mission mission = new Mission();
            boolean respFile = readFileRoute(mission, path, nRoute);
            if (!respFile){
                return false;
            }
            mission.printMission();
            if (mission.getMission().size() > 0){
                if (nRoute == 0){
                    dataAcquisition.setMission(mission);
                }else{
                    dataAcquisition.appendMission(mission);
                }
            }
            
            statePlanning = StatePlanning.READY;
            nRoute++;
            if (nRoute < wptsMission3D.size() - 1){
                statePlanning = StatePlanning.WAITING;
            }
            long timeFinal2 = System.currentTimeMillis();
            long time1 = timeFinal2 - timeInit2;
            StandardPrints.printMsgEmph("Time in Route (ms): " + time1);
        }
        long timeFinal1 = System.currentTimeMillis();
        long time1 = timeFinal1 - timeInit1;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time1);
        return true;
    }
    
    private boolean sendMissionsToDroneCalcGroundAndAir() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc ground and air");
        if (config.getTypePlanner().equals(TypePlanner.HGA4M)){
            planner = new HGA4m(drone, wptsMission3D);
        }
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean respMission = ((HGA4m)(planner)).execMission(nRoute);
            if (!respMission){
                return false;
            }
            
            if (nRoute == 1){
                Mission mission = new Mission();
                String path1 = config.getDirPlanner() + "routeGeo0.txt";
                boolean respFile1 = readFileRoute(mission, path1, 0);
                if (!respFile1){
                    return false;
                }
                String path2 = config.getDirPlanner() + "routeGeo1.txt";
                boolean respFile2 = readFileRoute(mission, path2, 1);
                if (!respFile2){
                    return false;
                }
                mission.printMission();
                if (mission.getMission().size() > 0){
                    dataAcquisition.setMission(mission);                    
                }
            }else if (nRoute > 1){
                String path = config.getDirPlanner() + "routeGeo" + nRoute + ".txt";
                Mission mission = new Mission();
                boolean respFile = readFileRoute(mission, path, nRoute);
                if (!respFile){
                    return false;
                }
                mission.printMission();
                if (mission.getMission().size() > 0){
                    dataAcquisition.appendMission(mission);
                }
            }
            
            statePlanning = StatePlanning.READY;
            nRoute++;
            if (nRoute < wptsMission3D.size() - 1){
                statePlanning = StatePlanning.WAITING;
            }
            long timeFinal2 = System.currentTimeMillis();
            long time1 = timeFinal2 - timeInit2;
            StandardPrints.printMsgEmph("Time in Route (ms): " + time1);
        }
        long timeFinal1 = System.currentTimeMillis();
        long time1 = timeFinal1 - timeInit1;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time1);
        return true;
    }
    
    private boolean sendMissionsToDroneCalcGroundCCQSP4m() {
        long timeInit = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc ground ccqsp4m");
        planner = new CCQSP4m(drone, wptsMission3D);
        planner.clearLogs();
        statePlanning = StatePlanning.WAITING;
        
        statePlanning = StatePlanning.PLANNING;
        boolean respMission = ((CCQSP4m)(planner)).execMission();
        if (!respMission){
            return false;
        }
        statePlanning = StatePlanning.READY;
        
        Mission mission = new Mission();
        String path = config.getDirPlanner() + "routeGeo.txt";                
        boolean respFile = readFileRoute(mission, path);
        if (!respFile){
            return false;
        }
        mission.printMission();
        if (mission.getMission().size() > 0){
            dataAcquisition.setMission(mission);
        }
        long timeFinal = System.currentTimeMillis();
        long time = timeFinal - timeInit;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time);
        return true;
    }
    
    private boolean sendFixedMissionToDrone() {
        StandardPrints.printMsgEmph("send fixed route");                
        String path = config.getDirFixedRoute() + config.getFileFixedRoute();
        Mission mission1 = new Mission();
        boolean respM1 = readFileRoute(mission1, path, -2);
        if (!respM1){            
            return false;
        }
        mission1.printMission();
        if (mission1.getMission().size() > 0){
            dataAcquisition.setMission(mission1);
        }
        if (config.isDynamicFixedRoute()){
            try {
                Thread.sleep(TIME_TO_SLEEP_NEXT_FIXED_ROUTE);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            String pathDyn = config.getDirFixedRoute()+ config.getFileFixedRouteDyn();
            Mission mission2 = new Mission();
            boolean respM2 = readFileRoute(mission2, pathDyn, -2);
            if (!respM2){
                return false;
            }
            mission2.printMission();
            if (mission2.getMission().size() > 0){
                dataAcquisition.appendMission(mission2);
            }
        }
        return true;
    }         
    
    private boolean readFileRoute(Mission wps, String path, int nRoute){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            boolean firstTime = true;
            double lat = 0.0;
            double lng = 0.0;
            double alt = 0.0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);
                if (firstTime && (nRoute == 0 || nRoute == -2)){
                    wps.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, alt));
                    firstTime = false;
                }
                wps.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
            }
            if (wps.getMission().size() > 0){
                if (nRoute == wptsMission3D.size() - 2){
                    wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: readFileRoute()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException]: readFileRoute()");
            return false;
        }
    }     
    
    private boolean readFileRoute(Mission wps, String path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            boolean firstTime = true;
            double lat = 0.0;
            double lng = 0.0;
            double alt = 0.0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);
                if (firstTime){
                    wps.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, alt));
                    firstTime = false;
                }
                wps.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
            }
            if (wps.getMission().size() > 0){
                wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
            }
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: readFileRoute()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException]: readFileRoute()");
            return false;
        }
    }
    
    public StatePlanning getStatePlanning() {
        return statePlanning;
    }
    
    private boolean sendMissionsToDroneCalcGroundOffboard(CommunicationGCS communicationGCS) {
        String typeAircraft = drone instanceof FixedWing ? "FixedWing" : "RotaryWing";
        String attributes = config.getFileWaypointsMissionHGA4m()  + ";" + wptsMission3D.size()
                + ";" + configGlobal.getDirFiles() + ";" + configGlobal.getFileGeoBase()
                + ";" + config.getDirPlanner() + ";" + config.getCmdExecPlanner() 
                + ";" + configGlobal.getOperationMode() + ";" + configGlobal.getAltRelMission()
                + ";" + config.getTimeExecHGA4m() + ";" + config.getDeltaHGA4m() 
                + ";" + config.getMaxVelocityHGA4m() + ";" + config.getMaxControlHGA4m() 
                + ";" + drone.getSpeedCruize() + ";" + typeAircraft;
        communicationGCS.sendDataPlannerInGCS(attributes);
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
        } while (!communicationGCS.hasReceiveRouteGCS());
        String msgRoute = communicationGCS.getRoutePlannerGCS();
        if (msgRoute.equals("failure")) {
            System.out.println("Route GCS [Failure]: " + msgRoute);
            return false;
        } else {
            dataAcquisition.setMission(msgRoute);
            return true;
        }
    }
}
