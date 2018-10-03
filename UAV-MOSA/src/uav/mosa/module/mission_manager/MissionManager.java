package uav.mosa.module.mission_manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import lib.uav.hardware.aircraft.Drone;
import lib.uav.hardware.aircraft.DroneFixedWing;
import lib.uav.hardware.aircraft.DroneRotaryWing;
import lib.uav.module.actuators.BuzzerControl;
import lib.uav.module.comm.DataAcquisition;
import lib.uav.module.comm.DataAcquisitionS2DK;
import lib.uav.module.sensors.CameraControl;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.reader.ReaderFileMission;
import lib.uav.struct.constants.Constants;
import lib.uav.struct.constants.LocalExecPlanner;
import lib.uav.struct.constants.TypeAircraft;
import lib.uav.struct.constants.TypeDataAcquisitionUAV;
import lib.uav.struct.constants.TypeMsgCommunication;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.struct.constants.TypePlanner;
import lib.uav.struct.constants.TypeSystemExecMOSA;
import lib.uav.struct.geom.PointGeo;
import lib.uav.struct.mission.Mission;
import lib.uav.struct.mission.Mission3D;
import lib.uav.struct.states.StateCommunication;
import lib.uav.struct.states.StateMonitoring;
import lib.uav.struct.states.StatePlanning;
import lib.uav.struct.states.StateSystem;
import lib.uav.util.UtilFile;
import lib.uav.util.UtilGeo;
import lib.uav.util.UtilGeom;
import uav.mosa.module.communication.CommunicationGCS;
import uav.mosa.module.communication.CommunicationIFA;
import uav.mosa.module.decision_making.DecisionMaking;

/**
 * The class manages the current mission by the aircraft.
 * @author Jesimar S. Arantes
 * @since version 1.0.0
 */
public class MissionManager {
    
    public static PointGeo pointGeo;
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final CommunicationIFA communicationIFA;
    private final CommunicationGCS communicationGCS;
    private final DecisionMaking decisonMaking;
    private final ReaderFileConfig config;
    
    private CameraControl camera;
    private Mission3D wptsMission3D;
    private Mission wptsBuzzer;
    private Mission wptsCameraPicture;
    private Mission wptsCameraVideo;
    private Mission wptsCameraPhotoInSeq;
    private Mission wptsSpraying;
    private PrintStream printLogOverhead;     
    private StateSystem stateMOSA;
    private StateMonitoring stateMonitoring;    
        
    private long timeInit;
    private long timeActual;
    private double horizontalErrorGPS;
    private double verticalErrorBarometer;
        
    /**
     * Class constructor.
     * @since version 1.0.0
     */
    public MissionManager() {
        timeInit = System.currentTimeMillis();
        
        this.config = ReaderFileConfig.getInstance();
        if (!config.read()){
            System.exit(1);
        }
        if (!config.checkReadFields()){
            System.exit(1);
        }
        if (!config.parseToVariables()){
            System.exit(1);
        }
        
        horizontalErrorGPS = config.getHorizontalErrorGPS();
        verticalErrorBarometer = config.getVerticalErrorBarometer();
        
        try{
            pointGeo = UtilGeo.getPointGeoBase(config.getDirFiles()+config.getFileGeoBase());
        }catch (FileNotFoundException ex){
            StandardPrints.printMsgError2("Error [FileNotFoundException] pointGeo");
            ex.printStackTrace();
            System.exit(1);
        }
        
        if (config.getTypeAircraft().equals(TypeAircraft.FIXED_WING)){
            drone = new DroneFixedWing(config.getUavName(), 
                    config.getUavSpeedCruize(), config.getUavSpeedMax(), 
                    config.getUavMass(), config.getUavPayload(), 
                    config.getUavEndurance());
        } else if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
            drone = new DroneRotaryWing(config.getUavName(), 
                    config.getUavSpeedCruize(), config.getUavSpeedMax(), 
                    config.getUavMass(), config.getUavPayload(), 
                    config.getUavEndurance());
        } else{
            drone = new DroneRotaryWing("iDroneAlpha");
        }
        
        printLogOverhead = UtilFile.createFileLog("log-overhead-ifa", ".csv");
        
        if (config.getTypeDataAcquisition().equals(TypeDataAcquisitionUAV.DRONEKIT)){
            this.dataAcquisition = new DataAcquisitionS2DK(drone, "MOSA", 
                    config.getHostS2DK(), config.getPortNetworkS2DK(), printLogOverhead);
        }else{
            dataAcquisition = null;
            System.out.println("Type data acquisition not supported");
            System.exit(1);
        }
        this.wptsMission3D = new Mission3D();
        readMission3D();
        this.decisonMaking = new DecisionMaking(drone, dataAcquisition, wptsMission3D); 
        this.communicationIFA = new CommunicationIFA(drone);
        this.communicationGCS = new CommunicationGCS();
        
        if (config.hasBuzzer()){
            this.wptsBuzzer = new Mission();
            readerFileBuzzer();
        }
        if (config.hasCamera()){
            this.camera = new CameraControl();
            this.wptsCameraPicture = new Mission(); 
            this.wptsCameraVideo = new Mission();
            this.wptsCameraPhotoInSeq = new Mission();
            readerFileCameraPhoto();
            readerFileCameraVideo();
            readerFileCameraPhotoInSeq();
        }
        if (config.hasSpraying()){
            this.wptsSpraying = new Mission(); 
            readerFileSpraying();
        }
        stateMOSA = StateSystem.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }
    
    /**
     * Initializes the system
     * @since version 1.0.0
     */
    public void init() {
        StandardPrints.printMsgEmph("initializing ...");    
        
        dataAcquisition.getParameters();
        
        communicationIFA.connectServer();       //blocked        
        communicationIFA.receiveData();         //Thread 
        
        communicationGCS.startServer();         //Thread        
        communicationGCS.receiveData();         //Thread 
                
        monitoringAircraft();                   //Thread
        waitingForAnActionOfMission();          //Thread                            
        monitoringStateMachine();               //Thread
        
        stateMOSA = StateSystem.INITIALIZED;
        try{//Wait to make sure IFA has finished booting
            Thread.sleep(1000);
        }catch (InterruptedException ex){ 
            
        }
        communicationIFA.sendData(TypeMsgCommunication.MOSA_IFA_INITIALIZED);
        StandardPrints.printMsgEmph("initialized ..."); 
        long timeFinal = System.currentTimeMillis();
        long time = timeFinal - timeInit; 
        StandardPrints.printMsgBlue("Time to Initialize MOSA (ms): " + time);
    }
    
    /**
     * Thread that monitors all sensors and aircraft data and triggers the mission 
     * devices (camera, buzzer, spraying).
     * @since version 1.0.0
     * @see DataAcquisition#getAllInfoSensors()
     */
    private void monitoringAircraft() {
        StandardPrints.printMsgEmph("monitoring aircraft");
        int time = (int)(1000.0/config.getFreqUpdateDataAP());      
        stateMonitoring = StateMonitoring.MONITORING;
        
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try { 
                    while(stateMOSA != StateSystem.DISABLED){
                        timeActual = System.currentTimeMillis();
                        double timeDiff = (timeActual - timeInit)/1000.0;
                        drone.getInfo().setTime(timeDiff);
                        dataAcquisition.getAllInfoSensors();
                        if (config.hasPowerModule() || 
                                !config.getOperationMode()
                                .equals(TypeOperationMode.REAL_FLIGHT)){
                            dataAcquisition.getBattery();
                        }
                        if (config.hasBuzzer()){
                            actionTurnOnTheBuzzer();
                        }
                        if (config.hasCamera()){
                            actionTakeAPicture();
                            actionMakeAVideo();
                            actionPhotoInSequence();
                        }
                        
                        if (communicationGCS.isBehaviorChanged()){
                            decisonMaking.actionChangeBehavior(config.getTypeBehavior());
                            communicationGCS.setBehaviorChanged(false);
                        }
                        if (communicationGCS.isBehaviorChangedCircle()){
                            decisonMaking.actionChangeBehavior("CIRCLE");
                            communicationGCS.setBehaviorChangedCircle(false);
                        }
                        if (communicationGCS.isBehaviorChangedTriangle()){
                            decisonMaking.actionChangeBehavior("TRIANGLE");
                            communicationGCS.setBehaviorChangedTriangle(false);
                        }
                        if (communicationGCS.isBehaviorChangedRectangle()){
                            decisonMaking.actionChangeBehavior("RECTANGLE");
                            communicationGCS.setBehaviorChangedRectangle(false);
                        }
                        
                        //Codigo usado no artigo ICAS 2018 para insercao de falha no MOSA
//                        if (drone.getTime() > 80){//Tempos testados 70, 80, 92, 102, 122.
//                            communicationIFA.sendData(TypeMsgCommunication.MOSA_IFA_DISABLED);
//                            stateMOSA = StateSystem.DISABLED;
//                            System.out.println("MOSA Failure");
//                            Thread.sleep(100);
//                            System.exit(0);
//                        }
                        Thread.sleep(time);                    
                    }
                } catch (InterruptedException ex) {
                    StandardPrints.printMsgError2("Error [InterruptedException] monitoringAircraft()");
                    ex.printStackTrace();
                    stateMonitoring = StateMonitoring.DISABLED;
                } catch (Exception ex){
                    StandardPrints.printMsgError2("Error [Exception] monitoringAircraft()");
                    ex.printStackTrace();
                    stateMonitoring = StateMonitoring.DISABLED;
                }
            }
        });
    }   
        
    /**
     * Thread waiting for a mission-related action
     * @since version 4.0.0
     * @see DecisionMaking
     */
    private void waitingForAnActionOfMission() {
        StandardPrints.printMsgEmph("waiting for an action of mission");
        Executors.newSingleThreadExecutor().execute(new Runnable(){            
            @Override
            public void run(){
                while(stateMOSA != StateSystem.DISABLED){
                    try {
                        if (communicationIFA.isStartMission()){
                            long timeInit = System.currentTimeMillis();        
                            if (config.getLocalExecPlanner().equals(LocalExecPlanner.ONBOARD)){
                                decisonMaking.actionForMissionOnboard();
                            }else{
                                decisonMaking.actionForMissionOffboard(communicationGCS);
                            }
                            long timeFinal = System.currentTimeMillis();
                            long time = timeFinal - timeInit;
                            StandardPrints.printMsgBlue("Time for Action Mission (ms): " + time);
                            break;
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_WAITING_FOR_AN_ACTION);
                    } catch (InterruptedException ex) {
                        
                    } 
                }
            }
        });
    }
    
    /**
     * Thread that monitors state machines.
     * @since version 1.0.0
     */
    private void monitoringStateMachine(){
        StandardPrints.printMsgEmph("monitoring the state machine");
        Executors.newSingleThreadExecutor().execute(new Runnable(){            
            @Override
            public void run(){
                try {
                    while(stateMOSA != StateSystem.DISABLED){                    
                        if (communicationIFA.getStateCommunication() == StateCommunication.DISABLED){
                            communicationIFA.sendData(TypeMsgCommunication.MOSA_IFA_DISABLED);
                            stateMOSA = StateSystem.DISABLED;
                            Thread.sleep(100);
                            System.exit(1);
                        }
                        if (decisonMaking.getStatePlanning()== StatePlanning.DISABLED){
                            communicationIFA.sendData(TypeMsgCommunication.MOSA_IFA_DISABLED);
                            stateMOSA = StateSystem.DISABLED;
                            Thread.sleep(100);
                            System.exit(1);
                        }
                        if (stateMonitoring == StateMonitoring.DISABLED){
                            communicationIFA.sendData(TypeMsgCommunication.MOSA_IFA_DISABLED);
                            stateMOSA = StateSystem.DISABLED;
                            Thread.sleep(100);
                            System.exit(1);
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_MONITORING_STATE_MACHINE);                     
                    }
                } catch (InterruptedException ex) {
                    StandardPrints.printMsgError2("Error [InterruptedException] monitoringStateMachine()");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    StandardPrints.printMsgError2("Error [Exception] monitoringStateMachine()");
                    ex.printStackTrace();
                }
            }
        });        
    }
    
    /**
     * Command that triggers the buzzer if the drone arrives at the specified location.
     * Note: Devido a forma como foi feito se tiver dois waypoints no mesmo lugar, 
     * esse codigo nao funciona muito bem, acionando um após o outro não olhando 
     * questões relacionados a ordem dos fatos. Por exemplo, Case-III do artigo IROS.
     * @since version 1.1.0
     */
    private void actionTurnOnTheBuzzer(){   
        double lat = drone.getSensors().getGPS().lat;
        double lng = drone.getSensors().getGPS().lng;
        double alt = drone.getSensors().getBarometer().alt_rel;
        double distH = Integer.MAX_VALUE;
        double distV = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < wptsBuzzer.size(); i++){
            double latDestiny = wptsBuzzer.getWaypoint(i).getLat();
            double lngDestiny = wptsBuzzer.getWaypoint(i).getLng();
            double altDestiny = config.getAltRelMission();            
            double distHActual = UtilGeom.distanceEuclidian(lat, lng, latDestiny, lngDestiny);
            double distVActual = Math.abs(alt - altDestiny); 
            if (distHActual < distH && distVActual < verticalErrorBarometer){
                distH = distHActual;
                distV = distVActual;
                index = i;
            }
        }        
        if (distH < horizontalErrorGPS*Constants.ONE_METER && distV < verticalErrorBarometer){   
            if (wptsBuzzer.size() > 0){
                wptsBuzzer.removeWaypoint(index);
            }
            StandardPrints.printMsgEmph("turn on the buzzer");
            BuzzerControl buzzer = new BuzzerControl();
            buzzer.turnOnBuzzer();
        }
    }
    
    /**
     * Take the action of take a picture
     * @since version 3.0.0
     */
    private void actionTakeAPicture(){
        double lat = drone.getSensors().getGPS().lat;
        double lng = drone.getSensors().getGPS().lng;
        double alt = drone.getSensors().getBarometer().alt_rel;        
        double distH = Integer.MAX_VALUE;
        double distV = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < wptsCameraPicture.size(); i++){
            double latDestiny = wptsCameraPicture.getWaypoint(i).getLat();
            double lngDestiny = wptsCameraPicture.getWaypoint(i).getLng();
            double altDestiny = config.getAltRelMission();            
            double distHActual = UtilGeom.distanceEuclidian(lat, lng, latDestiny, lngDestiny);
            double distVActual = Math.abs(alt - altDestiny); 
            if (distHActual < distH && distVActual < verticalErrorBarometer){
                distH = distHActual;
                distV = distVActual;
                index = i;
            }
        }        
        if (distH < horizontalErrorGPS*Constants.ONE_METER && distV < verticalErrorBarometer){   
            if (wptsCameraPicture.size() > 0){
                wptsCameraPicture.removeWaypoint(index);
            }
            StandardPrints.printMsgEmph("turn on the camera picture");
            camera.takeAPicture();
        }
    }
    
    /**
     * Take the action of make a video
     * @since version 4.0.0
     */
    private void actionMakeAVideo(){
        double lat = drone.getSensors().getGPS().lat;
        double lng = drone.getSensors().getGPS().lng;
        double alt = drone.getSensors().getBarometer().alt_rel;        
        double distH = Integer.MAX_VALUE;
        double distV = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < wptsCameraVideo.size(); i++){
            double latDestiny = wptsCameraVideo.getWaypoint(i).getLat();
            double lngDestiny = wptsCameraVideo.getWaypoint(i).getLng();
            double altDestiny = config.getAltRelMission();            
            double distHActual = UtilGeom.distanceEuclidian(lat, lng, latDestiny, lngDestiny);
            double distVActual = Math.abs(alt - altDestiny); 
            if (distHActual < distH && distVActual < verticalErrorBarometer){
                distH = distHActual;
                distV = distVActual;
                index = i;
            }
        }        
        if (distH < horizontalErrorGPS*Constants.ONE_METER && distV < verticalErrorBarometer){   
            if (wptsCameraVideo.size() > 0){
                wptsCameraVideo.removeWaypoint(index);
            }
            StandardPrints.printMsgEmph("turn on the camera video");
            camera.makeAVideo();
        }
    }
    
    /**
     * Take the action of taking photos in sequence
     * @since version 4.0.0
     */
    private void actionPhotoInSequence(){
        double lat = drone.getSensors().getGPS().lat;
        double lng = drone.getSensors().getGPS().lng;
        double alt = drone.getSensors().getBarometer().alt_rel;        
        double distH = Integer.MAX_VALUE;
        double distV = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < wptsCameraPhotoInSeq.size(); i++){
            double latDestiny = wptsCameraPhotoInSeq.getWaypoint(i).getLat();
            double lngDestiny = wptsCameraPhotoInSeq.getWaypoint(i).getLng();
            double altDestiny = config.getAltRelMission();            
            double distHActual = UtilGeom.distanceEuclidian(lat, lng, latDestiny, lngDestiny);
            double distVActual = Math.abs(alt - altDestiny); 
            if (distHActual < distH && distVActual < verticalErrorBarometer){
                distH = distHActual;
                distV = distVActual;
                index = i;
            }
        }
        if (distH < horizontalErrorGPS*Constants.ONE_METER && distV < verticalErrorBarometer){   
            if (wptsCameraPhotoInSeq.size() > 0){
                wptsCameraPhotoInSeq.removeWaypoint(index);
            }
            StandardPrints.printMsgEmph("turn on the camera photo in sequence");
            camera.photoInSequence();
        }
    }
    
    /**
     * Read the mission with Cartesian coordinates.
     * Note: used by HGA4m, A_STAR4m and PATH_PLANNER4M
     * @since version 2.0.0
     */
    private void readMission3D(){
        try {
            if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)){
                if (config.getTypePlanner().equals(TypePlanner.HGA4M)){
                    String path = config.getDirPlanner() + config.getFileMissionPlannerHGA4m();
                    ReaderFileMission.mission3D(new File(path), wptsMission3D);
                }else if (config.getTypePlanner().equals(TypePlanner.A_STAR4M)){
                    String path = config.getDirPlanner() + config.getFileMissionPlannerAStar4m();
                    ReaderFileMission.mission3D(new File(path), wptsMission3D);
                }else if (config.getTypePlanner().equals(TypePlanner.PATH_PLANNER4M)){
                    String path = config.getDirPlanner() + config.getFileMissionPlannerPathPlanner4m();
                    ReaderFileMission.mission3D(new File(path), wptsMission3D);
                }
            }
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException] readMission3D()");
            ex.printStackTrace();            
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the buzzer's trigger data.
     * @since version 2.0.0
     */
    private void readerFileBuzzer(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionBuzzer(new File(path), wptsBuzzer);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileBuzzer()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the camera's trigger data (photo).
     * @since version 3.0.0
     */
    private void readerFileCameraPhoto(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionCameraPicture(new File(path), wptsCameraPicture);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileCameraPhoto()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the camera's trigger data (video).
     * @since version 3.0.0
     */
    private void readerFileCameraVideo(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionCameraVideo(new File(path), wptsCameraVideo);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileCameraVideo()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the camera's trigger data (photo in sequence).
     * @since version 4.0.0
     */
    private void readerFileCameraPhotoInSeq(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionCameraPhotoInSequence(new File(path), wptsCameraPhotoInSeq);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileCameraVideo()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Reads the feature mission file with the spraying's trigger data.
     * @since version 3.0.0
     */
    private void readerFileSpraying(){
        try {
            String path = config.getDirFiles()+ config.getFileFeatureMission();
            ReaderFileMission.missionSpraying(new File(path), wptsSpraying);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileSpraying()");
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Checks if waypoint has been reached.
     * @param latDest the latitude destine
     * @param lngDest the longitude destine
     * @param altDest the altitude destine
     * @return {@code true} if waypoint was reached
     *         {@code false} otherwise
     * @since version 2.0.0
     */
    private boolean waypointWasReached(double latDest, double lngDest, double altDest){   
        double distH = UtilGeom.distanceEuclidian(
                drone.getSensors().getGPS().lat, drone.getSensors().getGPS().lng, 
                latDest, lngDest);
        double distV = Math.abs(drone.getSensors().getBarometer().alt_rel - altDest);
        if (distV < verticalErrorBarometer && distH < horizontalErrorGPS * Constants.ONE_METER){
            return true;
        }else{
            return false;
        }
    }
    
}
