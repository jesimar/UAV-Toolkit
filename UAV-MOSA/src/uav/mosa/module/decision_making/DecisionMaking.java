package uav.mosa.module.decision_making;

import java.io.File;
import java.io.IOException;
import lib.color.StandardPrints;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.hardware.aircraft.DroneFixedWing;
import uav.generic.module.comm.DataAcquisition;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.mission.Mission3D;
import uav.generic.struct.constants.LocalExecMission;
import uav.generic.struct.constants.TypeBehavior;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.constants.TypeSystemExecMOSA;
import uav.generic.struct.constants.TypePlanner;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.util.UtilRoute;
import uav.generic.struct.states.StatePlanning;
import uav.generic.util.UtilRunThread;
import uav.mosa.module.communication_control.CommunicationGCS;
import uav.mosa.module.path_planner.AStar4m;
import uav.mosa.module.path_planner.CCQSP4m;
import uav.mosa.module.path_planner.HGA4m;
import uav.mosa.module.path_planner.Planner;

/**
 * Classe que faz as tomadas de decisão do sistema MOSA.
 * @author Jesimar S. Arantes
 */
public class DecisionMaking {

    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final ReaderFileConfig config;
    private final Mission3D wptsMission3D;
    private Planner planner;
    private StatePlanning statePlanning;
    
    /**
     * Class constructor.
     * @param drone instance of the aircraft
     * @param dataAcquisition object to send commands to drone
     * @param wptsMission3D waypoints of the mission 3D
     */
    public DecisionMaking(Drone drone, DataAcquisition dataAcquisition, 
            Mission3D wptsMission3D) {        
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;       
        this.wptsMission3D = wptsMission3D;
        this.statePlanning = StatePlanning.WAITING;       
        this.config = ReaderFileConfig.getInstance();
    }
    
    public void actionForMissionOnboard() {
        statePlanning = StatePlanning.PLANNING;
        boolean resp = false;
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.FIXED_ROUTE)){
            resp = sendMissionBasedFixedRoute();
            if (resp){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send fixed route -> success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgEmph("send fixed route -> failure");
            }
        } else if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)){
            if (config.getTypePlanner().equals(TypePlanner.HGA4M)){
                if (config.getLocalExecProcessingPlannerHGA4m().equals(LocalExecMission.GROUND)){
                    resp = sendMissionBasedPlannerHGA4mCalcGroundOnboard();
                }else if (config.getLocalExecProcessingPlannerHGA4m().equals(LocalExecMission.GROUND_AND_AIR)) {
                    resp = sendMissionBasedPlannerHGA4mCalcGroundAndAirOnboard();
                }else if (config.getLocalExecProcessingPlannerHGA4m().equals(LocalExecMission.AIR)) {
                    resp = sendMissionBasedPlannerHGA4mCalcAirOnboard();
                }
            }else if (config.getTypePlanner().equals(TypePlanner.CCQSP4M)){
                resp = sendMissionBasedPlannerCCQSP4mOnboard();
            }else if (config.getTypePlanner().equals(TypePlanner.A_STAR4M)){
                resp = sendMissionBasedPlannerAStar4mOnboard();
            }
            if (resp){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send mission based planner - success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgWarning("send mission based planner - failure");
            }
        }
    }   
    
    public void actionForMissionOffboard(CommunicationGCS communicationGSC){
        statePlanning = StatePlanning.PLANNING; 
        boolean resp = false;
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.FIXED_ROUTE)){
            resp = sendMissionBasedFixedRoute();
            if (resp){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send fixed route -> success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgEmph("send fixed route -> failure");
            }
        } else if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)){
            if (config.getTypePlanner().equals(TypePlanner.HGA4M)){
                if (config.getLocalExecProcessingPlannerHGA4m().equals(LocalExecMission.GROUND)){
                    resp = sendMissionBasedPlannerHGA4mCalcGroundOffboard(communicationGSC);
                }else if (config.getLocalExecProcessingPlannerHGA4m().equals(LocalExecMission.GROUND_AND_AIR)) {
                    resp = sendMissionBasedPlannerHGA4mCalcGroundAndAirOnboard();
                }else if (config.getLocalExecProcessingPlannerHGA4m().equals(LocalExecMission.AIR)) {
                    resp = sendMissionBasedPlannerHGA4mCalcAirOnboard();
                }
            } else if (config.getTypePlanner().equals(TypePlanner.CCQSP4M)){
                resp = sendMissionBasedPlannerCCQSP4mOffboard(communicationGSC);                
            }else if (config.getTypePlanner().equals(TypePlanner.A_STAR4M)){
                resp = sendMissionBasedPlannerAStar4mOffboard(communicationGSC);
            }
            if (resp){
                statePlanning = StatePlanning.READY;
                StandardPrints.printMsgEmph("send mission based planner - success");
            }else {
                statePlanning = StatePlanning.DISABLED;
                StandardPrints.printMsgWarning("send mission based planner - failure");
            }
        }
    }
    
    public void actionChangeBehavior(String type){    
        String disc = config.getDiscretizationBehavior();
        String cmd = "";
        if (type.equals(TypeBehavior.CIRCLE)){
            String dist = config.getRadiusCircleBehavior();
            cmd = "./RouteStandard4m " + drone.getSensors().getGPS().lat + " " + 
                    drone.getSensors().getGPS().lng + " " + 
                    drone.getSensors().getBarometer().alt_rel + " CIRCLE " + 
                    dist + " " + disc;
        }else if (type.equals(TypeBehavior.TRIANGLE)){
            String dist = config.getBaseTriangleBehavior();
            cmd = "./RouteStandard4m " + drone.getSensors().getGPS().lat + " " + 
                    drone.getSensors().getGPS().lng + " " + 
                    drone.getSensors().getBarometer().alt_rel + " TRIANGLE " + 
                    dist + " " + disc;
        }else if (type.equals(TypeBehavior.RECTANGLE)){
            String dist = config.getBaseRectangleBehavior();
            cmd = "./RouteStandard4m " + drone.getSensors().getGPS().lat + " " + 
                    drone.getSensors().getGPS().lng + " " + 
                    drone.getSensors().getBarometer().alt_rel + " RECTANGLE " + 
                    dist + " " + disc;
        }
        String dir = config.getDirBehavior();
        try {
            boolean isPrint = true;
            UtilRunThread.singleThreadWaitFor(cmd, new File(dir), isPrint);
            Mission mission = new Mission();
            String path = dir + "route-behavior.txt";
            boolean respFile = UtilRoute.readFileRouteMOSA(mission, path);
            if (!respFile){
                return;
            }
//            mission.printMission();
            if (mission.getMission().size() > 0){
                dataAcquisition.setMission(mission);
            }
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] actionChangeBehavior()");
        } catch (InterruptedException ex) {
            StandardPrints.printMsgWarning("Warning [InterruptedException] actionChangeBehavior()");
        } 
    }
    
    public StatePlanning getStatePlanning() {
        return statePlanning;
    }
        
    private boolean sendMissionBasedPlannerHGA4mCalcGroundOnboard() {
        long timeInit = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc ground");
        planner = new HGA4m(drone, wptsMission3D);
        planner.clearLogs();  
        
        boolean resp = false;
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            resp = ((HGA4m)(planner)).execMission(nRoute);
            if (!resp){
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
            if (config.hasRouteSimplifier()){
                UtilRoute.execRouteSimplifier(path, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                path = config.getDirRouteSimplifier() + "output-simplifier.txt";               
            }
            resp = UtilRoute.readFileRouteMOSA(mission, path, nRoute, wptsMission3D.size());
            if (!resp){
                return false;
            }
            nRoute++;
        }
        mission.printMission();
        if (mission.getMission().size() > 0){
            resp = dataAcquisition.setMission(mission);
        }
        
        long timeFinal = System.currentTimeMillis();
        long time = timeFinal - timeInit;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time);
        return resp;
    }
    
    private boolean sendMissionBasedPlannerHGA4mCalcAirOnboard() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc air");
        planner = new HGA4m(drone, wptsMission3D);
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean resp = ((HGA4m)(planner)).execMission(nRoute);
            if (!resp){
                return false;
            }
            String path = config.getDirPlanner() + "routeGeo" + nRoute + ".txt";
            if (config.hasRouteSimplifier()){
                UtilRoute.execRouteSimplifier(path, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                path = config.getDirRouteSimplifier() + "output-simplifier.txt";               
            }
            Mission mission = new Mission();
            resp = UtilRoute.readFileRouteMOSA(mission, path, nRoute, wptsMission3D.size());
            if (!resp){
                return false;
            }
            mission.printMission();
            if (mission.getMission().size() > 0){
                if (nRoute == 0){
                    resp = dataAcquisition.setMission(mission);
                }else{
                    resp = dataAcquisition.appendMission(mission);
                }
            }
            if (!resp){
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
        long timeFinal1 = System.currentTimeMillis();
        long time1 = timeFinal1 - timeInit1;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time1);
        return true;
    }
    
    private boolean sendMissionBasedPlannerHGA4mCalcGroundAndAirOnboard() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc ground and air");
        planner = new HGA4m(drone, wptsMission3D);
        planner.clearLogs();  
        
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            boolean resp = ((HGA4m)(planner)).execMission(nRoute);
            if (!resp){
                return false;
            }
            
            if (nRoute == 1){
                Mission mission = new Mission();
                String path1 = config.getDirPlanner() + "routeGeo0.txt";
                if (config.hasRouteSimplifier()){
                    UtilRoute.execRouteSimplifier(path1, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                    path1 = config.getDirRouteSimplifier() + "output-simplifier.txt";               
                }
                resp = UtilRoute.readFileRouteMOSA(mission, path1, 0, wptsMission3D.size());
                if (!resp){
                    return false;
                }
                String path2 = config.getDirPlanner() + "routeGeo1.txt";
                if (config.hasRouteSimplifier()){
                    UtilRoute.execRouteSimplifier(path2, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                    path2 = config.getDirRouteSimplifier() + "output-simplifier.txt";               
                }
                resp = UtilRoute.readFileRouteMOSA(mission, path2, 1, wptsMission3D.size());
                if (!resp){
                    return false;
                }
                mission.printMission();
                if (mission.getMission().size() > 0){
                    resp = dataAcquisition.setMission(mission);
                }
            }else if (nRoute > 1){
                String path = config.getDirPlanner() + "routeGeo" + nRoute + ".txt";
                if (config.hasRouteSimplifier()){
                    UtilRoute.execRouteSimplifier(path, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                    path = config.getDirRouteSimplifier() + "output-simplifier.txt";               
                }
                Mission mission = new Mission();
                resp = UtilRoute.readFileRouteMOSA(mission, path, nRoute, wptsMission3D.size());
                if (!resp){
                    return false;
                }
                mission.printMission();
                if (mission.getMission().size() > 0){
                    resp = dataAcquisition.appendMission(mission);
                }
            }
            if (!resp){
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
        long timeFinal1 = System.currentTimeMillis();
        long time1 = timeFinal1 - timeInit1;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time1);
        return true;
    }
    
    private boolean sendMissionBasedPlannerCCQSP4mOnboard() {
        long timeInit = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc ground ccqsp4m");
        planner = new CCQSP4m(drone, wptsMission3D);
        planner.clearLogs();
        statePlanning = StatePlanning.WAITING;
        
        statePlanning = StatePlanning.PLANNING;
        boolean resp = ((CCQSP4m)(planner)).execMission();
        if (!resp){
            return false;
        }
        statePlanning = StatePlanning.READY;
        
        Mission mission = new Mission();
        String path = config.getDirPlanner() + "routeGeo.txt";
        if (config.hasRouteSimplifier()){
            UtilRoute.execRouteSimplifier(path, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
            path = config.getDirRouteSimplifier() + "output-simplifier.txt";               
        }
        resp = UtilRoute.readFileRouteMOSA(mission, path);
        if (!resp){
            return false;
        }
        mission.printMission();
        if (mission.getMission().size() > 0){
            resp = dataAcquisition.setMission(mission);
        }
        long timeFinal = System.currentTimeMillis();
        long time = timeFinal - timeInit;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time);
        return resp;
    }
    
    private boolean sendMissionBasedPlannerAStar4mOnboard() {
        long timeInit1 = System.currentTimeMillis();
        StandardPrints.printMsgEmph("send missions to drone calc ground Astar4m");
        planner = new AStar4m(drone, wptsMission3D);
        planner.clearLogs();  
        
        boolean resp = false;
        statePlanning = StatePlanning.WAITING;//Para entar a primeira vez
        int nRoute = 0;
        while (nRoute < wptsMission3D.size() - 1 && statePlanning == StatePlanning.WAITING){
            long timeInit2 = System.currentTimeMillis();
            StandardPrints.printMsgEmph("route: " + nRoute);
            statePlanning = StatePlanning.PLANNING;
            resp = ((AStar4m)(planner)).execMission(nRoute);
            if (!resp){
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
            if (config.hasRouteSimplifier()){
                UtilRoute.execRouteSimplifier(path, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                path = config.getDirRouteSimplifier() + "output-simplifier.txt";               
            }
            resp = UtilRoute.readFileRouteMOSA(mission, path, nRoute, wptsMission3D.size());
            if (!resp){
                return false;
            }
            nRoute++;
        }
        mission.printMission();
        if (mission.getMission().size() > 0){
            resp = dataAcquisition.setMission(mission);
        }
        
        long timeFinal1 = System.currentTimeMillis();
        long time1 = timeFinal1 - timeInit1;
        StandardPrints.printMsgEmph("Time in Missions (ms): " + time1);
        return resp;
    }
    
    private boolean sendMissionBasedPlannerHGA4mCalcGroundOffboard(
            CommunicationGCS communicationGCS) {
        String typeAircraft = drone instanceof DroneFixedWing ? "FixedWing" : "RotaryWing";
        String attributes = config.getTypePlanner() 
                + ";" + config.getFileMissionPlannerHGA4m()  
                + ";" + wptsMission3D.size()
                + ";" + config.getDirFiles() 
                + ";" + config.getFileGeoBase()
                + ";" + config.getDirPlanner() 
                + ";" + config.getCmdExecPlanner() 
                + ";" + config.getAltRelMission() 
                + ";" + config.getTimeExecPlannerHGA4m() 
                + ";" + config.getDeltaPlannerHGA4m() 
                + ";" + config.getMaxVelocityPlannerHGA4m() 
                + ";" + config.getMaxControlPlannerHGA4m() 
                + ";" + drone.getAttributes().getSpeedCruize() 
                + ";" + typeAircraft;
        communicationGCS.sendData(TypeMsgCommunication.MOSA_GCS_PLANNER + attributes);
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
        } while (!communicationGCS.hasReceiveRouteGCS());
        String msgRoute = communicationGCS.getRoutePlannerGCS();
        if (msgRoute.equals(TypeMsgCommunication.UAV_ROUTE_FAILURE)) {
            System.out.println("Route GCS [Failure]: " + msgRoute);
            return false;
        } else {
            boolean resp = dataAcquisition.setMission(msgRoute);
            return resp;
        }
    }
    
    private boolean sendMissionBasedPlannerCCQSP4mOffboard(
            CommunicationGCS communicationGCS) {
        
        long timeInit = System.currentTimeMillis();
        String attributes = config.getTypePlanner() 
                + ";" + config.getDirFiles() 
                + ";" + config.getFileGeoBase()
                + ";" + config.getDirPlanner() 
                + ";" + config.getCmdExecPlanner() 
                + ";" + config.getAltRelMission() 
                + ";" + config.getWaypointsPlannerCCQSP4m()
                + ";" + config.getDeltaPlannerCCQSP4m();
        communicationGCS.sendData(TypeMsgCommunication.MOSA_GCS_PLANNER + attributes);
        do {
            try {
                Thread.sleep(1);//AUMENTAR UM POUCO DEPOIS -> fazendo teste de velocidade de comunicação
            } catch (InterruptedException ex) {

            }
        } while (!communicationGCS.hasReceiveRouteGCS());
        String msgRoute = communicationGCS.getRoutePlannerGCS();
        long timeFinal = System.currentTimeMillis();
        long time = timeFinal - timeInit;
        StandardPrints.printMsgBlue("Time in Planner :> (ms): " + time);
        if (msgRoute.equals(TypeMsgCommunication.UAV_ROUTE_FAILURE)) {
            System.out.println("Route GCS [Failure]: " + msgRoute);
            return false;
        } else {
            boolean resp = dataAcquisition.setMission(msgRoute);
            return resp;
        }
    }
    
    private boolean sendMissionBasedPlannerAStar4mOffboard(CommunicationGCS communicationGCS) {
        String attributes = config.getTypePlanner() 
                + ";" + config.getFileMissionPlannerAStar4m()
                + ";" + wptsMission3D.size()
                + ";" + config.getDirFiles() 
                + ";" + config.getFileGeoBase()
                + ";" + config.getDirPlanner() 
                + ";" + config.getCmdExecPlanner() 
                + ";" + config.getAltRelMission();
        communicationGCS.sendData(TypeMsgCommunication.MOSA_GCS_PLANNER + attributes);
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
        } while (!communicationGCS.hasReceiveRouteGCS());
        String msgRoute = communicationGCS.getRoutePlannerGCS();
        if (msgRoute.equals(TypeMsgCommunication.UAV_ROUTE_FAILURE)) {
            System.out.println("Route GCS [Failure]: " + msgRoute);
            return false;
        } else {
            boolean resp = dataAcquisition.setMission(msgRoute);
            return resp;
        }
    }
    
    private boolean sendMissionBasedFixedRoute() {
        StandardPrints.printMsgEmph("send fixed route");                
        String path = config.getDirFixedRouteMOSA()+ config.getFileFixedRouteMOSA();
        Mission mission1 = new Mission();
        boolean resp = UtilRoute.readFileRouteMOSA(mission1, path);
        if (!resp){            
            return false;
        }
        mission1.printMission();
        if (mission1.getMission().size() > 0){
            resp = dataAcquisition.setMission(mission1);
            if (!resp){            
                return false;
            }
        }
        if (config.isDynamicFixedRouteMOSA()){
            try {
                Thread.sleep(Constants.TIME_TO_SLEEP_NEXT_FIXED_ROUTE);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            String pathDyn = config.getDirFixedRouteMOSA()+ config.getFileFixedRouteDynMOSA();
            Mission mission2 = new Mission();
            resp = UtilRoute.readFileRouteMOSA(mission2, pathDyn);
            if (!resp){
                return false;
            }
            mission2.printMission();
            if (mission2.getMission().size() > 0){
                resp = dataAcquisition.appendMission(mission2);
                if (!resp){            
                    return false;
                }
            }
        }
        return true;
    }
    
}
