package uav.mosa.module.mission_manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import lib.color.StandardPrints;
import uav.generic.module.data_acquisition.DataAcquisition;
import uav.generic.struct.Constants;
import uav.generic.struct.Mission;
import uav.hardware.aircraft.Ararinha;
import uav.hardware.aircraft.Drone;
import uav.hardware.aircraft.iDroneAlpha;
import uav.mosa.module.communication_control.CommunicationControl;
import uav.mosa.module.decision_making.DecisionMaking;
import uav.mosa.struct.ReaderBuzzer;
import uav.mosa.struct.ReaderFileConfig;
import uav.mosa.struct.states.StateCommunication;
import uav.mosa.struct.states.StateMOSA;
import uav.mosa.struct.states.StateMonitoring;
import uav.mosa.struct.states.StatePlanning;

/**
 *
 * @author jesimar
 */
public class MissionManager {
    
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final CommunicationControl communicationControl;
    private final DecisionMaking decisonMaking;
    private final ReaderFileConfig config;
    private final ReaderBuzzer readerBuzzer;
    private final Mission wptsBuzzer;
    private PrintStream printLogOverhead;     
    
    private StateMOSA stateMOSA;
    private StateMonitoring stateMonitoring;    
        
    private final int SLEEP_TIME_WAITING_ACTION = 100;//in milliseconds
    private final int SLEEP_TIME_WAITING_CHANGE_STATES = 100;//in milliseconds
        
    public MissionManager() {
        this.config = ReaderFileConfig.getInstance();
        if (!config.read()){
            System.exit(0);
        }
        if (!config.checkReadFields()){
            System.exit(0);
        }
        if (!config.parseToVariables()){
            System.exit(0);
        }
        if (config.getNameDrone().equals(Constants.NAME_DRONE_ARARINHA)){
            drone = new Ararinha();
        } else {
            drone = new iDroneAlpha();
        }
        createFileLogOverhead();
        this.dataAcquisition = new DataAcquisition(drone, "MOSA", printLogOverhead);
        this.communicationControl = new CommunicationControl(drone);
        this.decisonMaking = new DecisionMaking(drone, dataAcquisition); 
        this.wptsBuzzer = new Mission();
        this.readerBuzzer = new ReaderBuzzer(wptsBuzzer);        
        stateMOSA = StateMOSA.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }
    
    public void init() {
        StandardPrints.printMsgEmph("initializing ...");    
        
        try {
            readerBuzzer.reader(new File("../Modules-MOSA/Turn-On-The-Buzzer/"
                    + "waypointsTurnOnTheBuzzer.txt"));
        } catch (FileNotFoundException ex) {
            System.exit(0);
        }
        
        dataAcquisition.getParameters();
        
        communicationControl.connectClient();   //blocked        
        communicationControl.receiveData();     //Thread  
        monitoringAircraft();                   //Thread
        waitingForAnAction();                   //Thread                            
        monitoringStateMachine();               //Thread
        
        stateMOSA = StateMOSA.INITIALIZED;
        try{//Aguarda para ter certeza que IFA terminou tambem de inicializar
            Thread.sleep(1000);
        }catch (InterruptedException ex){ }
        
        communicationControl.sendData("StateMOSA.INITIALIZED");
        StandardPrints.printMsgEmph("initialized ..."); 
    }
    
    private void createFileLogOverhead(){
        try {
            int i = 0;
            File file;
            do{
                i++;
                file = new File("log-overhead" + i + ".csv");  
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
    
    private void monitoringAircraft() {
        StandardPrints.printMsgEmph("monitoring aircraft");
        int time = (int)(1000.0/config.getFreqUpdateData());      
        stateMonitoring = StateMonitoring.MONITORING;
        
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try { 
                    while(stateMOSA != StateMOSA.DISABLED){                                          
                        dataAcquisition.getAllInfoSensors();
                        dataAcquisition.getDistanceToHome();
                        actionTurnOnTheBuzzer();
                        drone.incrementTime(time);
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
                while(stateMOSA != StateMOSA.DISABLED){
                    try {
                        if (communicationControl.isStartMission()){
                            decisonMaking.actionToDoSomething();
//                            communicationControl.sendData("MOSA_READY");
                            break;
                        }
                        Thread.sleep(SLEEP_TIME_WAITING_ACTION);
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
                    while(stateMOSA != StateMOSA.DISABLED){                    
                        if (communicationControl.getStateCommunication() == StateCommunication.DISABLED){
                            communicationControl.sendData("MOSA.DISABLED");
                            stateMOSA = StateMOSA.DISABLED;
                            Thread.sleep(100);
                            System.exit(0);
                        }
                        if (decisonMaking.getStatePlanning()== StatePlanning.DISABLED){
                            communicationControl.sendData("MOSA.DISABLED");
                            stateMOSA = StateMOSA.DISABLED;
                            System.exit(0);
                        }
                        if (stateMonitoring == StateMonitoring.DISABLED){
                            communicationControl.sendData("MOSA.DISABLED");
                            stateMOSA = StateMOSA.DISABLED;
                            System.exit(0);
                        }
                        Thread.sleep(SLEEP_TIME_WAITING_CHANGE_STATES);                     
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
    
    private void actionTurnOnTheBuzzer(){
        double latDrone = drone.getGPS().lat;
        double lngDrone =  drone.getGPS().lng;
        double altDrone =  drone.getGPS().alt_rel;        
        double distH = Integer.MAX_VALUE;
        double distV = Integer.MAX_VALUE;
        double FACTOR = 0.5;
        int index = 0;
        for (int i = 0; i < wptsBuzzer.size(); i++){
            double latDestiny = wptsBuzzer.getWaypoint(i).getLat();
            double lngDestiny = wptsBuzzer.getWaypoint(i).getLng();
            double altDestiny = wptsBuzzer.getWaypoint(i).getAlt();            
            double distHActual = Math.sqrt(
                    (latDrone - latDestiny) * (latDrone - latDestiny) + 
                    (lngDrone - lngDestiny) * (lngDrone - lngDestiny));
            double distVActual = Math.abs(altDrone - altDestiny); 
            if (distHActual < distH && distVActual < FACTOR){
                distH = distHActual;
                distV = distVActual;
                index = i;
            }
        }
        wptsBuzzer.removeWaypoint(index);
        if (distH < 2*Constants.ONE_METER && distV < FACTOR){            
            try {
                boolean print = true;
                File f = new File(config.getDirBuzzer());
                final Process comp = Runtime.getRuntime().exec(config.getCmdExecBuzzer(), null, f);
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        Scanner sc = new Scanner(comp.getInputStream());
                        if (print) {
                            while (sc.hasNextLine()) {
                                System.out.println(sc.nextLine());
                            }
                        }
                        sc.close();
                    }
                });
                comp.waitFor();
            } catch (IOException ex) {
                StandardPrints.printMsgWarning("Warning [IOException] actionTurnOnTheBuzzer()");
            } catch (InterruptedException ex) {
                StandardPrints.printMsgWarning("Warning [InterruptedException] actionTurnOnTheBuzzer()");
            }
        }
    }
    
    private void actionTakeAPicture(){
        
    }
}
