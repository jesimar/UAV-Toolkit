package uav.ifa.module.security_manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_communication.DataCommunication;
import uav.generic.module.sensors_actuators.BuzzerControl;
import uav.generic.hardware.aircraft.FixedWing;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.hardware.aircraft.RotaryWing;
import uav.generic.module.sensors_actuators.SonarControl;
import uav.generic.module.sensors_actuators.TemperatureSensorControl;
import uav.generic.struct.Parameter;
import uav.generic.struct.reader.ReaderFileConfig;
import uav.generic.struct.reader.ReaderFileConfigParam;
import uav.generic.struct.constants.TypeAircraft;
import uav.generic.struct.constants.TypeFailure;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeLocalExecPlanner;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.struct.constants.TypeSystemExecIFA;
import uav.generic.struct.geom.PointGeo;
import uav.generic.struct.states.StateCommunication;
import uav.generic.struct.states.StateSystem;
import uav.generic.struct.states.StateMonitoring;
import uav.generic.struct.states.StateReplanning;
import uav.generic.util.UtilGeo;
import uav.generic.util.UtilGeom;
import uav.ifa.module.decision_making.DecisionMaking;
import uav.ifa.module.communication_control.CommunicationMOSA;
import uav.ifa.module.communication_control.CommunicationGCS;
import uav.ifa.struct.Failure;

/**
 * Classe que gerencia a segurança da aeronave durante toda a missão.
 *
 * @author Jesimar S. Arantes
 */
public class SecurityManager {

    public static PointGeo pointGeo;
    private final Drone drone;
    private final DataCommunication dataAcquisition;
    private final CommunicationMOSA communicationMOSA;
    private final CommunicationGCS communicationGCS;
    private final DecisionMaking decisonMaking;

    private final ReaderFileConfig config;
    private final ReaderFileConfigParam configParam;

    private PrintStream printLogAircraft;
    private PrintStream printLogOverhead;

    private StateSystem stateSystem;
    private StateMonitoring stateMonitoring;

    private SonarControl sonar;
    private TemperatureSensorControl temperature;

    private long timeInit;
    private long timeActual;

    private final List<Failure> listOfFailure = new LinkedList<>();
    
    private double latHome;
    private double lngHome;
    private double altHome;
    private double altRTL;
    private double speedUP;
    private double speedHorizontal;
    private double speedDN;

    /**
     * Class constructor.
     */
    public SecurityManager() {
        timeInit = System.currentTimeMillis();
        
        this.config = ReaderFileConfig.getInstance();
        if (!config.read()) {
            System.exit(1);
        }
        if (!config.checkReadFields()) {
            System.exit(1);
        }
        if (!config.parseToVariables()) {
            System.exit(1);
        }
        this.configParam = ReaderFileConfigParam.getInstance();

        execScript("../Scripts/exec-swap-mission.sh " + config.getDirMission());
        
        try {
            pointGeo = UtilGeo.getPointGeo(config.getDirFiles() + config.getFileGeoBase());
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException] pointGeo");
            ex.printStackTrace();
            System.exit(1);
        }
        
        if (config.getTypeAircraft().equals(TypeAircraft.FIXED_WING)) {
            drone = new FixedWing(config.getUavName(),
                    config.getUavSpeedCruize(), config.getUavSpeedMax(),
                    config.getUavMass(), config.getUavPayload(),
                    config.getUavEndurance());
        } else if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
            drone = new RotaryWing(config.getUavName(),
                    config.getUavSpeedCruize(), config.getUavSpeedMax(),
                    config.getUavMass(), config.getUavPayload(),
                    config.getUavEndurance());
        } else {
            drone = new RotaryWing("iDroneAlpha");
        }

        createFileLogOverhead();
        this.dataAcquisition = new DataCommunication(
                drone, "IFA", config.getHostS2DK(),
                config.getPortNetworkS2DK(), printLogOverhead);
        this.decisonMaking = new DecisionMaking(drone, dataAcquisition);
        this.communicationMOSA = new CommunicationMOSA(drone);
        this.communicationGCS = new CommunicationGCS(drone, decisonMaking);

        if (config.hasSonar()) {
            startSonarSensor();
        }

        if (config.hasTemperatureSensor()) {
            startTemperatureSensor();
        }

        stateSystem = StateSystem.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }

    public void init() {
        StandardPrints.printMsgEmph("initializing ...");
        createFileLogAircraft();                //blocked
        waitingForTheServer();                  //blocked
        configParametersToFlight();             //blocked

        dataAcquisition.getParameters();
        
        altRTL = drone.getListParameters().getValue("RTL_ALT")/100.0;
        speedUP = drone.getListParameters().getValue("WPNAV_SPEED_UP")/100.0;
        speedHorizontal = drone.getListParameters().getValue("WPNAV_SPEED")/100.0;
        speedDN = drone.getListParameters().getValue("WPNAV_SPEED_DN")/100.0;
        
//        dataAcquisition.getHomeLocation();

        communicationGCS.startServerIFA();      //Thread
        communicationGCS.receiveData();         //Thread

        if (!config.getSystemExecIFA().equals(TypeSystemExecIFA.CONTROLLER)) {
            communicationMOSA.startServerIFA(); //blocked
            communicationMOSA.receiveData();    //Thread        
        }
        monitoringAircraft();                   //Thread

        communicationGCS.sendDataDrone();       //Thread

        waitingForAnAction();                   //Thread                
        monitoringStateMachine();               //Thread

        stateSystem = StateSystem.INITIALIZED;
        StandardPrints.printMsgEmph("initialized ...");
        timeInit = System.currentTimeMillis();
    }

    private void createFileLogOverhead() {
        try {
            int i = 0;
            File file;
            do {
                i++;
                file = new File("log-overhead-ifa" + i + ".csv");
            } while (file.exists());
            printLogOverhead = new PrintStream(file);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: createFileLogOverhead()");
            ex.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            StandardPrints.printMsgError2("Error [Exception]: createFileLogOverhead()");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void createFileLogAircraft() {
        StandardPrints.printMsgEmph("create file log aircraft ...");
        try {
            int i = 0;
            File file;
            do {
                i++;
                file = new File("log-aircraft" + i + ".csv");
            } while (file.exists());
            printLogAircraft = new PrintStream(file);
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException]: createFileLogAircraft()");
            ex.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            StandardPrints.printMsgError2("Error [Exception]: createFileLogAircraft()");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void waitingForTheServer() {
        StandardPrints.printMsgEmph("waiting for the server ...");
        try {
            boolean serverIsRunning = dataAcquisition.serverIsRunning();
            while (!serverIsRunning) {
                StandardPrints.printMsgWarning("waiting for the server uav-services-dronekit...");
                Thread.sleep(Constants.TIME_TO_SLEEP_WAITING_SERVER);
                serverIsRunning = dataAcquisition.serverIsRunning();
            }
            Thread.sleep(Constants.TIME_TO_SLEEP_WAITING_SERVER);
        } catch (InterruptedException ex) {
            StandardPrints.printMsgError2("Error [InterruptedException]: waitingForTheServer()");
            ex.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            StandardPrints.printMsgError2("Error [Exception]: waitingForTheServer()");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void configParametersToFlight() {
        StandardPrints.printMsgEmph("config parameters to flight ...");
        try {
            configParam.read();
            for (int i = 0; i < configParam.size(); i++) {
                String name = configParam.getName(i);
                double value = configParam.getValue(i);
                Parameter param = new Parameter(name, value);
                dataAcquisition.setParameter(param);
            }
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException] configParametersToFlight()");
            ex.printStackTrace();
            System.exit(1);
        } catch (Exception ex) {
            StandardPrints.printMsgError2("Error [Exception] configParametersToFlight()");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private void monitoringAircraft() {
        StandardPrints.printMsgEmph("monitoring aircraft");
        int time = (int) (1000.0 / config.getFreqUpdateDataAP());
        stateMonitoring = StateMonitoring.MONITORING;
        printLogAircraft.println(drone.title());
        dataAcquisition.getAllInfoSensors();
        latHome = drone.getGPS().lat;
        lngHome = drone.getGPS().lng;
        altHome = drone.getBarometer().alt_rel;
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (stateSystem != StateSystem.DISABLED) {
                        timeActual = System.currentTimeMillis();
                        double timeDiff = (timeActual - timeInit) / 1000.0;
                        drone.setTime(timeDiff);
                        dataAcquisition.getAllInfoSensors();
                        if (config.hasSonar()) {
                            drone.getSonar().distance = sonar.getDistance();
                        }
                        if (config.hasTemperatureSensor()) {
                            drone.getTemperature().temperature = temperature.getTemperature();
                        }
                        if (config.hasPowerModule()
                                || !config.getOperationMode()
                                        .equals(TypeOperationMode.REAL_FLIGHT)) {
                            dataAcquisition.getBattery();
                            checkPossibilityOfRTL();
                        }

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
                } catch (Exception ex) {
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
    private void checkStatusSystem() {
        if (communicationGCS.hasFailure()
                && !hasFailure(TypeFailure.FAIL_BASED_INSERT_FAILURE)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_BASED_INSERT_FAILURE));
            decisonMaking.setTypeAction(communicationGCS.getTypeAction());
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_BASED_INSERT_FAILURE));
            StandardPrints.printMsgError("FAIL BASED INSERT FAILURE -> Time: " + drone.getTime());
        }
        if (communicationGCS.hasFailureBadWeather()
                && !hasFailure(TypeFailure.FAIL_BAD_WEATHER)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_BAD_WEATHER));
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_BAD_WEATHER));
            StandardPrints.printMsgError("FAIL BAD WEATHER -> Time: " + drone.getTime());
        }
        if (config.hasPowerModule()
                && drone.getBattery().level < config.getLevelMinimumBattery()
                && !hasFailure(TypeFailure.FAIL_LOW_BATTERY)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_LOW_BATTERY));
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_LOW_BATTERY));
            StandardPrints.printMsgError("FAIL LOW BATTERY -> Time: " + drone.getTime());
        }
        if (config.hasTemperatureSensor()
                && drone.getTemperature().temperature > config.getLevelMaximumTemperature()
                && !hasFailure(TypeFailure.FAIL_BATTERY_OVERHEATING)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_BATTERY_OVERHEATING));
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_BATTERY_OVERHEATING));
            StandardPrints.printMsgError("FAIL BATTERY OVERHEATING -> Time: " + drone.getTime());
        }
        if (drone.getGPSInfo().fixType != 3
                && !hasFailure(TypeFailure.FAIL_GPS)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_GPS));
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_GPS));
            StandardPrints.printMsgError("FAIL GPS -> Time: " + drone.getTime());
        }

        //insercao de falha no IFA para testes em artigo ICAS 2018
//        if (drone.getTime() >= 103){//remover isso apos artigo ICAS
//            stateSystem = StateSystem.DISABLED;
//        }
        if (stateSystem == StateSystem.DISABLED
                && !hasFailure(TypeFailure.FAIL_SYSTEM_IFA)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_SYSTEM_IFA));
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_SYSTEM_IFA));
            StandardPrints.printMsgError("FAIL IFA -> Time: " + drone.getTime());
        }
        if ((communicationMOSA.getStateCommunication() == StateCommunication.DISABLED
                || communicationMOSA.isMosaDisabled())
                && !hasFailure(TypeFailure.FAIL_SYSTEM_MOSA)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_SYSTEM_MOSA));
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_SYSTEM_MOSA));
            StandardPrints.printMsgError("FAIL MOSA -> Time: " + drone.getTime());
        }
//        if (configGlobal.hasMotorRotationSensor()
//                && drone.getMotorFailure()
//                && !hasFailure(TypeFailure.FAIL_ENGINE)) {
//            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_ENGINE));
//            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_ENGINE));
//            StandardPrints.printMsgError("FAIL ENGINE -> Time: " + drone.getTime());
//        }
        //Descomentar quando for virar produto
//        if (drone.getStatusUAV().systemStatus.equals("CRITICAL")
//                && !hasFailure(TypeFailure.FAIL_AP_CRITICAL)) {
//            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_AP_CRITICAL));
//            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_AP_CRITICAL));
//            StandardPrints.printMsgError("FAIL AP CRITICAL -> Time: " + drone.getTime());
//        }
        if (drone.getStatusUAV().systemStatus.equals("EMERGENCY")
                && !hasFailure(TypeFailure.FAIL_AP_EMERGENCY)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_AP_EMERGENCY));
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_AP_EMERGENCY));
            StandardPrints.printMsgError("FAIL AP EMERGENCY -> Time: " + drone.getTime());
        }
        if (drone.getStatusUAV().systemStatus.equals("POWEROFF")
                && !hasFailure(TypeFailure.FAIL_AP_POWEROFF)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_AP_POWEROFF));
            drone.setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_AP_POWEROFF));
            StandardPrints.printMsgError("FAIL AP POWEROFF -> Time: " + drone.getTime());
        }
    }

    //melhorar: por enquanto esta tratando apenas a primeira falha.
    //melhorar: o sistema so tenta planejar algo sobre a 1 falha encontrada.
    private void waitingForAnAction() {
        StandardPrints.printMsgEmph("waiting for an action");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (stateSystem != StateSystem.DISABLED) {
                    try {
                        //Melhorar verificacao para ver se a aeronave esta voando.
                        if (drone.getStatusUAV().armed && hasFailure()) {
                            communicationMOSA.sendData(TypeMsgCommunication.IFA_MOSA_STOP);
                            if (config.hasBuzzer()) {
                                actionTurnOnTheAlarm();
                            }
                            if (config.getLocalExecReplanner().equals(TypeLocalExecPlanner.ONBOARD)) {
                                decisonMaking.actionToDoSomethingOnboard(listOfFailure.get(0));
                            } else {
                                decisonMaking.actionToDoSomethingOffboard(listOfFailure.get(0), communicationGCS);
                            }
                            break;
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_WAITING_FOR_AN_ACTION);
                    } catch (InterruptedException ex) {

                    }
                }
                if (stateSystem == StateSystem.DISABLED) {
                    if (drone.getStatusUAV().armed && hasFailure()) {
                        if (config.hasParachute()) {
                            decisonMaking.openParachute();
                        }
                    }
                }
            }
        });
    }

    private void monitoringStateMachine() {
        StandardPrints.printMsgEmph("monitoring the state machine");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (stateSystem != StateSystem.DISABLED) {
                        if (decisonMaking.getStateReplanning() == StateReplanning.DISABLED) {
                            stateSystem = StateSystem.DISABLED;
                        }
                        if (stateMonitoring == StateMonitoring.DISABLED) {
                            stateSystem = StateSystem.DISABLED;
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_MONITORING_STATE_MACHINE);
                    }
                } catch (InterruptedException ex) {

                }
                try {
                    communicationMOSA.close();
                    communicationGCS.close();
                } catch (Exception ex) {

                }
            }
        });
    }

    private boolean hasFailure(TypeFailure failure) {
        for (Failure fail : listOfFailure) {
            if (fail.getTypeFailure() == failure) {
                return true;
            }
        }
        return false;
    }

    private boolean hasFailure() {
        return listOfFailure.size() > 0;
    }

    private void actionTurnOnTheAlarm() {
        StandardPrints.printMsgEmph("turn on the alarm");
        BuzzerControl buzzer = new BuzzerControl();
        buzzer.turnOnAlarm();
    }

    private void startSonarSensor() {
        StandardPrints.printMsgEmph("turn on the sonar sensor");
        sonar = new SonarControl();
        sonar.startSonarSensor();
    }

    private void startTemperatureSensor() {
        StandardPrints.printMsgEmph("turn on the temperature sensor");
        temperature = new TemperatureSensorControl();
        temperature.startTemperatureSensor();
    }

    private void execScript(String cmd) {
        try {
            Process proc = Runtime.getRuntime().exec(cmd);
            BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            while (read.ready()) {
                System.out.println(read.readLine());
            }
        }catch (IOException ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    private void checkPossibilityOfRTL(){
        double lat = drone.getGPS().lat;
        double lng = drone.getGPS().lng;
        double alt = drone.getBarometer().alt_rel;      
        
        double distVerticalUP = Math.abs(alt - altRTL);
        double distHorizontal = UtilGeom.distanceEuclidian(lat, lng, latHome, lngHome)/Constants.ONE_METER;
        double distVerticalDN = Math.abs(altRTL - altHome);
        
        //Velocidade: v = d/t      ->   t = d/v
        //    t_h = d_h/v_h  (horizontal)
        //    t_v = d_v/v_v  (vertical)
        double estimatedTimeForRTLverticalUp = distVerticalUP/speedUP;
        double estimatedTimeForRTLhorizontal = distHorizontal/speedHorizontal;
        double estimatedTimeForRTLverticalDn = distVerticalDN/speedDN;
        
        double estimatedTimeForRTL = estimatedTimeForRTLverticalUp + estimatedTimeForRTLhorizontal +
                estimatedTimeForRTLverticalDn;
        
        //5: representa (5 segundos) que em meus testes o tempo que leva para o drone 
        //   desarmar completamente.
        //3: representa (3 segundos) que em meus testes o tempo que leva para o drone 
        //   trocar de waypoint.
        //1.18: representa (18% a mais de tempo) que em meus testes foi a margem que tive 
        //   que adicionar a estimativa de tempo, pois as mesmas foram baseadas na 
        //   velocidade máxima e o drone, as vezes, voa em uma velocidade inferior a esta.
        //OBS: Estes dados foram obtidos em experimentos SITL
        estimatedTimeForRTL = estimatedTimeForRTL * 1.18 + 5 + 3;
        drone.setEstimatedTimeToDoRTL(estimatedTimeForRTL);
        
        double consumptionBatteryUP;
        double consumptionBatteryDN;
        double consumptionBatteryHorizontal;
        if (config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
            consumptionBatteryUP         = distVerticalUP * Constants.EFFICIENCY_VERTICAL_UP_REAL;
            consumptionBatteryDN         = distVerticalDN * Constants.EFFICIENCY_VERTICAL_DOWN_REAL;
            consumptionBatteryHorizontal = distHorizontal * Constants.EFFICIENCY_HORIZONTAL_NAV_REAL;
        }else{
            consumptionBatteryUP         = distVerticalUP * Constants.EFFICIENCY_VERTICAL_UP_SIMULATED;
            consumptionBatteryDN         = distVerticalDN * Constants.EFFICIENCY_VERTICAL_DOWN_SIMULATED;
            consumptionBatteryHorizontal = distHorizontal * Constants.EFFICIENCY_HORIZONTAL_NAV_SIMULATED;
        }
        double estimatedConsumptionBat   = 
                consumptionBatteryUP + consumptionBatteryDN +consumptionBatteryHorizontal;
        
        drone.setEstimatedConsumptionBatForRTL(estimatedConsumptionBat);  
    }
}
