package uav.mosa.module.decision_making;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_communication.DataCommunication;
import uav.generic.struct.constants.TypeAngle;
import uav.generic.struct.constants.TypeWaypoint;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeDirection;
import uav.generic.struct.Heading;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.mission.Mission3D;
import uav.generic.struct.Waypoint;
import uav.generic.util.UtilString;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.module.sensors_actuators.BuzzerControl;
import uav.generic.module.sensors_actuators.CameraControl;
import uav.generic.module.sensors_actuators.ParachuteControl;
import uav.generic.struct.constants.LocalCalcMission;
import uav.generic.struct.constants.TypeActionAfterFinishMission;
import uav.generic.struct.constants.TypeSystemExecMOSA;
import uav.generic.struct.constants.TypeInputCommand;
import uav.mosa.module.path_planner.HGA4m;
import uav.mosa.struct.ReaderFileConfig;
import uav.mosa.module.path_planner.Planner;
import uav.generic.struct.states.StatePlanning;
import uav.mosa.struct.ReaderMission;

/**
 *
 * @author Jesimar S. Arantes
 */
public class DecisionMaking {

    private final Drone drone;
    private final DataCommunication dataAcquisition;
    private final ReaderFileConfig config;
    private final Mission3D wptsMission3D;
    private Planner planner;
    private StatePlanning statePlanning;    
    
    private final int TIME_TO_SLEEP_NEXT_FIXED_ROUTE = 20000;//in milliseconds
    private final double FACTOR_DESLC = Constants.FACTOR_DESLC_CONTROLLER;
    private final double ONE_METER = Constants.ONE_METER;
    
    public DecisionMaking(Drone drone, DataCommunication dataAcquisition) {
        this.config = ReaderFileConfig.getInstance();
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;       
        this.wptsMission3D = new Mission3D();
        this.statePlanning = StatePlanning.WAITING;       
    }
    
    public void actionToDoSomething() {
        statePlanning = StatePlanning.PLANNING;
        
        if (config.getSystemExec().equals(TypeSystemExecMOSA.PLANNER)){
            boolean respM = false;
            if (config.getMissionProcessingLocation().equals(LocalCalcMission.GROUND)){
                respM = sendMissionsToDroneCalcGround();
            }else if (config.getMissionProcessingLocation().equals(LocalCalcMission.GROUND_AND_AIR)) {
                respM = sendMissionsToDroneCalcGroundAndAir();
            }else if (config.getMissionProcessingLocation().equals(LocalCalcMission.AIR)) {
                respM = sendMissionsToDroneCalcAir();
            }
            if (respM){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send mission to drone with success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgWarning("send mission to drone failure");
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
        } else if (config.getSystemExec().equals(TypeSystemExecMOSA.CONTROLLER)){
            boolean itIsOkController = execController();
            if (itIsOkController){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("exec controller with success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgWarning("exec controller of drone failure");
            }
        }
    }   
    
    private boolean sendMissionsToDroneCalcGround() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc ground");
        boolean respM = readMission(); 
        if (!respM){
            return false;
        }
        if (config.getMethodPlanner().equals("HGA4m")){
            planner = new HGA4m(drone, wptsMission3D);
        }
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean respMission = planner.execMission(nRoute);
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
        boolean respM = readMission(); 
        if (!respM){
            return false;
        }
        if (config.getMethodPlanner().equals("HGA4m")){
            planner = new HGA4m(drone, wptsMission3D);
        }
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean respMission = planner.execMission(nRoute);
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
        boolean respM = readMission(); 
        if (!respM){
            return false;
        }
        if (config.getMethodPlanner().equals("HGA4m")){
            planner = new HGA4m(drone, wptsMission3D);
        }
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean respMission = planner.execMission(nRoute);
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
                    if (config.getActionAfterFinishMission().equals(
                            TypeActionAfterFinishMission.CMD_LAND)){
                        wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
                    }else if (config.getActionAfterFinishMission().equals(
                            TypeActionAfterFinishMission.CMD_RTL)){
                        wps.addWaypoint(new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0));
                    }
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
    
    private boolean readMission(){
        try {
            ReaderMission read = new ReaderMission();
            String path = config.getDirPlanner() + config.getFileWaypointsMission();
            read.readerMission(new File(path), wptsMission3D);
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException] readMission()");
            ex.printStackTrace();            
            return false;
        }
    }
    
    public boolean execController() {
        try{
            StandardPrints.printMsgEmph("controller");
            File f = new File(config.getDirController());
            final Process comp = Runtime.getRuntime().exec(config.getCmdExecController(), null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());                
                    while (sc.hasNextLine()) {
                        String cmd = sc.nextLine();
                        interpretCommand(cmd);
                    }
                    sc.close();
                }
            });
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getErrorStream());
                    while (sc.hasNextLine()) {
                        StandardPrints.printMsgError("err:" + sc.nextLine());
                    }
                    sc.close();
                }
            });        
            comp.waitFor();
            return true;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] execController()");
            return false;
        } catch (InterruptedException ex) {
            StandardPrints.printMsgWarning("Warning [InterruptedException] execController()");
            return false;
        }
    }        
    
    private void interpretCommand(String cmd) {
        if (!cmd.contains("CMD: ")){
            StandardPrints.printMsgEmph3(cmd);
        }else if (cmd.contains("CMD: ")){
            StandardPrints.printMsgEmph4(cmd);
            if (cmd.contains(TypeInputCommand.CMD_TAKEOFF)){
                Waypoint wpt = new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, 3.0);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_LAND)){               
                Waypoint wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_QUIT)){               
                Waypoint wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_UP)){
                double lat = drone.getGPS().lat;
                double lng = drone.getGPS().lng;
                double newAltRel = drone.getBarometer().alt_rel + 1;
                if (newAltRel > Constants.MAX_ALT_CONTROLLER){
                    newAltRel = Constants.MAX_ALT_CONTROLLER;
                }
                Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, lat, lng, newAltRel);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_DOWN)){
                double lat = drone.getGPS().lat;
                double lng = drone.getGPS().lng;
                double newAltRel = drone.getBarometer().alt_rel - 1;
                if (newAltRel < Constants.MIN_ALT_CONTROLLER){
                    newAltRel = Constants.MIN_ALT_CONTROLLER;
                }
                Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, lat, lng, newAltRel);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_LEFT)){
                double lat = drone.getGPS().lat;
                double newLon = drone.getGPS().lng - FACTOR_DESLC * ONE_METER;
                double alt_rel = drone.getBarometer().alt_rel;
                Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, lat, newLon, alt_rel);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_RIGHT)){
                double lat = drone.getGPS().lat;
                double newLon = drone.getGPS().lng + FACTOR_DESLC * ONE_METER;               
                double alt_rel = drone.getBarometer().alt_rel;
                Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, lat, newLon, alt_rel);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_FORWARD)){
                double newLat = drone.getGPS().lat + FACTOR_DESLC * ONE_METER;
                double lng = drone.getGPS().lng;
                double alt_rel = drone.getBarometer().alt_rel;
                Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, newLat, lng, alt_rel);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_BACK)){
                double newLat = drone.getGPS().lat - FACTOR_DESLC * ONE_METER;
                double lng = drone.getGPS().lng;
                double alt_rel = drone.getBarometer().alt_rel;
                Waypoint wpt = new Waypoint(TypeWaypoint.GOTO, newLat, lng, alt_rel);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_ROTATE)){
                for (int i = 0; i < 4; i++){
                    Heading heading = new Heading(20, TypeDirection.CW, TypeAngle.RELATIVE);
                    dataAcquisition.setHeading(heading);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        
                    }
                }
            }
            if (cmd.contains(TypeInputCommand.CMD_RTL)){
                Waypoint wpt = new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);
                dataAcquisition.setWaypoint(wpt);
            }
            if (cmd.contains(TypeInputCommand.CMD_BUZZER)){
                BuzzerControl buzzer = new BuzzerControl();
                buzzer.turnOnBuzzer();
            }
            if (cmd.contains(TypeInputCommand.CMD_ALARM)){
                BuzzerControl buzzer = new BuzzerControl();
                buzzer.turnOnAlarm();
            }
            if (cmd.contains(TypeInputCommand.CMD_PICTURE)){
                CameraControl camera = new CameraControl();
                camera.takeAPicture();
            }
            if (cmd.contains(TypeInputCommand.CMD_OPEN_PARACHUTE)){
                ParachuteControl parachute = new ParachuteControl();
                parachute.open();
            }
        }
    }
    
    public StatePlanning getStatePlanning() {
        return statePlanning;
    }
    
    public Planner getPlanner(){
        return planner;
    }
}
