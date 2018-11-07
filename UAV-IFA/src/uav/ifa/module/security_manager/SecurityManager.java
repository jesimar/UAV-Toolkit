package uav.ifa.module.security_manager;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import lib.uav.hardware.aircraft.Drone;
import lib.uav.hardware.aircraft.DroneFixedWing;
import lib.uav.hardware.aircraft.DroneRotaryWing;
import lib.uav.module.actuators.BuzzerControl;
import lib.uav.module.comm.DataAcquisition;
import lib.uav.module.comm.DataAcquisitionS2DK;
import lib.uav.module.sensors.SonarControl;
import lib.uav.module.sensors.TemperatureSensorControl;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.reader.ReaderFileConfigParam;
import lib.uav.struct.Parameter;
import lib.uav.struct.constants.Constants;
import lib.uav.struct.constants.LocalExecPlanner;
import lib.uav.struct.constants.TypeAircraft;
import lib.uav.struct.constants.TypeDataAcquisitionUAV;
import lib.uav.struct.constants.TypeFailure;
import lib.uav.struct.constants.TypeMsgCommunication;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.struct.constants.TypeSystemExecIFA;
import lib.uav.struct.geom.PointGeo;
import lib.uav.struct.states.StateCommunication;
import lib.uav.struct.states.StateMonitoring;
import lib.uav.struct.states.StateReplanning;
import lib.uav.struct.states.StateSystem;
import lib.uav.util.UtilFile;
import lib.uav.util.UtilGeo;
import lib.uav.util.UtilGeom;
import lib.uav.util.UtilRunScript;
import uav.ifa.module.decision_making.DecisionMaking;
import uav.ifa.module.communication.CommunicationMOSA;
import uav.ifa.module.communication.CommunicationGCS;
import uav.ifa.module.decision_making.Controller;
import uav.ifa.struct.Failure;

/**
 * The class manages the safety of the aircraft during the mission.
 * @author Jesimar S. Arantes
 * @since version 1.0.0
 */
public class SecurityManager {

    public static PointGeo pointGeo;
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final CommunicationMOSA communicationMOSA;
    private final CommunicationGCS communicationGCS;
    private final DecisionMaking decisonMaking;
    private final Controller controller;

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
    
    private int killSystem = 0;

    /**
     * Class constructor.
     * @since version 1.0.0
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

        UtilRunScript.execScript("../Scripts/exec-swap-mission.sh " + config.getDirMission());
        
        try {
            pointGeo = UtilGeo.getPointGeoBase(config.getDirFiles() + config.getFileGeoBase());
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgError2("Error [FileNotFoundException] pointGeo");
            ex.printStackTrace();
            System.exit(1);
        }
        
        if (config.getTypeAircraft().equals(TypeAircraft.FIXED_WING)) {
            drone = new DroneFixedWing(config.getUavName(),
                    config.getUavSpeedCruize(), config.getUavSpeedMax(),
                    config.getUavMass(), config.getUavPayload(),
                    config.getUavEndurance());
        } else if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
            drone = new DroneRotaryWing(config.getUavName(),
                    config.getUavSpeedCruize(), config.getUavSpeedMax(),
                    config.getUavMass(), config.getUavPayload(),
                    config.getUavEndurance());
        } else {
            drone = new DroneRotaryWing("iDroneAlpha");
        }
        
        printLogOverhead = UtilFile.createFileLog("log-overhead-ifa", ".csv");
        printLogAircraft = UtilFile.createFileLog("log-aircraft", ".csv");
   
        if (config.getTypeDataAcquisition().equals(TypeDataAcquisitionUAV.DRONEKIT)){
            this.dataAcquisition = new DataAcquisitionS2DK(
                    drone, "IFA", config.getHostS2DK(),
                    config.getPortNetworkS2DK(), printLogOverhead);
        }else{
            dataAcquisition = null;
            System.out.println("Type data acquisition not supported");
            System.exit(1);
        }

        this.decisonMaking = new DecisionMaking(drone, dataAcquisition);
        this.controller = new Controller(drone, dataAcquisition);
        this.communicationMOSA = new CommunicationMOSA(drone);
        this.communicationGCS = new CommunicationGCS(drone, controller);

        if (config.hasSonar()) {
            StandardPrints.printMsgEmph("turn on the sonar sensor");
            sonar = new SonarControl();
            sonar.startSonarSensor();
        }

        if (config.hasTemperatureSensor()) {
            StandardPrints.printMsgEmph("turn on the temperature sensor");
            temperature = new TemperatureSensorControl();
            temperature.startTemperatureSensor();
        }

        stateSystem = StateSystem.INITIALIZING;
        stateMonitoring = StateMonitoring.WAITING;
    }

    /**
     * Initializes the system
     * @since version 1.0.0
     */
    public void init() {
        StandardPrints.printMsgEmph("initializing ...");
        waitingForTheServer();                  //blocked
        configParametersToFlight();             //blocked

        dataAcquisition.getParameters();
        
        altRTL = drone.getInfo().getListParameters().getValue("RTL_ALT")/100.0;
        speedUP = drone.getInfo().getListParameters().getValue("WPNAV_SPEED_UP")/100.0;
        speedHorizontal = drone.getInfo().getListParameters().getValue("WPNAV_SPEED")/100.0;
        speedDN = drone.getInfo().getListParameters().getValue("WPNAV_SPEED_DN")/100.0;
        
//        dataAcquisition.getHomeLocation();

        communicationGCS.startServer();         //Thread
        communicationGCS.receiveData();         //Thread
        monitoringAircraft();                   //Thread
        communicationGCS.sendDataDrone();       //Thread
        
        if (!config.getSystemExecIFA().equals(TypeSystemExecIFA.CONTROLLER)) {
            communicationMOSA.startServer();    //blocked
            communicationMOSA.receiveData();    //Thread        
        }       
        waitingForAnActionOfEmergency();        //Thread                
        monitoringStateMachine();               //Thread

        stateSystem = StateSystem.INITIALIZED;
        StandardPrints.printMsgEmph("initialized ...");
        timeInit = System.currentTimeMillis();
    }

    /**
     * Wait for the server to start
     * @since version 1.0.0
     * @see DataAcquisition#serverIsRunning() 
     */
    private void waitingForTheServer() {
        StandardPrints.printMsgEmph("waiting for the server ...");
        try {
            boolean serverIsRunning = dataAcquisition.serverIsRunning();
            while (!serverIsRunning) {
                StandardPrints.printMsgWarning("waiting for the server uav-s2dk...");
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

    /**
     * Configure the parameters to flight
     * @since version 1.0.0
     */
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

    /**
     * Thread that monitors all the sensors and data of the aircraft and makes the 
     * activation of the safety devices (emergency route, RLT, open parachute, alarm).
     * @since version 1.0.0
     * @see DataAcquisition#getAllInfoSensors()
     */
    private void monitoringAircraft() {
        StandardPrints.printMsgEmph("monitoring aircraft");
        int time = (int) (1000.0 / config.getFreqUpdateDataAP());
        stateMonitoring = StateMonitoring.MONITORING;
        printLogAircraft.println(drone.title());
        dataAcquisition.getAllInfoSensors();
        latHome = drone.getSensors().getGPS().lat;
        lngHome = drone.getSensors().getGPS().lng;
        altHome = drone.getSensors().getBarometer().alt_rel;
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (stateSystem != StateSystem.DISABLED) {
                        timeActual = System.currentTimeMillis();
                        double timeDiff = (timeActual - timeInit) / 1000.0;
                        drone.getInfo().setTime(timeDiff);
                        dataAcquisition.getAllInfoSensors();
                        if (config.hasSonar()) {
                            drone.getSensors().getSonar().distance = sonar.getDistance();
                        }
                        if (config.hasTemperatureSensor()) {
                            drone.getSensors().getTemperature().temperature = temperature.getTemperature();
                        }
                        if (config.hasPowerModule()
                                || !config.getOperationMode()
                                        .equals(TypeOperationMode.REAL_FLIGHT)) {
                            dataAcquisition.getBattery();
                            checkPossibilityOfRTL();
                        }                        

                        checkSystemStatus();

                        printLogAircraft.println(drone.toString());
                        printLogAircraft.flush();
                        Thread.sleep(time);
                        
                        if (killSystem == 0){
                            if (!drone.getSensors().getStatusUAV().armed){
                                killSystem++;
                            }
                        }else if (killSystem == 1){
                            if (drone.getSensors().getStatusUAV().armed){
                                killSystem++;
                            }
                        }else if (killSystem == 2){
                            if (!drone.getSensors().getStatusUAV().armed){
                                killSystem++;
                            }
                        }else if (killSystem == 3){
                            Thread.sleep(1000);
                            StandardPrints.printMsgEmph("Fineshed Mission");
                            StandardPrints.printMsgEmph("Stop MOSA");
                            communicationMOSA.sendData(TypeMsgCommunication.IFA_MOSA_STOP);
                            Thread.sleep(1000);
                            communicationMOSA.close();
                            communicationGCS.close();
                            StandardPrints.printMsgEmph("Terminating IFA system execution");
                            System.exit(0);
                        }
                        
                    }
                } catch (InterruptedException ex) {
                    StandardPrints.printMsgError2("Error [InterruptedException] monitoringAircraft()");
                    ex.printStackTrace();
                    printLogAircraft.close();
                    stateMonitoring = StateMonitoring.DISABLED;
                    checkSystemStatus();
                } catch (Exception ex) {
                    StandardPrints.printMsgError2("Error [Exception] monitoringAircraft()");
                    ex.printStackTrace();
                    stateMonitoring = StateMonitoring.DISABLED;
                    checkSystemStatus();
                }
            }
        });
    }

    /**
     * Check the system status (if the system is working).
     * TO DO: Adicionar as falhas somente se o drone estiver voando, caso 
     * contrário, isso não faz sentido.
     * @since version 1.0.0
     */
    private void checkSystemStatus() {
        if (communicationGCS.hasFailure()
                && !hasFailure(TypeFailure.FAIL_BASED_INSERT_FAILURE)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_BASED_INSERT_FAILURE));
            decisonMaking.setTypeAction(communicationGCS.getTypeAction());
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_BASED_INSERT_FAILURE));
            StandardPrints.printMsgError("FAIL BASED INSERT FAILURE -> Time: " + drone.getInfo().getTime());
        }
        if (communicationGCS.hasFailureBadWeather()
                && !hasFailure(TypeFailure.FAIL_BAD_WEATHER)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_BAD_WEATHER));
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_BAD_WEATHER));
            StandardPrints.printMsgError("FAIL BAD WEATHER -> Time: " + drone.getInfo().getTime());
        }
        if (config.hasPowerModule()
                && drone.getSensors().getBattery().level < config.getLevelMinimumBattery()
                && !hasFailure(TypeFailure.FAIL_LOW_BATTERY)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_LOW_BATTERY));
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_LOW_BATTERY));
            StandardPrints.printMsgError("FAIL LOW BATTERY -> Time: " + drone.getInfo().getTime());
        }
        if (config.hasTemperatureSensor()
                && drone.getSensors().getTemperature().temperature > config.getLevelMaximumTemperature()
                && !hasFailure(TypeFailure.FAIL_BATTERY_OVERHEATING)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_BATTERY_OVERHEATING));
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_BATTERY_OVERHEATING));
            StandardPrints.printMsgError("FAIL BATTERY OVERHEATING -> Time: " + drone.getInfo().getTime());
        }
        if (drone.getSensors().getGPSInfo().fixType != 3
                && !hasFailure(TypeFailure.FAIL_GPS)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_GPS));
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_GPS));
            StandardPrints.printMsgError("FAIL GPS -> Time: " + drone.getInfo().getTime());
        }

        //insercao de falha no IFA para testes em artigo ICAS 2018
//        if (drone.getInfo().getTime() >= 103){//remover isso apos artigo ICAS
//            stateSystem = StateSystem.DISABLED;
//        }
        if (stateSystem == StateSystem.DISABLED
                && !hasFailure(TypeFailure.FAIL_SYSTEM_IFA)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_SYSTEM_IFA));
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_SYSTEM_IFA));
            StandardPrints.printMsgError("FAIL IFA -> Time: " + drone.getInfo().getTime());
        }
        if ((communicationMOSA.getStateCommunication() == StateCommunication.DISABLED
                || communicationMOSA.isMosaDisabled())
                && !hasFailure(TypeFailure.FAIL_SYSTEM_MOSA)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_SYSTEM_MOSA));
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_SYSTEM_MOSA));
            StandardPrints.printMsgError("FAIL MOSA -> Time: " + drone.getInfo().getTime());
        }
//        if (config.hasMotorRotationSensor()
//                && drone.getMotorFailure()
//                && !hasFailure(TypeFailure.FAIL_ENGINE)) {
//            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_ENGINE));
//            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_ENGINE));
//            StandardPrints.printMsgError("FAIL ENGINE -> Time: " + drone.getInfo().getTime());
//        }
        //Descomentar quando for virar produto
//        if (drone.getSensors().getStatusUAV().systemStatus.equals("CRITICAL")
//                && !hasFailure(TypeFailure.FAIL_AP_CRITICAL)) {
//            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_AP_CRITICAL));
//            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_AP_CRITICAL));
//            StandardPrints.printMsgError("FAIL AP CRITICAL -> Time: " + drone.getInfo().getTime());
//        }
        if (drone.getSensors().getStatusUAV().systemStatus.equals("EMERGENCY")
                && !hasFailure(TypeFailure.FAIL_AP_EMERGENCY)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_AP_EMERGENCY));
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_AP_EMERGENCY));
            StandardPrints.printMsgError("FAIL AP EMERGENCY -> Time: " + drone.getInfo().getTime());
        }
        if (drone.getSensors().getStatusUAV().systemStatus.equals("POWEROFF")
                && !hasFailure(TypeFailure.FAIL_AP_POWEROFF)) {
            listOfFailure.add(new Failure(drone, TypeFailure.FAIL_AP_POWEROFF));
            drone.getInfo().setTypeFailure(TypeFailure.getTypeFailure(TypeFailure.FAIL_AP_POWEROFF));
            StandardPrints.printMsgError("FAIL AP POWEROFF -> Time: " + drone.getInfo().getTime());
        }
    }

    /**
     * Thread waiting for an emergency action.
     * TO DO: O sistema trata apenas a primeira falha, fazer o tratamento de falhas consecutivas.
     * TO DO: Melhorar verificação para ver se a aeronave esta voando.
     * @since version 4.0.0
     * @see DecisionMaking
     */
    private void waitingForAnActionOfEmergency() {
        StandardPrints.printMsgEmph("waiting for an action of emergency");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (stateSystem != StateSystem.DISABLED) {
                    try {
                        if (drone.getSensors().getStatusUAV().armed && hasFailure()) {
                            long timeInit = System.currentTimeMillis();        
                            communicationMOSA.sendData(TypeMsgCommunication.IFA_MOSA_STOP);
                            if (config.hasBuzzer()) {
                                StandardPrints.printMsgEmph("turn on the alarm");
                                BuzzerControl buzzer = new BuzzerControl();
                                buzzer.turnOnAlarm();
                            }
                            if (config.getLocalExecReplanner().equals(LocalExecPlanner.ONBOARD)) {
                                decisonMaking.actionForSafetyOnboard(listOfFailure.get(0));
                            } else {
                                decisonMaking.actionForSafetyOffboard(listOfFailure.get(0), communicationGCS);
                            }
                            long timeFinal = System.currentTimeMillis();
                            long time = timeFinal - timeInit;
                            StandardPrints.printMsgBlue("Time for Action Emergency (ms): " + time);
                            break;
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_WAITING_FOR_AN_ACTION);
                    } catch (InterruptedException ex) {

                    }
                }
                if (stateSystem == StateSystem.DISABLED) {
                    if (drone.getSensors().getStatusUAV().armed && hasFailure()) {
                        if (config.hasParachute()) {
                            decisonMaking.openParachute();
                        }
                    }
                }
            }
        });
    }

    /**
     * Thread that monitors state machines.
     * @since version 1.0.0
     */
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

    /**
     * Check if has a specific failure.
     * @return {@code true} if has failure
     *         {@code false} otherwise
     * @since version 1.0.0
     */
    private boolean hasFailure(TypeFailure failure) {
        for (Failure fail : listOfFailure) {
            if (fail.getTypeFailure() == failure) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if has a failure.
     * @return {@code true} if has failure
     *         {@code false} otherwise
     * @since version 1.0.0
     */
    private boolean hasFailure() {
        return listOfFailure.size() > 0;
    }
    
    /**
     * Check the possibility of doing RTL.
     * Calculate also: Estimated Time For RTL
     *                 Estimated Consumption Battery
     *                 Estimated Maximum Distance Reached
     *                 Estimated Maximum Time to Flight
     * Equations: 
     *     speed: v = d/t    ->    t = d/v
     *         t_h = d_h/v_h    (horizontal)
     *         t_v = d_v/v_v    (vertical)
     * @since version 4.0.0
     */
    private void checkPossibilityOfRTL(){
        double lat = drone.getSensors().getGPS().lat;
        double lng = drone.getSensors().getGPS().lng;
        double alt = drone.getSensors().getBarometer().alt_rel;      
        
        double distVerticalUP = Math.abs(alt - altRTL);
        double distHorizontal = UtilGeom.distanceEuclidian(lat, lng, latHome, lngHome)/Constants.ONE_METER;
        double distVerticalDN = Math.abs(altRTL - altHome);
        
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
        drone.getInfo().setEstimatedTimeToDoRTL(estimatedTimeForRTL);
        
        double consumptionBatteryUP;
        double consumptionBatteryDN;
        double consumptionBatteryHorizontal;
        
        double estMaxDist;
        double estMaxTime;
        double level = drone.getSensors().getBattery().level;
        // 0.7: Este fator representa de fato a eficiência da velocidade de fato 
        //percorrida pelo drone em relação a velocidade média.
        if (config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
            consumptionBatteryUP         = distVerticalUP * Constants.EFFICIENCY_VERTICAL_UP_REAL;
            consumptionBatteryDN         = distVerticalDN * Constants.EFFICIENCY_VERTICAL_DOWN_REAL;
            consumptionBatteryHorizontal = distHorizontal * Constants.EFFICIENCY_HORIZONTAL_NAV_REAL;
            estMaxDist = 0.7 * speedHorizontal * level/Constants.EFFICIENCY_HORIZONTAL_NAV_REAL;
            estMaxTime = level/Constants.EFFICIENCY_FLIGHT_TIME_REAL;
        }else{
            consumptionBatteryUP         = distVerticalUP * Constants.EFFICIENCY_VERTICAL_UP_SIMULATED;
            consumptionBatteryDN         = distVerticalDN * Constants.EFFICIENCY_VERTICAL_DOWN_SIMULATED;
            consumptionBatteryHorizontal = distHorizontal * Constants.EFFICIENCY_HORIZONTAL_NAV_SIMULATED;
            estMaxDist = 0.7 * speedHorizontal * level/Constants.EFFICIENCY_HORIZONTAL_NAV_SIMULATED;
            estMaxTime = level/Constants.EFFICIENCY_FLIGHT_TIME_SIMULATED;
        }
        double estimatedConsumptionBat   = 
                consumptionBatteryUP + consumptionBatteryDN +consumptionBatteryHorizontal;
        
        drone.getInfo().setEstimatedConsumptionBatForRTL(estimatedConsumptionBat);
        drone.getInfo().setEstimatedMaxDistReached(estMaxDist);
        drone.getInfo().setEstimatedMaxTimeFlight(estMaxTime);
    }
}
