package uav.ifa.module.security_manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_acquisition.DataAcquisition;
import uav.generic.struct.Constants;
import uav.generic.struct.Parameter;
import uav.generic.struct.ParameterJSON;
import uav.hardware.aircraft.Ararinha;
import uav.hardware.aircraft.Drone;
import uav.hardware.aircraft.iDroneAlpha;
import uav.ifa.module.decision_making.DecisionMaking;
import uav.ifa.struct.ReaderFileConfig;
import uav.ifa.module.communication_control.CommunicationControl;
import uav.ifa.module.communication_control.CommunicationFailure;
import uav.ifa.struct.Failure;
import uav.ifa.struct.ReaderFileParam;
import uav.ifa.struct.TypesOfFailures;
import uav.ifa.struct.states.StateCommunication;
import uav.ifa.struct.states.StateIFA;
import uav.ifa.struct.states.StateMonitoring;
import uav.ifa.struct.states.StateReplanning;

/**
 *
 * @author jesimar
 */
public class SecurityManager {
        
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final CommunicationControl communicationControl;
    private CommunicationFailure communicationFailure;
    private final DecisionMaking decisonMaking;
    private final ReaderFileConfig config;
    
    private PrintStream printLogAircraft; 
    private PrintStream printLogOverhead; 
    private StateIFA stateIFA;
    private StateMonitoring stateMonitoring;
        
    private long timeInitApp;
    private long timeActual;
    private final int SLEEP_TIME_WAITING_SERVER = 1000;//in milliseconds
    private final int SLEEP_TIME_WAITING_ACTION = 100;//in milliseconds
    private final int SLEEP_TIME_WAITING_CHANGE_STATES = 100;//in milliseconds
    
    private final List<Failure> listOfFailure = new LinkedList<>();    

    public SecurityManager() {
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
        this.dataAcquisition = new DataAcquisition(drone, "IFA", printLogOverhead);
        this.communicationControl = new CommunicationControl(drone);
        if (config.isUavInsertFailure()){
            this.communicationFailure = new CommunicationFailure();
        }
        this.decisonMaking = new DecisionMaking(drone, dataAcquisition);
        stateIFA = StateIFA.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }
    
    public void init() {        
        StandardPrints.printMsgEmph("initializing ...");        
        createFileLogAircraft();                //blocked
        waitingForTheServer();                  //blocked
        configParametersToFlight();             //blocked
        
        dataAcquisition.getParameters();
        dataAcquisition.getHomeLocation();
        
        if (config.isUavInsertFailure()){
            communicationFailure.startServerFailure();//blocked
            communicationFailure.receiveData();     //Thread
        }
        
        communicationControl.startServerIFA();  //blocked
        communicationControl.receiveData();     //Thread
        
        timeInitApp = System.currentTimeMillis();
        monitoringAircraft();                   //Thread
        waitingForAnAction();                   //Thread                
        monitoringStateMachine();               //Thread
                
        stateIFA = StateIFA.INITIALIZED;
        StandardPrints.printMsgEmph("initialized ...");
        timeInitApp = System.currentTimeMillis();
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
                file = new File(config.getFileLogAircraft() + i + ".csv");  
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
                Thread.sleep(SLEEP_TIME_WAITING_SERVER);
                serverIsRunning = dataAcquisition.serverIsRunning();
            }
            Thread.sleep(SLEEP_TIME_WAITING_SERVER);
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
        ReaderFileParam readerParam = new ReaderFileParam();
        try {
            readerParam.reader(new File("./config-param.txt"));
            for (int i = 0; i < readerParam.getSize(); i++){
                String name = readerParam.getName(i);
                double value = readerParam.getValue(i);
                Parameter param = new Parameter(name, value);
                ParameterJSON ps = new ParameterJSON(param);        
                dataAcquisition.setParameter(ps);
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
        int time = (int)(1000.0/config.getFreqUpdateData());
        stateMonitoring = StateMonitoring.MONITORING;
        printLogAircraft.println(drone.title());        
        
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while(stateIFA != StateIFA.DISABLED){            
                        timeActual = System.currentTimeMillis();
//                        System.out.print((timeActual - timeInitApp)/1000 + "  ");
                        dataAcquisition.getAllInfoSensors();
                        dataAcquisition.getDistanceToHome();
                        checkStatusSystem();
                        printLogAircraft.println(drone.toString());
                        printLogAircraft.flush();
                        drone.incrementTime(time);
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
        if (config.isUavInsertFailure() && 
                communicationFailure.getFailure() && 
                !hasFailure(TypesOfFailures.FAIL_BASED_INSERT_FAILURE)){
            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_BASED_INSERT_FAILURE));
            decisonMaking.setTypeAction(communicationFailure.getTypeAction());
            StandardPrints.printMsgError("FAIL BASED INSERT FAILURE -> Time: "+drone.getTime());
        }
        if (timeActual - timeInitApp > config.getTimeToFailure() * 1000 && 
                !hasFailure(TypesOfFailures.FAIL_BASED_TIME)){
            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_BASED_TIME));
            StandardPrints.printMsgError("FAIL BASED TIME -> Time: "+drone.getTime());
        }
        if (drone.getBattery().level < config.getLevelMinBatteryToFail() && 
                !hasFailure(TypesOfFailures.FAIL_BATTERY)){
            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_BATTERY));
            StandardPrints.printMsgError("FAIL BATTERY -> Time: "+drone.getTime());
        } 
        if (drone.getGPSInfo().fixType != 3 && 
                !hasFailure(TypesOfFailures.FAIL_GPS)){
            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_GPS));
            StandardPrints.printMsgError("FAIL GPS -> Time: "+drone.getTime());            
        }
        if (stateIFA == StateIFA.DISABLED && 
                !hasFailure(TypesOfFailures.FAIL_SYSTEM_IFA)){
            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_SYSTEM_IFA));
            StandardPrints.printMsgError("FAIL IFA -> Time: "+drone.getTime());
        }
        if ((communicationControl.getStateCommunication() == StateCommunication.DISABLED || 
                communicationControl.isMosaDisabled()) && 
                !hasFailure(TypesOfFailures.FAIL_SYSTEM_MOSA)){
            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_SYSTEM_MOSA));
            StandardPrints.printMsgError("FAIL MOSA -> Time: "+drone.getTime());
        }
        if (drone.getStatusUAV().systemStatus.equals("EMERGENCY") && 
                !hasFailure(TypesOfFailures.FAIL_AP_EMERGENCY)){
            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_AP_EMERGENCY));
            StandardPrints.printMsgError("FAIL AP EMERGENCY -> Time: "+drone.getTime());
        }
        if (drone.getStatusUAV().systemStatus.equals("POWEROFF") && 
                !hasFailure(TypesOfFailures.FAIL_AP_POWEROFF)){
            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_AP_POWEROFF));
            StandardPrints.printMsgError("FAIL AP POWEROFF -> Time: "+drone.getTime());
        }
    }   
    
    //melhorar: por enquanto esta tratando apenas a primeira falha.
    //melhorar: o sistema so tenta planejar algo sobre a 1 falha encontrada.
    private void waitingForAnAction() {
        StandardPrints.printMsgEmph("waiting for an action");
        Executors.newSingleThreadExecutor().execute(new Runnable(){            
            @Override
            public void run(){
                while(stateIFA != StateIFA.DISABLED){
                    try {
                        //Melhorar verificacao para ver se a aeronave esta voando.
                        if (drone.getStatusUAV().armed && hasFailure()){
//                            communicationControl.sendData("MOSA.GET_LOCATION_FUTURE");
                            communicationControl.sendData("MOSA.STOP");
                            actionTurnOnTheAlarm();
                            decisonMaking.actionToDoSomething(listOfFailure.get(0));
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
                    while(stateIFA != StateIFA.DISABLED){                    
                        if (decisonMaking.getStateReplanning()== StateReplanning.DISABLED){
                            stateIFA = StateIFA.DISABLED;
                        }
                        if (stateMonitoring == StateMonitoring.DISABLED){
                            stateIFA = StateIFA.DISABLED;
                        }
//                        if (communicationControl.getStateCommunication() == StateCommunication.DISABLED){
//                            stateIFA = StateIFA.DISABLED;                            
//                        }
                        Thread.sleep(SLEEP_TIME_WAITING_CHANGE_STATES);                     
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
    
    private boolean hasFailure(TypesOfFailures failure){        
        for (Failure fail : listOfFailure){
            if (fail.typeOfFailure == failure){
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
        try {
            boolean print = true;
            File f = new File(config.getDirAlarm());
            final Process comp = Runtime.getRuntime().exec(config.getCmdExecAlarm(), null, f);
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
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] actionTurnOnTheBuzzer()");
        } 
    }
}
