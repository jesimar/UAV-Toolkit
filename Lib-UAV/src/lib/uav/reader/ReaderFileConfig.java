package lib.uav.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import lib.color.StandardPrints;
import lib.uav.struct.constants.TypeAP;
import lib.uav.struct.constants.TypeAircraft;
import lib.uav.struct.constants.TypeAltitudeDecay;
import lib.uav.struct.constants.TypeCC;
import lib.uav.struct.constants.LocalExecPlanner;
import lib.uav.struct.constants.TypeDataAcquisitionUAV;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.struct.constants.TypePlanner;
import lib.uav.struct.constants.TypeReplanner;
import lib.uav.struct.constants.TypeSystemExecIFA;
import lib.uav.struct.constants.TypeSystemExecMOSA;

/**
 * The class reads the file with configurations/properties of the MOSA and IFA systems.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class ReaderFileConfig {
    
    private static final ReaderFileConfig instance = new ReaderFileConfig();
    
    private final String nameFile = "../Modules-Global/config-global.properties";
    private Properties prop;
    
    //global
    private String typeCC;
    private String typeAP;
    private String operationMode;
    private String typeAircraft;
    private String typeDataAcquisition;
    private String dirMission;
    private double altRelMission;
    private double freqUpdateDataAP;
    private boolean hasRouteSimplifier;
    private String dirRouteSimplifier;
    private String factorRouteSimplifier;
    
    private String dirFiles;
    private String fileGeoBase;
    private String fileFeatureMission;
    
    //communication
    private String hostIFA;
    private String hostMOSA;
    private String hostS2DK;
    private int portNetworkIFAandMOSA;
    private int portNetworkIFAandGCS;
    private int portNetworkMOSAandGCS;
    private int portNetworkS2DK;
        
    //sensor
    private boolean hasGPS;
    private boolean hasCamera;
    private String dirCamera;
    private String timeVideo;
    private String numberPhotoInSequence;
    private String delayPhotoInSequence;
    private boolean hasSonar;
    private String dirSonar;
    private String pinSonarTrig;
    private String pinSonarEcho;
    private boolean hasPowerModule;
    private int levelMinimumBattery;
    private boolean hasTemperatureSensor;
    private String dirTemperatureSensor;
    private int levelMaximumTemperature;
    private String pinTemperatureCLK;
    private String pinTemperatureCS;
    private String pinTemperatureSO;
    
    //atuactor
    private boolean hasBuzzer;
    private String dirBuzzer;
    private String pinBuzzer;
    private boolean hasParachute;
    private String dirParachute;
    private String pinParachute;
    private boolean hasLED;
    private String dirLED;
    private String pinLED;
    private String delayLED;
    private boolean hasSpraying;
    private String dirSpraying;
    private String pinSpraying;
    
    //accessory
    private boolean hasTelemetry;
    
    //aircraft 
    private String uavName;
    private double uavSpeedCruize;
    private double uavSpeedMax;
    private double uavMass;
    private double uavPayload;
    private double uavEndurance;
    
    //gcs
    private boolean hasGoogleMaps;
    private boolean hasDB;
    private String hostOD;
    private String portOD;
    private String userEmail;
    
    //ifa
    //global
    private String systemExecIFA;
    
    //replanner
    private String localExecReplanner;
    private String methodReplanner;
    private String typeReplanner;
    private String dirReplanner;
    private String cmdExecReplanner;    
    private String timeExecReplanner;
    private String numberWaypointsReplanner;
    private String deltaReplanner; 
    private String typeAltitudeDecayReplanner;
    
    //fixed route
    private String dirFixedRouteIFA;
    private String fileFixedRouteIFA;
    
    //mosa
    //global
    private String systemExecMOSA;
    
    //planner
    private String localExecPlanner;
    private String methodPlanner;
    private String typePlanner;
    private String dirPlanner;
    private String cmdExecPlanner;
    
    //hga4m
    private String localExecProcessingPlannerHGA4m;
    private String fileMissionPlannerHGA4m;    
    private String timeExecPlannerHGA4m;
    private String deltaPlannerHGA4m;
    private String maxVelocityPlannerHGA4m;
    private String maxControlPlannerHGA4m;
    private String stdPositionPlannerHGA4m;
    
    //ccqsp4m
    private String waypointsPlannerCCQSP4m;
    private String timeHorizonPlannerCCQSP4m;
    private String deltaPlannerCCQSP4m;
    private String stepsPlannerCCQSP4m;
    private String stdPositionPlannerCCQSP4m;
    
    //astar4m
    private String fileMissionPlannerAStar4m;
    
    //pathplanner4m
    private String fileMissionPlannerGPathPlanner4m;
    
    //fixed route    
    private String dirFixedRouteMOSA;
    private String fileFixedRouteMOSA;   
    private boolean isDynamicFixedRouteMOSA;
    private String fileFixedRouteDynMOSA;
    
    //system 
    private double maxAltController;
    private double minAltController;
    private double displacFactorController;
    private double horizontalErrorGPS;
    private double verticalErrorBarometer;
    
    //behavior
    private String dirBehavior;
    private String typeBehavior;
    private String discretizationBehavior;
    private String radiusCircleBehavior;
    private String baseTriangleBehavior;
    private String baseRectangleBehavior;
    
    /**
     * Class constructor.
     * @since version 4.0.0
     */
    private ReaderFileConfig() {
        
    }
    
    /**
     * Gets a instance this class (singleton pattern).
     * @return the instance this class
     * @since version 4.0.0
     */
    public static ReaderFileConfig getInstance() {
        return instance;
    }
    
    /**
     * Read the properties file
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean read(){
        try {
            prop = new Properties();
            prop.load(new FileInputStream(nameFile));
            
            //global
            typeCC                = prop.getProperty("prop.global.type_cc");
            typeAP                = prop.getProperty("prop.global.type_ap");
            operationMode         = prop.getProperty("prop.global.operation_mode");
            typeAircraft          = prop.getProperty("prop.global.type_aircraft");
            typeDataAcquisition   = prop.getProperty("prop.global.type_data_acquisition");
            dirMission            = prop.getProperty("prop.global.mission.dir");
            altRelMission         = Double.parseDouble(prop.getProperty("prop.global.mission.altitude_relative"));
            freqUpdateDataAP      = Double.parseDouble(prop.getProperty("prop.global.freq_update_data_ap"));
            hasRouteSimplifier    = Boolean.parseBoolean(prop.getProperty("prop.global.has_route_simplifier"));
            dirRouteSimplifier    = prop.getProperty("prop.route_simplifier.dir");
            factorRouteSimplifier = prop.getProperty("prop.route_simplifier.factor");
                                          
            dirFiles              = prop.getProperty("prop.global.dir_files");
            fileFeatureMission    = prop.getProperty("prop.global.file.feature_mission");
            fileGeoBase           = prop.getProperty("prop.global.file.geo_base");
            
            //communication
            hostIFA               = prop.getProperty("prop.global.comm.host_ifa");
            hostMOSA              = prop.getProperty("prop.global.comm.host_mosa");
            hostS2DK              = prop.getProperty("prop.global.comm.host_s2dk");
            portNetworkIFAandMOSA = Integer.parseInt(prop.getProperty("prop.global.comm.port_network_ifa_mosa"));
            portNetworkIFAandGCS  = Integer.parseInt(prop.getProperty("prop.global.comm.port_network_ifa_gcs"));
            portNetworkMOSAandGCS = Integer.parseInt(prop.getProperty("prop.global.comm.port_network_mosa_gcs"));
            portNetworkS2DK       = Integer.parseInt(prop.getProperty("prop.global.comm.port_network_s2dk"));
            
            //modules hardware
            hasGPS                = Boolean.parseBoolean(prop.getProperty("prop.hw.sensor.has_gps"));
            hasCamera             = Boolean.parseBoolean(prop.getProperty("prop.hw.sensor.has_camera"));
            hasSonar              = Boolean.parseBoolean(prop.getProperty("prop.hw.sensor.has_sonar"));
            hasPowerModule        = Boolean.parseBoolean(prop.getProperty("prop.hw.sensor.has_powermodule"));
            hasTemperatureSensor  = Boolean.parseBoolean(prop.getProperty("prop.hw.sensor.has_temperature_sensor"));
            hasBuzzer             = Boolean.parseBoolean(prop.getProperty("prop.hw.actuator.has_buzzer"));
            hasParachute          = Boolean.parseBoolean(prop.getProperty("prop.hw.actuator.has_parachute"));
            hasLED                = Boolean.parseBoolean(prop.getProperty("prop.hw.actuator.has_led"));
            hasSpraying           = Boolean.parseBoolean(prop.getProperty("prop.hw.actuator.has_spraying"));      
            hasTelemetry          = Boolean.parseBoolean(prop.getProperty("prop.hw.accessory.has_telemetry"));
            
            //sensor
            dirCamera             = prop.getProperty("prop.hw.sensor.camera.dir");
            timeVideo             = prop.getProperty("prop.hw.sensor.camera.video.time");
            numberPhotoInSequence = prop.getProperty("prop.hw.sensor.camera.photo_in_sequence.number");
            delayPhotoInSequence  = prop.getProperty("prop.hw.sensor.camera.photo_in_sequence.delay");
            dirSonar              = prop.getProperty("prop.hw.sensor.sonar.dir");
            pinSonarTrig          = prop.getProperty("prop.hw.sensor.sonar.pin_trig");
            pinSonarEcho          = prop.getProperty("prop.hw.sensor.sonar.pin_echo");
            levelMinimumBattery   = Integer.parseInt(prop.getProperty("prop.hw.sensor.powermodule.level_min_battery"));
            dirTemperatureSensor  = prop.getProperty("prop.hw.sensor.temperature.dir");
            levelMaximumTemperature = Integer.parseInt(prop.getProperty("prop.hw.sensor.temperature.level_max_temperature"));
            pinTemperatureCLK     = prop.getProperty("prop.hw.sensor.temperature.pin_clk");
            pinTemperatureCS      = prop.getProperty("prop.hw.sensor.temperature.pin_cs");
            pinTemperatureSO      = prop.getProperty("prop.hw.sensor.temperature.pin_so");
            
            //actuators
            dirBuzzer             = prop.getProperty("prop.hw.actuator.buzzer.dir");
            pinBuzzer             = prop.getProperty("prop.hw.actuator.buzzer.pin");
            dirParachute          = prop.getProperty("prop.hw.actuator.parachute.dir");
            pinParachute          = prop.getProperty("prop.hw.actuator.parachute.pin");
            dirLED                = prop.getProperty("prop.hw.actuator.led.dir");
            pinLED                = prop.getProperty("prop.hw.actuator.led.pin");
            delayLED              = prop.getProperty("prop.hw.actuator.led.delay");
            dirSpraying           = prop.getProperty("prop.hw.actuator.spraying.dir");
            pinSpraying           = prop.getProperty("prop.hw.actuator.spraying.pin");
            
            //aircraft
            uavName               = prop.getProperty("prop.aircraft.name");
            uavSpeedCruize        = Double.parseDouble(prop.getProperty("prop.aircraft.speed_cruize"));
            uavSpeedMax           = Double.parseDouble(prop.getProperty("prop.aircraft.speed_max"));
            uavMass               = Double.parseDouble(prop.getProperty("prop.aircraft.mass"));
            uavPayload            = Double.parseDouble(prop.getProperty("prop.aircraft.payload"));
            uavEndurance          = Double.parseDouble(prop.getProperty("prop.aircraft.endurance"));
            
            //modules software
            //gcs
            hasGoogleMaps         = Boolean.parseBoolean(prop.getProperty("prop.gcs.internet.has_googlemaps"));
            hasDB                 = Boolean.parseBoolean(prop.getProperty("prop.gcs.od.has_db"));
            hostOD                = prop.getProperty("prop.gcs.od.host_od");
            portOD                = prop.getProperty("prop.gcs.od.port_od_gcs");
            userEmail             = prop.getProperty("prop.gcs.od.user_email");
            
            //ifa
            systemExecIFA              = prop.getProperty("prop.ifa.global.system_exec");
            localExecReplanner         = prop.getProperty("prop.ifa.replanner.local_exec");
            methodReplanner            = prop.getProperty("prop.ifa.replanner.method");
            cmdExecReplanner           = prop.getProperty("prop.ifa.replanner.cmd_exec");
            typeAltitudeDecayReplanner = prop.getProperty("prop.ifa.replanner.type_altitude_decay");
            timeExecReplanner          = prop.getProperty("prop.ifa.replanner.time_exec");
            numberWaypointsReplanner   = prop.getProperty("prop.ifa.replanner.number_waypoints");
            deltaReplanner             = prop.getProperty("prop.ifa.replanner.delta");
            dirFixedRouteIFA           = prop.getProperty("prop.ifa.fixed_route.dir");
            fileFixedRouteIFA          = prop.getProperty("prop.ifa.fixed_route.file_waypoints");
            
            //mosa
            systemExecMOSA            = prop.getProperty("prop.mosa.global.system_exec");
            localExecPlanner          = prop.getProperty("prop.mosa.planner.local_exec");
            methodPlanner             = prop.getProperty("prop.mosa.planner.method");
            cmdExecPlanner            = prop.getProperty("prop.mosa.planner.cmd_exec");
            
            localExecProcessingPlannerHGA4m = prop.getProperty("prop.mosa.planner.hga4m.local_exec_processing");
            fileMissionPlannerHGA4m   = prop.getProperty("prop.mosa.planner.hga4m.file_mission");
            timeExecPlannerHGA4m      = prop.getProperty("prop.mosa.planner.hga4m.time_exec");
            deltaPlannerHGA4m         = prop.getProperty("prop.mosa.planner.hga4m.delta");
            maxVelocityPlannerHGA4m   = prop.getProperty("prop.mosa.planner.hga4m.max_velocity");
            maxControlPlannerHGA4m    = prop.getProperty("prop.mosa.planner.hga4m.max_control");
            stdPositionPlannerHGA4m   = prop.getProperty("prop.mosa.planner.hga4m.std_position");
            
            waypointsPlannerCCQSP4m   = prop.getProperty("prop.mosa.planner.ccqsp4m.waypoints");
            timeHorizonPlannerCCQSP4m = prop.getProperty("prop.mosa.planner.ccqsp4m.time_horizon");
            deltaPlannerCCQSP4m       = prop.getProperty("prop.mosa.planner.ccqsp4m.delta");
            stepsPlannerCCQSP4m       = prop.getProperty("prop.mosa.planner.ccqsp4m.steps");
            stdPositionPlannerCCQSP4m = prop.getProperty("prop.mosa.planner.ccqsp4m.std_position");
            
            fileMissionPlannerAStar4m = prop.getProperty("prop.mosa.planner.astar4m.file_mission");         
            
            fileMissionPlannerGPathPlanner4m = prop.getProperty("prop.mosa.planner.gpathplanner4m.file_mission");         
            
            dirFixedRouteMOSA         = prop.getProperty("prop.mosa.fixed_route.dir");
            fileFixedRouteMOSA        = prop.getProperty("prop.mosa.fixed_route.file_waypoints");
            isDynamicFixedRouteMOSA   = Boolean.parseBoolean(prop.getProperty("prop.mosa.fixed_route.is_dynamic"));
            fileFixedRouteDynMOSA     = prop.getProperty("prop.mosa.fixed_route.file_waypoints_dyn");
            
            //system
            maxAltController          = Double.parseDouble(prop.getProperty("prop.controller.max_alt"));
            minAltController          = Double.parseDouble(prop.getProperty("prop.controller.min_alt"));
            displacFactorController   = Double.parseDouble(prop.getProperty("prop.controller.displacement_factor"));
            horizontalErrorGPS        = Double.parseDouble(prop.getProperty("prop.sensors.gps.error.horizontal"));
            verticalErrorBarometer    = Double.parseDouble(prop.getProperty("prop.sensors.barometer.error.vertical"));
            
            //behavior
            dirBehavior               = prop.getProperty("prop.behavior.dir");
            typeBehavior              = prop.getProperty("prop.behavior.type");
            discretizationBehavior    = prop.getProperty("prop.behavior.discretization");
            radiusCircleBehavior      = prop.getProperty("prop.behavior.radius_circle");
            baseTriangleBehavior      = prop.getProperty("prop.behavior.base_triangle");
            baseRectangleBehavior     = prop.getProperty("prop.behavior.base_rectangle");
            
            return true;
        } catch (FileNotFoundException ex){     
            StandardPrints.printMsgError2("Error [FileNotFoundException] read()");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgError2("Error [IOException] read()");
            ex.printStackTrace();
            return false;
        } catch (NumberFormatException ex) {
            StandardPrints.printMsgError2("Error [NumberFormatException] read()");
            ex.printStackTrace();
            return false;
        }
    }
    
    /**
     * Check some fields read of the properties file
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean checkReadFields(){
        if (typeCC == null || 
                (!typeCC.equals(TypeCC.INTEL_EDISON) && 
                !typeCC.equals(TypeCC.RASPBERRY) &&
                !typeCC.equals(TypeCC.ODROID) &&
                !typeCC.equals(TypeCC.BEAGLE_BONE) &&
                !typeCC.equals(TypeCC.INTEL_GALILEO))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of companion computer not valid");
            return false;
        }
        if (typeAP == null || 
                (!typeAP.equals(TypeAP.APM) && 
                !typeAP.equals(TypeAP.PIXHAWK))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of autopilot not valid");
            return false;
        }
        if (operationMode == null || 
                (!operationMode.equals(TypeOperationMode.SITL) &&
                !operationMode.equals(TypeOperationMode.HITL) &&
                !operationMode.equals(TypeOperationMode.REAL_FLIGHT))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of operation mode not valid");
            return false;
        }
        if (typeAircraft == null || 
                (!typeAircraft.equals(TypeAircraft.FIXED_WING) && 
                !typeAircraft.equals(TypeAircraft.ROTARY_WING))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of aircraft not valid");
            return false;
        }
        if (typeDataAcquisition == null || 
                (!typeDataAcquisition.equals(TypeDataAcquisitionUAV.DRONEKIT) && 
                !typeDataAcquisition.equals(TypeDataAcquisitionUAV.FLYTOS) && 
                !typeDataAcquisition.equals(TypeDataAcquisitionUAV.MAVLINK))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of data acquisition not valid");
            return false;
        }
        if (systemExecIFA == null || 
                (!systemExecIFA.equals(TypeSystemExecIFA.REPLANNER) &&
                 !systemExecIFA.equals(TypeSystemExecIFA.FIXED_ROUTE) && 
                 !systemExecIFA.equals(TypeSystemExecIFA.CONTROLLER))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of system exec not valid");
            return false;
        }
        if (localExecReplanner == null || 
                (!localExecReplanner.equals(LocalExecPlanner.ONBOARD) &&
                 !localExecReplanner.equals(LocalExecPlanner.OFFBOARD))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of local exec method not valid");
            return false;
        }
        if (methodReplanner == null || 
               (!methodReplanner.equals(TypeReplanner.GH4S) &&
                !methodReplanner.equals(TypeReplanner.GA4S) &&
                !methodReplanner.equals(TypeReplanner.MPGA4S) && 
                !methodReplanner.equals(TypeReplanner.MS4S) &&
                !methodReplanner.equals(TypeReplanner.DE4S) &&
                !methodReplanner.equals(TypeReplanner.GA_GA_4S) && 
                !methodReplanner.equals(TypeReplanner.GA_GH_4S) && 
                !methodReplanner.equals(TypeReplanner.PRE_PLANNED4s) &&
                !methodReplanner.equals(TypeReplanner.G_PATH_REPLANNER4s))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of method not valid");
            return false;
        }
        if (typeAltitudeDecayReplanner == null ||
                (!typeAltitudeDecayReplanner.equals(TypeAltitudeDecay.CONSTANTE) && 
                !typeAltitudeDecayReplanner.equals(TypeAltitudeDecay.LINEAR))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type altitude decay not valid");
            return false;
        }
        if (systemExecMOSA == null || 
                (!systemExecMOSA.equals(TypeSystemExecMOSA.FIXED_ROUTE) && 
                 !systemExecMOSA.equals(TypeSystemExecMOSA.PLANNER))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of execution not valid");
            return false;
        }
        if (localExecPlanner == null || 
                (!localExecPlanner.equals(LocalExecPlanner.ONBOARD) &&
                 !localExecPlanner.equals(LocalExecPlanner.OFFBOARD))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of local exec method not valid");
            return false;
        }
        if (methodPlanner == null || 
                (!methodPlanner.equals(TypePlanner.HGA4M) &&
                 !methodPlanner.equals(TypePlanner.CCQSP4M) && 
                 !methodPlanner.equals(TypePlanner.A_STAR4M) &&
                 !methodPlanner.equals(TypePlanner.G_PATH_PLANNER4M))){
            StandardPrints.printMsgError2("Error [[file ./config-global.properties]] type of method not valid");
            return false;
        } 
        
        return true;
    }
    
    /**
     * Automatically completes some paths and variables
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean parseToVariables(){
        try{
            if (methodReplanner.equals(TypeReplanner.DE4S)){
                typeReplanner = TypeReplanner.DE4S;
                dirReplanner = "../Modules-IFA/DE4s/";
            }else if (methodReplanner.equals(TypeReplanner.GH4S)){
                typeReplanner = TypeReplanner.GH4S;
                dirReplanner = "../Modules-IFA/GH4s/";
            }else if (methodReplanner.equals(TypeReplanner.GA4S)){
                typeReplanner = TypeReplanner.GA4S;
                dirReplanner = "../Modules-IFA/GA4s/";
            }else if (methodReplanner.equals(TypeReplanner.MPGA4S)){
                typeReplanner = TypeReplanner.MPGA4S;
                dirReplanner = "../Modules-IFA/MPGA4s/";
            }else if (methodReplanner.equals(TypeReplanner.MS4S)){
                typeReplanner = TypeReplanner.MS4S;
                dirReplanner = "../Modules-IFA/MS4s/";
            }else if (methodReplanner.equals(TypeReplanner.GA_GA_4S)){
                typeReplanner = TypeReplanner.GA_GA_4S;
                dirReplanner = "../Modules-IFA/GA-GA-4s/";
            }else if (methodReplanner.equals(TypeReplanner.GA_GH_4S)){
                typeReplanner = TypeReplanner.GA_GH_4S;
                dirReplanner = "../Modules-IFA/GA-GH-4s/";
            }else if (methodReplanner.equals(TypeReplanner.PRE_PLANNED4s)){
                typeReplanner = TypeReplanner.PRE_PLANNED4s;
                dirReplanner = "../Modules-IFA/Fixed-Route4s/";
            }else if (methodReplanner.equals(TypeReplanner.G_PATH_REPLANNER4s)){
                typeReplanner = TypeReplanner.G_PATH_REPLANNER4s;
                dirReplanner = "../Modules-IFA/G-Path-Replanner4s/";
            }
            if (methodPlanner.equals(TypePlanner.HGA4M)){
                typePlanner = TypePlanner.HGA4M;
                dirPlanner = "../Modules-MOSA/HGA4m/";
            }else if (methodPlanner.equals(TypePlanner.CCQSP4M)){
                typePlanner = TypePlanner.CCQSP4M;
                dirPlanner = "../Modules-MOSA/CCQSP4m/";
            }else if (methodPlanner.equals(TypePlanner.A_STAR4M)){
                typePlanner = TypePlanner.A_STAR4M;
                dirPlanner = "../Modules-MOSA/A-Star4m/";
            }else if (methodPlanner.equals(TypePlanner.G_PATH_PLANNER4M)){
                typePlanner = TypePlanner.G_PATH_PLANNER4M;
                dirPlanner = "../Modules-MOSA/G-Path-Planner4m/";
            }
            return true;
        } catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            ex.printStackTrace();
            return false;
        } catch (Exception ex){
            StandardPrints.printMsgError2("Error [Exception] parseToVariables()");
            ex.printStackTrace();
            return false;
        }
    }
    
    //global
    public String getTypeCC() {
        return typeCC;
    }
    
    public String getTypeAP() {
        return typeAP;
    }
    
    public String getOperationMode() {
        return operationMode;
    }
    
    public String getTypeAircraft() {
        return typeAircraft;
    }

    public String getTypeDataAcquisition() {
        return typeDataAcquisition;
    }
    
    public String getDirMission() {
        return dirMission;
    }
    
    public double getAltRelMission() {
        return altRelMission;
    }

    public double getFreqUpdateDataAP() {
        return freqUpdateDataAP;
    }
    
    public boolean hasRouteSimplifier() {
        return hasRouteSimplifier;
    }
    
    public String getDirRouteSimplifier() {
        return dirRouteSimplifier;
    }
    
    public String getFactorRouteSimplifier() {
        return factorRouteSimplifier;
    }
    
    public String getDirFiles() {
        return dirFiles;
    }
    
    public String getFileFeatureMission() {
        return fileFeatureMission;
    }
    
    public String getFileGeoBase() {
        return fileGeoBase;
    }

    //communication
    public String getHostIFA() {
        return hostIFA;
    }
    
    public String getHostMOSA() {
        return hostMOSA;
    }
    
    public String getHostS2DK() {
        return hostS2DK;
    }

    public int getPortNetworkIFAandMOSA() {
        return portNetworkIFAandMOSA;
    }
    
    public int getPortNetworkIFAandGCS() {
        return portNetworkIFAandGCS;
    }
    
    public int getPortNetworkMOSAandGCS() {
        return portNetworkMOSAandGCS;
    }
    
    public int getPortNetworkS2DK() {
        return portNetworkS2DK;
    }
    
    //hardwares    
    public boolean hasTelemetry() {
        return hasTelemetry;
    }
    
    public boolean hasGPS() {
        return hasGPS;
    }
    
    public boolean hasCamera() {
        return hasCamera;
    }

    public boolean hasSonar() {
        return hasSonar;
    }

    public boolean hasBuzzer() {
        return hasBuzzer;
    }

    public boolean hasParachute() {
        return hasParachute;
    }
    
    public boolean hasLED() {
        return hasLED;
    }
    
    public boolean hasSpraying() {
        return hasSpraying;
    }
    
    public boolean hasPowerModule() {
        return hasPowerModule;
    }
    
    public boolean hasTemperatureSensor() {
        return hasTemperatureSensor;
    }

    //sensors
    public String getDirCamera() {
        return dirCamera;
    }
    
    public String getTimeVideo() {
        return timeVideo;
    }
    
    public String getNumberPhotoInSequence() {
        return numberPhotoInSequence;
    }
    
    public String getDelayPhotoInSequence() {
        return delayPhotoInSequence;
    }

    public String getDirSonar() {
        return dirSonar;
    }
    
    public String getPinSonarTrig() {
        return pinSonarTrig;
    }
    
    public String getPinSonarEcho() {
        return pinSonarEcho;
    }
    
    public String getDirTemperatureSensor() {
        return dirTemperatureSensor;
    }
    
    public int getLevelMinimumBattery() {
        return levelMinimumBattery;
    }
    
    public int getLevelMaximumTemperature() {
        return levelMaximumTemperature;
    }
    
    public String getPinTemperatureCLK() {
        return pinTemperatureCLK;
    }
    
    public String getPinTemperatureCS() {
        return pinTemperatureCS;
    }
    
    public String getPinTemperatureSO() {
        return pinTemperatureSO;
    }    
            
    //actuators
    public String getDirBuzzer() {
        return dirBuzzer;
    }
    
    public String getPinBuzzer() {
        return pinBuzzer;
    }
    
    public String getDirParachute() {
        return dirParachute;
    }
    
    public String getPinParachute() {
        return pinParachute;
    }
    
    public String getDirLED() {
        return dirLED;
    }
    
    public String getPinLED() {
        return pinLED;
    }
    
    public String getDelayLED() {
        return delayLED;
    }
    
    public String getDirSpraying() {
        return dirSpraying;
    }
    
    public String getPinSpraying() {
        return pinSpraying;
    }
    
    //Aircraft
    public String getUavName() {
        return uavName;
    }

    public double getUavSpeedCruize() {
        return uavSpeedCruize;
    }

    public double getUavSpeedMax() {
        return uavSpeedMax;
    }

    public double getUavMass() {
        return uavMass;
    }

    public double getUavPayload() {
        return uavPayload;
    }

    public double getUavEndurance() {
        return uavEndurance;
    }
    
    public boolean hasGoogleMaps() {
        return hasGoogleMaps;
    }
    
    public boolean hasDB() {
        return hasDB;
    }

    public String getHostOD() {
        return hostOD;
    }
    
    public String getPortOD() {
        return portOD;
    }

    public String getUserEmail() {
        return userEmail;
    }
    
    //ifa
    public String getSystemExecIFA() {
        return systemExecIFA;
    }
    
    public String getLocalExecReplanner() {
        return localExecReplanner;
    }
    
    public String getTypeReplanner() {
        return typeReplanner;
    }

    public String getDirReplanner() {
        return dirReplanner;
    }

    public String getCmdExecReplanner() {
        return cmdExecReplanner;
    }
    
    public String getTypeAltitudeDecayReplanner() {
        return typeAltitudeDecayReplanner;
    }
    
    public String getTimeExecReplanner() {
        return timeExecReplanner;
    }

    public String getNumberWaypointsReplanner() {
        return numberWaypointsReplanner;
    }

    public String getDeltaReplanner() {
        return deltaReplanner;
    }
    
    public String getDirFixedRouteIFA() {
        return dirFixedRouteIFA;
    }

    public String getFileFixedRouteIFA() {
        return fileFixedRouteIFA;
    }
    
    //mosa
    public String getSystemExecMOSA() {
        return systemExecMOSA;
    }
    
    public String getLocalExecPlanner() {
        return localExecPlanner;
    }
    
    public String getTypePlanner() {
        return typePlanner;
    }
    
    public String getCmdExecPlanner() {
        return cmdExecPlanner;
    }

    public String getDirPlanner() {
        return dirPlanner;
    }
    
    public String getLocalExecProcessingPlannerHGA4m() {
        return localExecProcessingPlannerHGA4m;
    }

    public String getFileMissionPlannerHGA4m() {
        return fileMissionPlannerHGA4m;
    }
    
    public String getFileMissionPlannerAStar4m() {
        return fileMissionPlannerAStar4m;
    }
    
    public String getFileMissionPlannerGPathPlanner4m() {
        return fileMissionPlannerGPathPlanner4m;
    }
    
    public String getTimeExecPlannerHGA4m(int i) {
        if (!timeExecPlannerHGA4m.contains("[")){
            return timeExecPlannerHGA4m;
        }else{
            String str = timeExecPlannerHGA4m.replace("[", "");
            str = str.replace("]", "");
            str = str.replace(" ", "");
            String v[] = str.split(",");
            return v[i];
        }
    }
    
    public String getTimeExecPlannerHGA4m(){
        return timeExecPlannerHGA4m;
    }

    public String getDeltaPlannerHGA4m() {
        return deltaPlannerHGA4m;
    }

    public String getMaxVelocityPlannerHGA4m() {
        return maxVelocityPlannerHGA4m;
    }

    public String getMaxControlPlannerHGA4m() {
        return maxControlPlannerHGA4m;
    }
    
    public String getStdPositionPlannerHGA4m() {
        return stdPositionPlannerHGA4m;
    }
    
    public String getWaypointsPlannerCCQSP4m() {
        return waypointsPlannerCCQSP4m;
    }
    
    public String getTimeHorizonPlannerCCQSP4m() {
        return timeHorizonPlannerCCQSP4m;
    }
    
    public String getDeltaPlannerCCQSP4m() {
        return deltaPlannerCCQSP4m;
    }
    
    public String getStepsPlannerCCQSP4m() {
        return stepsPlannerCCQSP4m;
    }
    
    public String getStdPositionPlannerCCQSP4m() {
        return stdPositionPlannerCCQSP4m;
    }
    
    public String getDirFixedRouteMOSA() {
        return dirFixedRouteMOSA;
    }

    public String getFileFixedRouteMOSA() {
        return fileFixedRouteMOSA;
    }
    
    public boolean isDynamicFixedRouteMOSA() {
        return isDynamicFixedRouteMOSA;
    }
    
    public String getFileFixedRouteDynMOSA() {
        return fileFixedRouteDynMOSA;
    }
    
    //System
    public double getMaxAltController(){
        return maxAltController;
    }
    
    public double getMinAltController(){
        return minAltController;
    }
    
    public double getDisplacFactorController(){
        return displacFactorController;
    }
    
    public double getHorizontalErrorGPS(){
        return horizontalErrorGPS;
    }
    
    public double getVerticalErrorBarometer(){
        return verticalErrorBarometer;
    }

    //Behavior
    public String getDirBehavior() {
        return dirBehavior;
    }
    
    public String getTypeBehavior() {
        return typeBehavior;
    }

    public String getDiscretizationBehavior() {
        return discretizationBehavior;
    }

    public String getRadiusCircleBehavior() {
        return radiusCircleBehavior;
    }

    public String getBaseTriangleBehavior() {
        return baseTriangleBehavior;
    }

    public String getBaseRectangleBehavior() {
        return baseRectangleBehavior;
    }

}