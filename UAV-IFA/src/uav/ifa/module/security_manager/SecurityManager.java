package uav.ifa.module.security_manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_communication.DataCommunication;
import uav.generic.struct.Parameter;
import uav.generic.hardware.aircraft.FixedWing;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.hardware.aircraft.RotaryWing;
import uav.generic.module.sensors_actuators.BuzzerControl;
import uav.generic.struct.constants.TypeAircraft;
import uav.ifa.module.decision_making.DecisionMaking;
import uav.ifa.struct.ReaderFileConfig;
import uav.ifa.module.communication_control.CommunicationControl;
import uav.ifa.module.communication_control.CommunicationFailure;
import uav.ifa.struct.Failure;
import uav.ifa.struct.ReaderFileParam;
import uav.ifa.struct.TypeFailure;
import uav.generic.struct.states.StateCommunication;
import uav.generic.struct.states.StateSystem;
import uav.generic.struct.states.StateMonitoring;
import uav.generic.struct.states.StateReplanning;
import uav.generic.struct.ReaderFileConfigGlobal;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeMsgCommunication;

/**
 *
 * @author Jesimar S. Arantes
 */
public class SecurityManager {
        
    private final Drone drone;
    private final DataCommunication dataAcquisition;
    private final CommunicationControl communicationControl;
    private CommunicationFailure communicationFailure;
    private final DecisionMaking decisonMaking;
    
    private final ReaderFileConfig configLocal;
    private final ReaderFileConfigGlobal configGlobal;
    private final ReaderFileParam readerParam;
    
    private PrintStream printLogAircraft; 
    private PrintStream printLogOverhead; 
    private StateSystem stateSystem;
    private StateMonitoring stateMonitoring;
        
    private long timeInit;
    private long timeActual;
    
    private final List<Failure> listOfFailure = new LinkedList<>();    

    public SecurityManager() {
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
        this.configLocal = ReaderFileConfig.getInstance();
        if (!configLocal.read()){
            System.exit(0);
        }
        if (!configLocal.checkReadFields()){
            System.exit(0);
        }
        if (!configLocal.parseToVariables()){
            System.exit(0);
        }
        this.readerParam = ReaderFileParam.getInstance();
        
        if (configGlobal.getTypeAircraft().equals(TypeAircraft.FIXED_WING)){
            drone = new FixedWing("Ararinha");
        } else if (configGlobal.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
            drone = new RotaryWing("iDroneAlpha");
        } else{
            drone = new RotaryWing("iDroneAlpha");
        }
        
        createFileLogOverhead();
        this.dataAcquisition = new DataCommunication(drone, "IFA", printLogOverhead);
        this.communicationControl = new CommunicationControl(drone);
        if (configLocal.isUavInsertFailure()){
            this.communicationFailure = new CommunicationFailure();
        }
        this.decisonMaking = new DecisionMaking(drone, dataAcquisition);
        stateSystem = StateSystem.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }
    
    public void init() {        
        StandardPrints.printMsgEmph("initializing ...");        
        createFileLogAircraft();                //blocked
        waitingForTheServer();                  //blocked
        configParametersToFlight();             //blocked
        
        dataAcquisition.getParameters();
        dataAcquisition.getHomeLocation();
        
        if (configLocal.isUavInsertFailure()){
            communicationFailure.startServerFailure();//blocked
            communicationFailure.receiveData();     //Thread
        }
        
        communicationControl.startServerIFA();  //blocked
        communicationControl.receiveData();     //Thread
        
        monitoringAircraft();                   //Thread
        waitingForAnAction();                   //Thread                
        monitoringStateMachine();               //Thread
                
        stateSystem = StateSystem.INITIALIZED;
        StandardPrints.printMsgEmph("initialized ...");
        timeInit = System.currentTimeMillis();
    }
    
    private void createFileLogOverhead(){
        try {
            int i = 0;
            File file;
            do{
                i++;
                file = new File("log-overhead-ifa" + i + ".csv");  
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
    
    private void createFileLogAircraft(){
        StandardPrints.printMsgEmph("create file log aircraft ...");
        try {
            int i = 0;
            File file;
            do{
                i++;
                file = new File("log-aircraft" + i + ".csv");  
            }while(file.exists());
            printLogAircraft = new PrintStream(file);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: createFileLogAircraft()");
            ex.printStackTrace();
            System.exit(0);
        } catch(Exception ex){
            StandardPrints.printMsgError2("Error [Exception]: createFileLogAircraft()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void waitingForTheServer(){
        StandardPrints.printMsgEmph("waiting for the server ...");
        try{
            boolean serverIsRunning = dataAcquisition.serverIsRunning();
            while(!serverIsRunning){          
                StandardPrints.printMsgWarning("waiting for the server uav-services-dronekit...");
                Thread.sleep(Constants.TIME_TO_SLEEP_WAITING_SERVER);
                serverIsRunning = dataAcquisition.serverIsRunning();
            }
            Thread.sleep(Constants.TIME_TO_SLEEP_WAITING_SERVER);
        } catch(InterruptedException ex){
            StandardPrints.printMsgError2("Error [InterruptedException]: waitingForTheServer()");
            ex.printStackTrace();
            System.exit(0);
        } catch(Exception ex){
            StandardPrints.printMsgError2("Error [Exception]: waitingForTheServer()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void configParametersToFlight() {
        StandardPrints.printMsgEmph("config parameters to flight ...");
        try {
            readerParam.read();
            for (int i = 0; i < readerParam.size(); i++){
                String name = readerParam.getName(i);
                double value = readerParam.getValue(i);
                Parameter param = new Parameter(name, value);       
                dataAcquisition.setParameter(param);
            }
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException] configParametersToFlight()");
            ex.printStackTrace();
            System.exit(0);
        } catch (Exception ex) {
            StandardPrints.printMsgError2("Error [Exception] configParametersToFlight()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private void monitoringAircraft() {
        StandardPrints.printMsgEmph("monitoring aircraft");        
        int time = (int)(1000.0/configGlobal.getFreqUpdateData());
        stateMonitoring = StateMonitoring.MONITORING;
        printLogAircraft.println(drone.title());        
        
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while(stateSystem != StateSystem.DISABLED){    
                        timeActual = System.currentTimeMillis();
                        double timeDiff = (timeActual - timeInit)/1000.0;
                        drone.setTime(timeDiff);
                        dataAcquisition.getAllInfoSensors();
                        checkStatusSystem();
                        printLogAircraft.println(drone.toString());
                        printLogAircraft.flush();
                        Thread.sleep(time);
                    }
                } catch (InterruptedException ex) {
                    StandardPrints.printMsgError2("Error [InterruptedException] monitoringAircraft()");
                    ex.printStackTrace();
                    printLogAircraft.close();
                    stateMonitoring = StateMonitoring.DISABLED;
                    checkStatusSystem();
                } catch (Exception ex){
                    StandardPrints.printMsgError2("Error [Exception] monitoringAircraft()");
                    ex.printStackTrace();
                    stateMonitoring = StateMonitoring.DISABLED;
                    checkStatusSystem();
                }
            }
        });
    }
    
    //Adicionar ao sistema as seguintes falhas somente se o drone estiver voando 
    //caso contrario isso nao faz sentido.
    private void checkStatusSystem(){
        if (configLocal.isUavInsertFailure() && 
                communicationFailure.getFailure() && 
                !hasFailure(TypeFailure.FAIL_BASED_INSERT_FAILURE)){
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_BASED_INSERT_FAILURE));
            decisonMaking.setTypeAction(communicationFailure.getTypeAction());
            StandardPrints.printMsgError("FAIL BASED INSERT FAILURE -> Time: "+drone.getTime());
        }
        if (drone.getBattery().level < configGlobal.getLevelMinimumBattery() && 
                !hasFailure(TypeFailure.FAIL_BATTERY)){
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_BATTERY));
            StandardPrints.printMsgError("FAIL BATTERY -> Time: "+drone.getTime());
        } 
        if (drone.getGPSInfo().fixType != 3 && 
                !hasFailure(TypeFailure.FAIL_GPS)){
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_GPS));
            StandardPrints.printMsgError("FAIL GPS -> Time: "+drone.getTime());            
        }
        if (stateSystem == StateSystem.DISABLED && 
                !hasFailure(TypeFailure.FAIL_SYSTEM_IFA)){
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_SYSTEM_IFA));
            StandardPrints.printMsgError("FAIL IFA -> Time: "+drone.getTime());
        }
        if ((communicationControl.getStateCommunication() == StateCommunication.DISABLED || 
                communicationControl.isMosaDisabled()) && 
                !hasFailure(TypeFailure.FAIL_SYSTEM_MOSA)){
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_SYSTEM_MOSA));
            StandardPrints.printMsgError("FAIL MOSA -> Time: "+drone.getTime());
        }
        if (drone.getStatusUAV().systemStatus.equals("EMERGENCY") && 
                !hasFailure(TypeFailure.FAIL_AP_EMERGENCY)){
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_AP_EMERGENCY));
            StandardPrints.printMsgError("FAIL AP EMERGENCY -> Time: "+drone.getTime());
        }
        if (drone.getStatusUAV().systemStatus.equals("POWEROFF") && 
                !hasFailure(TypeFailure.FAIL_AP_POWEROFF)){
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_AP_POWEROFF));
            StandardPrints.printMsgError("FAIL AP POWEROFF -> Time: "+drone.getTime());
        }
//        if (drone.engineCrash() && 
//                !hasFailure(TypeFailure.FAIL_ENGINE)){
//            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_ENGINE));
//            StandardPrints.printMsgError("FAIL ENGINE -> Time: "+drone.getTime());
//        }
    }   
    
    //melhorar: por enquanto esta tratando apenas a primeira falha.
    //melhorar: o sistema so tenta planejar algo sobre a 1 falha encontrada.
    private void waitingForAnAction() {
        StandardPrints.printMsgEmph("waiting for an action");
        Executors.newSingleThreadExecutor().execute(new Runnable(){            
            @Override
            public void run(){
                while(stateSystem != StateSystem.DISABLED){
                    try {
                        //Melhorar verificacao para ver se a aeronave esta voando.
                        if (drone.getStatusUAV().armed && hasFailure()){
                            communicationControl.sendData(TypeMsgCommunication.IFA_MOSA_STOP);
                            actionTurnOnTheAlarm();
                            decisonMaking.actionToDoSomething(listOfFailure.get(0));
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
                    while(stateSystem != StateSystem.DISABLED){                    
                        if (decisonMaking.getStateReplanning()== StateReplanning.DISABLED){
                            stateSystem = StateSystem.DISABLED;
                        }
                        if (stateMonitoring == StateMonitoring.DISABLED){
                            stateSystem = StateSystem.DISABLED;
                        }
//                        if (communicationControl.getStateCommunication() == StateCommunication.DISABLED){
//                            stateIFA = StateIFA.DISABLED;                            
//                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_MONITORING_STATE_MACHINE);                     
                    }
                } catch (InterruptedException ex) {
                    
                }
                try{
                    communicationControl.close();
                } catch (Exception ex){
                    
                }
            }
        });        
    }
    
    private boolean hasFailure(TypeFailure failure){        
        for (Failure fail : listOfFailure){
            if (fail.typeFailure == failure){
                return true;
            }
        }
        return false;
    }
    
    private boolean hasFailure(){        
        return listOfFailure.size() > 0;
    }
    
    private void actionTurnOnTheAlarm(){
        StandardPrints.printMsgEmph("turn on the alarm");
        BuzzerControl buzzer = new BuzzerControl();
        buzzer.turnOnAlarm();
    }
}
