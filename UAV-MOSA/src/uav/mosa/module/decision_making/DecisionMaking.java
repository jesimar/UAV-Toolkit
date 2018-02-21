package uav.mosa.module.decision_making;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_acquisition.DataAcquisition;
import uav.generic.struct.Angle;
import uav.generic.struct.Command;
import uav.generic.struct.Constants;
import uav.generic.struct.Direction;
import uav.generic.struct.Heading;
import uav.generic.struct.HeadingJSON;
import uav.generic.struct.Mission;
import uav.generic.struct.Mission3D;
import uav.generic.struct.Waypoint;
import uav.generic.struct.WaypointJSON;
import uav.generic.util.UtilString;
import uav.hardware.aircraft.Drone;
import uav.mosa.struct.InputCommand;
import uav.mosa.module.path_planner.HGA4m;
import uav.mosa.struct.ReaderFileConfig;
import uav.mosa.module.path_planner.Planner;
import uav.mosa.struct.states.StatePlanning;
import uav.mosa.struct.ReaderMission;

/**
 *
 * @author jesimar
 */
public class DecisionMaking {

    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final ReaderFileConfig config;
    private final Mission3D waypointsMission3D;
    private Planner planner;
    private StatePlanning statePlanning;    
    
    private final int SLEEP_TIME_CALC_NEW_ROUTE = 1000;//in milliseconds
    private final int SLEEP_TIME_NEXT_ROUTE = 20000;//in milliseconds
    private final double FACTOR_DESLC = Constants.FACTOR_DESLC;
    private final double ONE_METER = Constants.ONE_METER;
    private final double MAX_ALT = Constants.MAX_ALT;
    private final double MIN_ALT = Constants.MIN_ALT;
    
    public DecisionMaking(Drone drone, DataAcquisition dataAcquisition) {
        this.config = ReaderFileConfig.getInstance();
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;       
        this.waypointsMission3D = new Mission3D();
        this.statePlanning = StatePlanning.WAITING;       
    }
    
    public void actionToDoSomething() {
        statePlanning = StatePlanning.PLANNING;
        
        if (config.getSystemExec().equals(Constants.SYS_EXEC_PLANNER)){            
//            boolean respM = sendMissionsToDrone();
            boolean respM = sendMissionsToDroneOriginal();
            if (respM){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send mission to drone with success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgWarning("send mission to drone failure");
            }
        } else if (config.getSystemExec().equals(Constants.SYS_EXEC_FIXED_ROUTE)){
            boolean respF = sendFixedMissionToDrone();
            if (respF){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send fixed mission with success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgWarning("send fixed mission to drone failure");
            }
        } else if (config.getSystemExec().equals(Constants.SYS_EXEC_CONTROLLER)){
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
    
    private boolean sendMissionsToDrone() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone");
        boolean respM = readMission(); 
        if (!respM){
            return false;
        }
        if (config.getMethodPlanner().equals("HGA4m")){
            planner = new HGA4m(drone, waypointsMission3D);
        }
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < waypointsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean respMission = planner.execMission(nRoute);
            if (!respMission){
                return false;
            }
            statePlanning = StatePlanning.READY;
            nRoute++;
            if (nRoute < waypointsMission3D.size() - 1){
                statePlanning = StatePlanning.WAITING;
                try {
                    Thread.sleep(SLEEP_TIME_CALC_NEW_ROUTE);
                } catch (InterruptedException ex) {
                    
                }
            }
            long timeFinal2 = System.currentTimeMillis();
            long time1 = timeFinal2 - timeInit2;
            StandardPrints.printMsgEmph("Time in Route (ms): " + time1);
        }
        
        Mission mission = new Mission();
        nRoute = 0;
        while (nRoute < waypointsMission3D.size() - 1){
            String path = config.getDirPlanner() + "routeGeo" + nRoute + ".txt";                
            boolean respFile = readFileRoute(mission, path, nRoute);
            if (!respFile){
                return false;
            }
            nRoute++;
        }
        mission.printMission();
        dataAcquisition.setMission(mission);
        
//        planner.getMission3D().printMission();
//        planner.getMissionGeo().printMission();
        
        long timeFinal1 = System.currentTimeMillis();
        long time1 = timeFinal1 - timeInit1;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time1);
        return true;
    }
    
        
    private boolean sendMissionsToDroneOriginal() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone");
        boolean respM = readMission(); 
        if (!respM){
            return false;
        }
        if (config.getMethodPlanner().equals("HGA4m")){
            planner = new HGA4m(drone, waypointsMission3D);
        }
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < waypointsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
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
            if (nRoute == 0){
                dataAcquisition.setMission(mission);
            }else{
                dataAcquisition.appendMission(mission);
            }
            
            statePlanning = StatePlanning.READY;
            nRoute++;
            if (nRoute < waypointsMission3D.size() - 1){
                statePlanning = StatePlanning.WAITING;
                try {
                    Thread.sleep(SLEEP_TIME_CALC_NEW_ROUTE);
                } catch (InterruptedException ex) {
                    
                }
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
        dataAcquisition.setMission(mission1);
        if (config.isDynamicFixedRoute()){
            try {
                Thread.sleep(SLEEP_TIME_NEXT_ROUTE);
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
            dataAcquisition.appendMission(mission2);
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
                    wps.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, alt));
                    firstTime = false;
                }
                wps.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, lat, lng, alt));
            }   
            if (nRoute == waypointsMission3D.size() - 2){
                if (config.getActionAfterFinishMission().equals(Constants.CMD_LAND)){
                    wps.addWaypoint(new Waypoint(Command.CMD_LAND, lat, lng, 0.0));
                }else if (config.getActionAfterFinishMission().equals(Constants.CMD_RTL)){
                    wps.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));
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
            ReaderMission read = new ReaderMission(waypointsMission3D);
            String path = config.getDirPlanner() + config.getFileWaypointsMission();
            read.reader(new File(path));
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
            if (cmd.contains(InputCommand.CMD_TAKEOFF)){
                Waypoint wpt = new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0);
                WaypointJSON wptJson= new WaypointJSON(wpt);
                dataAcquisition.setWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_LAND)){               
//                Waypoint wpt = new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0);
//                WaypointJSON wptJson = new WaypointJSON(wpt);
//                dataAcquisition.appendWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_QUIT)){               
                Waypoint wpt = new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0);
                WaypointJSON wptJson = new WaypointJSON(wpt);
                dataAcquisition.appendWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_UP)){
                double lat = drone.getGPS().lat;
                double lng = drone.getGPS().lng;
                double newAltRel = drone.getGPS().alt_rel + 1;
                if (newAltRel > MAX_ALT){
                    newAltRel = MAX_ALT;
                }
                Waypoint wpt = new Waypoint(Command.CMD_WAYPOINT, lat, lng, newAltRel);
                WaypointJSON wptJson = new WaypointJSON(wpt);
                dataAcquisition.setWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_DOWN)){
                double lat = drone.getGPS().lat;
                double lng = drone.getGPS().lng;
                double newAltRel = drone.getGPS().alt_rel - 1;
                if (newAltRel < MIN_ALT){
                    newAltRel = MIN_ALT;
                }
                Waypoint wpt = new Waypoint(Command.CMD_WAYPOINT, lat, lng, newAltRel);
                WaypointJSON wptJson = new WaypointJSON(wpt);
                dataAcquisition.setWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_LEFT)){
                double lat = drone.getGPS().lat;
                double newLon = drone.getGPS().lng - FACTOR_DESLC * ONE_METER;
                double alt_rel = drone.getGPS().alt_rel;
                Waypoint wpt = new Waypoint(Command.CMD_WAYPOINT, lat, newLon, alt_rel);
                WaypointJSON wptJson = new WaypointJSON(wpt);
                dataAcquisition.setWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_RIGHT)){
                double lat = drone.getGPS().lat;
                double newLon = drone.getGPS().lng + FACTOR_DESLC * ONE_METER;               
                double alt_rel = drone.getGPS().alt_rel;
                Waypoint wpt = new Waypoint(Command.CMD_WAYPOINT, lat, newLon, alt_rel);
                WaypointJSON wptJson = new WaypointJSON(wpt);
                dataAcquisition.setWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_FORWARD)){
                double newLat = drone.getGPS().lat + FACTOR_DESLC * ONE_METER;
                double lng = drone.getGPS().lng;
                double alt_rel = drone.getGPS().alt_rel;
                Waypoint wpt = new Waypoint(Command.CMD_WAYPOINT, newLat, lng, alt_rel);
                WaypointJSON wptJson = new WaypointJSON(wpt);
                dataAcquisition.setWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_BACK)){
                double newLat = drone.getGPS().lat - FACTOR_DESLC * ONE_METER;
                double lng = drone.getGPS().lng;
                double alt_rel = drone.getGPS().alt_rel;
                Waypoint wpt = new Waypoint(Command.CMD_WAYPOINT, newLat, lng, alt_rel);
                WaypointJSON wptJson = new WaypointJSON(wpt);
                dataAcquisition.setWaypoint(wptJson);
            }
            if (cmd.contains(InputCommand.CMD_ROTATE)){
                for (int i = 0; i < 4; i++){
                    Heading heading = new Heading(20, Direction.CW, Angle.RELATIVE);
                    HeadingJSON headingJson = new HeadingJSON(heading);
                    dataAcquisition.setHeading(headingJson);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        
                    }
                }
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
