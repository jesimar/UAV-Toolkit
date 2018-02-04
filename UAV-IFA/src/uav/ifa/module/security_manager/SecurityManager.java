package uav.ifa.module.security_manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_acquisition.DataAcquisition;
import uav.generic.struct.Command;
import uav.generic.struct.Constants;
import uav.generic.struct.Mission;
import uav.generic.struct.Parameter;
import uav.generic.struct.ParameterJSON;
import uav.generic.struct.Waypoint;
import uav.generic.struct.WaypointJSON;
import uav.hardware.aircraft.Ararinha;
import uav.hardware.aircraft.Drone;
import uav.hardware.aircraft.iDroneAlpha;
import uav.ifa.module.decision_making.DecisionMaking;
import uav.ifa.struct.ReaderFileConfig;
import uav.ifa.module.communication_control.CommunicationControl;
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
    private final DecisionMaking decisonMaking;
    private final ReaderFileConfig config;
    
    private PrintStream printLogAircraft; 
    private StateIFA stateIFA;
    private StateMonitoring stateMonitoring;
        
    private long timeInit;
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
        this.dataAcquisition = new DataAcquisition(drone, "IFA");
        this.communicationControl = new CommunicationControl(drone);
        this.decisonMaking = new DecisionMaking(drone, dataAcquisition);
        stateIFA = StateIFA.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }
    
    public void init() {
        StandardPrints.printMsgEmph("initializing ...");                
        timeInit = System.currentTimeMillis();
        
        createFileLogAircraft();                //blocked
        waitingForTheServer();                  //blocked
        configParametersToFlight();             //blocked
        
        dataAcquisition.getParameters();
        dataAcquisition.getHomeLocation();
        
        //Teste de comandos UAV-Services-Dronekit
        try {
            sendCommandsTest();
        } catch (InterruptedException ex) { }
        
        communicationControl.startServerIFA();  //blocked
        communicationControl.receiveData();     //Thread
        monitoringAircraft();                   //Thread
        waitingForAnAction();                   //Thread                
        monitoringStateMachine();               //Thread
                
        stateIFA = StateIFA.INITIALIZED;
        StandardPrints.printMsgEmph("initialized ...");
    }
    
    private void createFileLogAircraft(){
        StandardPrints.printMsgEmph("    create file log aircraft ...");
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
        StandardPrints.printMsgEmph("    waiting for the server ...");
        try{
            boolean serverIsRunning = dataAcquisition.serverIsRunning();
            while(!serverIsRunning){          
                StandardPrints.printMsgWarning("waiting for the server uav-services-dronekit...");
                Thread.sleep(SLEEP_TIME_WAITING_SERVER);
                serverIsRunning = dataAcquisition.serverIsRunning();
            }
            Thread.sleep(3 * SLEEP_TIME_WAITING_SERVER);
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
        StandardPrints.printMsgEmph("    config parameters to flight ...");
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
        if (communicationControl.isMosaDisabled() && 
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
        //CRITICAL: Comportamento esquisito em SITL
//        if (drone.getStatusUAV().systemStatus.equals("CRITICAL") && 
//                !hasFailure(TypesOfFailures.FAIL_AP_CRITICAL)){
//            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_AP_CRITICAL));
//            StandardPrints.printMsgError("FAIL AP CRITICAL -> Time: "+drone.getTime());
//        }
//        if (timeActual - timeInit > config.getTimeToFailure() * 1000 && 
//                !hasFailure(TypesOfFailures.FAIL_BASED_TIME)){
//            listOfFailure.add(new Failure(drone, TypesOfFailures.FAIL_BASED_TIME));
//            StandardPrints.printMsgError("FAIL BASED TIME -> Time: "+drone.getTime());
//        }
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
                        if (hasFailure()){//Verificar se a aeronave esta voando.
                            communicationControl.sendData("MOSA.STOP");
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
                        if (communicationControl.getStateCommunication() == StateCommunication.DISABLED){
                            stateIFA = StateIFA.DISABLED;
                            decisonMaking.openParachute();//aqui pode-se rodar um emergencyLanding()
                        }
                        if (decisonMaking.getStateReplanning()== StateReplanning.DISABLED){
                            stateIFA = StateIFA.DISABLED;
                            decisonMaking.openParachute();
                        }
                        if (stateMonitoring == StateMonitoring.DISABLED){
                            stateIFA = StateIFA.DISABLED;
                            decisonMaking.openParachute();
                        }
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
            if (fail.failure == failure){
                return true;
            }
        }
        return false;
    }
    
    private boolean hasFailure(){        
        return listOfFailure.size() > 0;
    }
    
    private void sendCommandsTest() throws InterruptedException {
        StandardPrints.printMsgEmph("send commands test");
            
        Mission mission = new Mission();
        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 3.0));
        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 5.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));
        dataAcquisition.setMission(mission);
        
        Thread.sleep(13000);
        
        //Teste 1: Set waypoint Takeoff
        WaypointJSON wpt3= new WaypointJSON(new Waypoint(Command.CMD_WAYPOINT, -22.00704778, -47.89853005, 2.0));
        dataAcquisition.setWaypoint(wpt3);
        
        Thread.sleep(7000);

        WaypointJSON wpt4= new WaypointJSON(new Waypoint(Command.CMD_WAYPOINT, -22.00504778, -47.89863005, 2.0));
        dataAcquisition.setWaypoint(wpt4);
        
        Thread.sleep(8000);

        WaypointJSON wpt5= new WaypointJSON(new Waypoint(Command.CMD_WAYPOINT, -22.00404778, -47.89153005, 2.0));
        dataAcquisition.setWaypoint(wpt5);
        
        Thread.sleep(10000);

        WaypointJSON wpt6= new WaypointJSON(new Waypoint(Command.CMD_WAYPOINT, -22.00304778, -47.89453005, 2.0));
        dataAcquisition.setWaypoint(wpt6);
//        //Teste 2: Append waypoint Takeoff
//        WaypointJSON wpt4= new WaypointJSON(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        dataAcquisition.appendWaypoint(wpt4);

        //Teste 3: set parameter 
//        Parameter param1 = new Parameter("RTL_ALT", 1200.0);
//        ParameterJSON ps1 = new ParameterJSON(param1);        
//        dataAcquisition.setParameter(ps1);
//        Parameter param2 = new Parameter("WPNAV_RADIUS", 50.0);
//        ParameterJSON ps2 = new ParameterJSON(param2);
//        dataAcquisition.setParameter(ps2);
        
        //Teste 6: Angulos (Heading)
//        Waypoint w5 = new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0);
//        WaypointJSON wpt5= new WaypointJSON(w5);
//        dataAcquisition.setWaypoint(wpt5);
//        Thread.sleep(5000);
//        Waypoint w6 = new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 2.0);
//        WaypointJSON wpt6= new WaypointJSON(w6);
//        dataAcquisition.appendWaypoint(wpt6);  
//        Heading heading = new Heading(80, Direction.CCW, Angle.RELATIVE);
//        HeadingJSON hj = new HeadingJSON(heading);
//        dataAcquisition.setHeading(hj); 
//        Thread.sleep(1000);
//        Waypoint w7 = new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 2.0);
//        WaypointJSON wpt7= new WaypointJSON(w7);
//        dataAcquisition.appendWaypoint(wpt7);
//        Thread.sleep(2000);
//        Heading heading1 = new Heading(140, Direction.CCW, Angle.RELATIVE);
//        HeadingJSON hj2 = new HeadingJSON(heading1);
//        dataAcquisition.setHeading(hj2);
//        Thread.sleep(3000);        
//        Heading heading2 = new Heading(193, Direction.CCW, Angle.RELATIVE);
//        HeadingJSON hj3 = new HeadingJSON(heading2);
//        dataAcquisition.setHeading(hj3);

        //Teste 7: Missao: 
//        Mission mission = new Mission();
//        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 5.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));
//        dataAcquisition.setMission(mission);
        
        //Teste 8: Velocidade:
//        Mission mission = new Mission();
//        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.01587424, -47.89874454, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 5.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00604778, -47.89853005, 2.0));
//        dataAcquisition.setMission(mission);
//        Thread.sleep(5000);
//        dataAcquisition.setVelocity(20);
//        Thread.sleep(8000);
//        dataAcquisition.setVelocity(1);
//        Thread.sleep(8000);
//        dataAcquisition.setVelocity(10);
//        Thread.sleep(8000);
//        dataAcquisition.setVelocity(3);
//        Thread.sleep(8000);

        //Teste 9: Change Modes Autopilot:
//        dataAcquisition.setMode("GUIDED");
//        Thread.sleep(4000);
//        dataAcquisition.setMode("AUTO");
//        Thread.sleep(4000);
//        dataAcquisition.setMode("CIRCLE");
//        Thread.sleep(4000);
//        dataAcquisition.setMode("LOITER");


        //Teste 10: //Teste change mode in Flight //Este teste nao esta legal nao.     
//        Mission mission = new Mission();
//        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 3.0));
//        dataAcquisition.setMission(mission);
//        Thread.sleep(15000);
//        dataAcquisition.setMode("CIRCLE");
//        Thread.sleep(35000);
//        dataAcquisition.setMode("RTL");
            
        //Teste 8:         
//        Mission mission = new Mission();
//        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 5.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_LAND, 0.0, 0.0, 0.0));
//        dataAcquisition.setMission(mission);
        
//        Waypoint w8 = new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0);
//        WaypointJSON wpt8 = new WaypointJSON(w8);
//        dataAcquisition.setWaypoint(wpt8);
//        Thread.sleep(8000);
//        Waypoint w9 = new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 6.0);
//        WaypointJSON wpt9 = new WaypointJSON(w9);
//        dataAcquisition.appendWaypoint(wpt9);
//        Thread.sleep(8000);
//        Waypoint w10 = new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0);
//        WaypointJSON wpt10 = new WaypointJSON(w10);
//        dataAcquisition.appendWaypoint(wpt10);

//        for (int i = 0; i < 1800; i++){
//            for (int j = 1; j <= 2; j++){  
//                Heading heading = new Heading(140, Direction.CCW, Angle.ABSOLUTE);
//                HeadingJSON hj = new HeadingJSON(heading);
//                dataAcquisition.setHeading(hj);
//                Thread.sleep(1000);
//            }
//        }
        
//        dataAcquisition.setHeading(45);
        
        
//        WaypointSimple wpt7= new WaypointSimple();
//        wpt7.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 2.0));
//        dataAcquisition.setWaypoint(wpt7);
        
//        Mission mission = new Mission();
//        mission.addWaypoint(new Waypoint(Command.CMD_TAKEOFF, 0.0, 0.0, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587424, -47.89874454, 3.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00587325, -47.89854124, 5.0));
//        dataAcquisition.setMission(mission);
//        try {
//            Thread.sleep(7000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        WaypointSimple wpt5= new WaypointSimple();
//        wpt5.addWaypoint(new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 3.0));
//        dataAcquisition.setWaypoint(wpt5);
        
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        WaypointSimple wpt2= new WaypointSimple();
//        wpt2.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.01587424417797, -47.99874454308930, 3.0));
//        dataAcquisition.setWaypoint(wpt2);
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        WaypointSimple wpt3= new WaypointSimple();
//        wpt3.addWaypoint(new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0));
//        dataAcquisition.setWaypoint(wpt3);
                 
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        Mission mission2 = new Mission();
//        mission2.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00594843,-47.89839733, 2.0));
//        mission2.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00595094,-47.89864182, 5.0));
//        mission2.addWaypoint(new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0));
//
//        dataAcquisition.setMission(mission2); 
//        WaypointSimple wpt = new WaypointSimple();
//        wpt.addWaypoint(new Waypoint(Command.CMD_LAND, -22.00587325146283, -47.89854124629112, 0.0));
//        dataAcquisition.appendWaypoint(wpt);
        
//        WaypointSimple wpt2 = new WaypointSimple();
//        wpt2.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));
//        dataAcquisition.appendWaypoint(wpt2);

//        mission.addWaypoint(new Waypoint(Command.CMD_RTL, 0, 0, 0.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_LAND, -22.00587424417797, -47.89874454308930, 0.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_RTL, 0, 0, 0.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_LAND, -22.00587325146283, -47.89854124629112, 0.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, -22.00605030421996, -47.89872355493285, 1.0));
//        mission.addWaypoint(new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0));
    }
}
