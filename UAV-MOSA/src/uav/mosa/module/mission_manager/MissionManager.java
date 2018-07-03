package uav.mosa.module.mission_manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_communication.DataCommunication;
import uav.generic.module.sensors_actuators.BuzzerControl;
import uav.generic.module.sensors_actuators.CameraControl;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.hardware.aircraft.FixedWing;
import uav.generic.hardware.aircraft.RotaryWing;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.reader.ReaderFileConfigAircraft;
import uav.generic.struct.reader.ReaderFileConfigGlobal;
import uav.generic.struct.constants.TypeAircraft;
import uav.generic.struct.constants.TypeLocalExecPlanner;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.constants.TypeSystemExecMOSA;
import uav.generic.struct.mission.Mission3D;
import uav.generic.struct.states.StateCommunication;
import uav.generic.struct.states.StateSystem;
import uav.generic.struct.states.StateMonitoring;
import uav.generic.struct.states.StatePlanning;
import uav.generic.struct.reader.ReaderFileMission;
import uav.generic.util.UtilGeom;
import uav.mosa.module.communication_control.CommunicationGCS;
import uav.mosa.module.communication_control.CommunicationIFA;
import uav.mosa.module.decision_making.DecisionMaking;
import uav.mosa.struct.ReaderFileConfigMOSA;

/**
 * Classe que gerencia toda a missão em curso pela aeronave.
 * @author Jesimar S. Arantes
 */
public class MissionManager {
    
    private final Drone drone;
    private final DataCommunication dataAcquisition;
    private final CommunicationIFA communicationIFA;
    private final CommunicationGCS communicationGCS;
    private final DecisionMaking decisonMaking;
    
    private final ReaderFileConfigMOSA configLocal;
    private final ReaderFileConfigGlobal configGlobal;
    private final ReaderFileConfigAircraft configAircraft;
    
    private final Mission3D wptsMission3D;
    private Mission wptsBuzzer;
    private Mission wptsCameraPhoto;
    private Mission wptsCameraVideo;
    private Mission wptsSpraying;
    private PrintStream printLogOverhead;     
    
    private StateSystem stateMOSA;
    private StateMonitoring stateMonitoring;    
        
    private final long timeInit;
    private long timeActual;
    
    private final double HORIZONTAL_ERROR = Constants.HORIZONTAL_ERROR_GPS;
    private final double VERTICAL_ERROR = Constants.VERTICAL_ERROR_BAROMETER;
        
    /**
     * Class constructor.
     */
    public MissionManager() {
        timeInit = System.currentTimeMillis();
        
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
        if (!configGlobal.read()){
            System.exit(0);
        }
        if (!configGlobal.checkReadFields()){
            System.exit(0);
        }
        if (!configGlobal.parseToVariables()){
            System.exit(0);
        }
        this.configLocal = ReaderFileConfigMOSA.getInstance();
        if (!configLocal.read()){
            System.exit(0);
        }
        if (!configLocal.checkReadFields()){
            System.exit(0);
        }
        if (!configLocal.parseToVariables()){
            System.exit(0);
        }
        
        this.configAircraft = ReaderFileConfigAircraft.getInstance();
        if (!configAircraft.read()){
            System.exit(0);
        }
        if (!configAircraft.checkReadFields()){
            System.exit(0);
        }
        
        if (configGlobal.getTypeAircraft().equals(TypeAircraft.FIXED_WING)){
            drone = new FixedWing(configAircraft.getNameAircraft(), 
                    configAircraft.getSpeedCruize(), configAircraft.getSpeedMax(), 
                    configAircraft.getMass(), configAircraft.getPayload(), 
                    configAircraft.getEndurance());
        } else if (configGlobal.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
            drone = new RotaryWing(configAircraft.getNameAircraft(), 
                    configAircraft.getSpeedCruize(), configAircraft.getSpeedMax(), 
                    configAircraft.getMass(), configAircraft.getPayload(), 
                    configAircraft.getEndurance());
        } else{
            drone = new RotaryWing("iDroneAlpha");
        }
        
        createFileLogOverhead();
        this.dataAcquisition = new DataCommunication(drone, "MOSA", 
                configGlobal.getHostSOA(), configGlobal.getPortNetworkSOA(), 
                printLogOverhead);
        this.wptsMission3D = new Mission3D();
        readMission3D();
        this.decisonMaking = new DecisionMaking(drone, dataAcquisition, wptsMission3D); 
        this.communicationIFA = new CommunicationIFA(drone);
        this.communicationGCS = new CommunicationGCS();
        
        if (configGlobal.hasBuzzer()){
            this.wptsBuzzer = new Mission();
            readerFileBuzzer();
        }
        if (configGlobal.hasCamera()){
            this.wptsCameraPhoto = new Mission(); 
            this.wptsCameraVideo = new Mission();
            readerFileCameraPhoto();
            readerFileCameraVideo();
        }
        if (configGlobal.hasSpraying()){
            this.wptsSpraying = new Mission(); 
            readerFileSpraying();
        }
        stateMOSA = StateSystem.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }
    
    public void init() {
        StandardPrints.printMsgEmph("initializing ...");    
        
        dataAcquisition.getParameters();
        
        communicationIFA.connectServerIFA();    //blocked        
        communicationIFA.receiveData();         //Thread 
        
        communicationGCS.startServerMOSA();     //Thread        
        communicationGCS.receiveData();         //Thread 
                
        monitoringAircraft();                   //Thread
        waitingForAnAction();                   //Thread                            
        monitoringStateMachine();               //Thread
        
        stateMOSA = StateSystem.INITIALIZED;
        try{//Aguarda para ter certeza que IFA terminou tambem de inicializar
            Thread.sleep(1000);
        }catch (InterruptedException ex){ }
        
        communicationIFA.sendData(TypeMsgCommunication.MOSA_IFA_INITIALIZED);
        StandardPrints.printMsgEmph("initialized ..."); 
    }
    
    private void createFileLogOverhead(){
        try {
            int i = 0;
            File file;
            do{
                i++;
                file = new File("log-overhead-mosa" + i + ".csv");  
            }while(file.exists());
            printLogOverhead = new PrintStream(file);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: createFileLogOverhead()");
            ex.printStackTrace();
            System.exit(0);
        } catch(Exception ex){
            StandardPrints.printMsgError2("Error [Exception]: createFileLogOverhead()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void readMission3D(){
        try {
            if (configLocal.getSystemExec().equals(TypeSystemExecMOSA.PLANNER)){
                String path = configLocal.getDirPlanner() + configLocal.getFileWaypointsMission();
                ReaderFileMission.mission3D(new File(path), wptsMission3D);
            }
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException] readMission3D()");
            ex.printStackTrace();            
            System.exit(0);
        }
    }
    
    private void readerFileBuzzer(){
        try {
            String path = configGlobal.getDirFiles()+ configGlobal.getFileFeatureMission();
            ReaderFileMission.missionBuzzer(new File(path), wptsBuzzer);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileBuzzer()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void readerFileCameraPhoto(){
        try {
            String path = configGlobal.getDirFiles()+ configGlobal.getFileFeatureMission();
            ReaderFileMission.missionCameraPhoto(new File(path), wptsCameraPhoto);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileCameraPhoto()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void readerFileCameraVideo(){
        try {
            String path = configGlobal.getDirFiles()+ configGlobal.getFileFeatureMission();
            ReaderFileMission.missionCameraVideo(new File(path), wptsCameraVideo);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileCameraVideo()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void readerFileSpraying(){
        try {
            String path = configGlobal.getDirFiles()+ configGlobal.getFileFeatureMission();
            ReaderFileMission.missionSpraying(new File(path), wptsSpraying);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: readerFileSpraying()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void monitoringAircraft() {
        StandardPrints.printMsgEmph("monitoring aircraft");
        int time = (int)(1000.0/configGlobal.getFreqUpdateDataAP());      
        stateMonitoring = StateMonitoring.MONITORING;
        
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try { 
                    while(stateMOSA != StateSystem.DISABLED){
                        timeActual = System.currentTimeMillis();
                        double timeDiff = (timeActual - timeInit)/1000.0;
                        drone.setTime(timeDiff);
                        dataAcquisition.getAllInfoSensors();
                        if (configGlobal.hasPowerModule()){
                            dataAcquisition.getBattery();
                        }
                        if (configGlobal.hasBuzzer()){
                            actionTurnOnTheBuzzer();
                        }
                        if (configGlobal.hasCamera()){
                            actionTakeAPicture();
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
        
    private void waitingForAnAction() {
        StandardPrints.printMsgEmph("waiting for an action");
        Executors.newSingleThreadExecutor().execute(new Runnable(){            
            @Override
            public void run(){
                while(stateMOSA != StateSystem.DISABLED){
                    try {
                        if (communicationIFA.isStartMission()){
                            if (configLocal.getLocalExecPlanner().equals(TypeLocalExecPlanner.ONBOARD)){
                                decisonMaking.actionToDoSomething();
                            }else{
                                decisonMaking.actionToDoSomethingOffboard(communicationGCS);
                            }
                            break;
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_WAITING_FOR_AN_ACTION);
                    } catch (InterruptedException ex) {
                        
                    } 
                }
            }
        });
    }
    
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
                            System.exit(0);
                        }
                        if (decisonMaking.getStatePlanning()== StatePlanning.DISABLED){
                            communicationIFA.sendData(TypeMsgCommunication.MOSA_IFA_DISABLED);
                            stateMOSA = StateSystem.DISABLED;
                            Thread.sleep(100);
                            System.exit(0);
                        }
                        if (stateMonitoring == StateMonitoring.DISABLED){
                            communicationIFA.sendData(TypeMsgCommunication.MOSA_IFA_DISABLED);
                            stateMOSA = StateSystem.DISABLED;
                            Thread.sleep(100);
                            System.exit(0);
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
     * Comando que aciona o buzzer caso o drone chegue ao local especificado para 
     * acioná-lo.
     * Devido a forma como foi feito se tiver dois waypoints no mesmo lugar, 
     * esse codigo nao funciona muito bem, acionando um após o outro não olhando 
     * questões relacionados a ordem dos fatos. Por exemplo, Case-III do artigo IROS.
     */  
    private void actionTurnOnTheBuzzer(){   
        double lat = drone.getGPS().lat;
        double lng = drone.getGPS().lng;
        double alt = drone.getBarometer().alt_rel;
        double distH = Integer.MAX_VALUE;
        double distV = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < wptsBuzzer.size(); i++){
            double latDestiny = wptsBuzzer.getWaypoint(i).getLat();
            double lngDestiny = wptsBuzzer.getWaypoint(i).getLng();
            double altDestiny = configGlobal.getAltRelMission();            
            double distHActual = UtilGeom.distanceEuclidian(lat, lng, latDestiny, lngDestiny);
            double distVActual = Math.abs(alt - altDestiny); 
            if (distHActual < distH && distVActual < VERTICAL_ERROR){
                distH = distHActual;
                distV = distVActual;
                index = i;
            }
        }        
        if (distH < HORIZONTAL_ERROR*Constants.ONE_METER && distV < VERTICAL_ERROR){   
            if (wptsBuzzer.size() > 0){
                wptsBuzzer.removeWaypoint(index);
            }
            StandardPrints.printMsgEmph("turn on the buzzer");
            BuzzerControl buzzer = new BuzzerControl();
            buzzer.turnOnBuzzer();
        }
    }
    
    private void actionTakeAPicture(){
        double lat = drone.getGPS().lat;
        double lng = drone.getGPS().lng;
        double alt = drone.getBarometer().alt_rel;        
        double distH = Integer.MAX_VALUE;
        double distV = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < wptsCameraPhoto.size(); i++){
            double latDestiny = wptsCameraPhoto.getWaypoint(i).getLat();
            double lngDestiny = wptsCameraPhoto.getWaypoint(i).getLng();
            double altDestiny = configGlobal.getAltRelMission();            
            double distHActual = UtilGeom.distanceEuclidian(lat, lng, latDestiny, lngDestiny);
            double distVActual = Math.abs(alt - altDestiny); 
            if (distHActual < distH && distVActual < VERTICAL_ERROR){
                distH = distHActual;
                distV = distVActual;
                index = i;
            }
        }        
        if (distH < HORIZONTAL_ERROR*Constants.ONE_METER && distV < VERTICAL_ERROR){   
            if (wptsCameraPhoto.size() > 0){
                wptsCameraPhoto.removeWaypoint(index);
            }
            StandardPrints.printMsgEmph("turn on the camera");
            CameraControl camera = new CameraControl();
            camera.takeAPicture();
        }
    }
    
    private boolean waypointWasReached(double latDest, double lngDest, double altDest){   
        double distH = UtilGeom.distanceEuclidian(
                drone.getGPS().lat, drone.getGPS().lng, latDest, lngDest);
        double distV = Math.abs(drone.getBarometer().alt_rel - altDest);
        if (distV < VERTICAL_ERROR && distH < HORIZONTAL_ERROR * Constants.ONE_METER){
            return true;
        }else{
            return false;
        }
    }
    
    //falta começar
    private void actionMakeAVideo(){
        
    }
}
