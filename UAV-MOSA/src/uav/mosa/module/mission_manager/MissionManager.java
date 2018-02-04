package uav.mosa.module.mission_manager;

import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_acquisition.DataAcquisition;
import uav.generic.struct.Constants;
import uav.hardware.aircraft.Ararinha;
import uav.hardware.aircraft.Drone;
import uav.hardware.aircraft.iDroneAlpha;
import uav.mosa.module.communication_control.CommunicationControl;
import uav.mosa.module.decision_making.DecisionMaking;
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
        this.dataAcquisition = new DataAcquisition(drone, "MOSA");
        this.communicationControl = new CommunicationControl(drone);
        this.decisonMaking = new DecisionMaking(drone, dataAcquisition); 
        stateMOSA = StateMOSA.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }
    
    public void init() {
        StandardPrints.printMsgEmph("initializing ...");        
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
}
