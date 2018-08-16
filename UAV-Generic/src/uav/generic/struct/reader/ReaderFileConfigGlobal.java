package uav.generic.struct.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeAP;
import uav.generic.struct.constants.TypeAircraft;
import uav.generic.struct.constants.TypeCC;
import uav.generic.struct.constants.TypeOperationMode;

/**
 * Classe que lê o arquivo com configurações gerais da missão.
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfigGlobal {
    
    private static final ReaderFileConfigGlobal instance = new ReaderFileConfigGlobal();
    
    private final String nameFile = "../Modules-Global/config-global.properties";
    private Properties prop;

    //global
    private String typeCC;
    private String typeAP;
    private String operationMode;
    private String typeAircraft;
    private double freqUpdateDataAP;
    private double altRelMission;
    private String dirFiles;
    private String fileGeoBase;
    private String fileFeatureMission;
    
    private String hostIFA;
    private String hostMOSA;
    private String hostSOA;
    private int portNetworkIFAandMOSA;
    private int portNetworkIFAandGCS;
    private int portNetworkMOSAandGCS;
    private int portNetworkSOA;
    
    private boolean hasDB;
    private String hostOD;
    private String portOD;
    private String userEmail;
        
    //sensor camera
    private boolean hasCamera;
    private String dirCamera;
    
    //sensor sonar
    private boolean hasSonar;
    private String dirSonar;
    
    //sensor power module
    private boolean hasPowerModule;
    private int levelMinimumBattery;
    
    //sensor temperature
    private boolean hasTemperatureSensor;
    private String dirTemperatureSensor;
    private int levelMaximumTemperature;
    
    //atuactor buzzer
    private boolean hasBuzzer;
    private String dirBuzzer;
    
    //atuactor open-parachute
    private boolean hasParachute;
    private String dirParachute;
    
    //atuactor LED
    private boolean hasLED;
    private String dirLED;
    
    //atuactor Spraying
    private boolean hasSpraying;
    private String dirSpraying;

    /**
     * Class constructor.
     */
    private ReaderFileConfigGlobal() {
        
    }
    
    public static ReaderFileConfigGlobal getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            prop.load(new FileInputStream(nameFile));
            
            typeCC                = prop.getProperty("prop.global.type_cc");
            typeAP                = prop.getProperty("prop.global.type_ap");
            operationMode         = prop.getProperty("prop.global.operation_mode");
            typeAircraft          = prop.getProperty("prop.global.type_aircraft");
            freqUpdateDataAP      = Double.parseDouble(prop.getProperty("prop.global.freq_update_data_ap"));
            altRelMission         = Double.parseDouble(prop.getProperty("prop.global.altitude_relative_mission"));
            dirFiles              = prop.getProperty("prop.global.dir_files");
            fileFeatureMission    = prop.getProperty("prop.global.file_feature_mission");
            fileGeoBase           = prop.getProperty("prop.global.file_geo_base");
            
            hostIFA               = prop.getProperty("prop.global.comm.host_ifa");
            hostMOSA              = prop.getProperty("prop.global.comm.host_mosa");
            hostSOA               = prop.getProperty("prop.global.comm.host_soa");
            portNetworkIFAandMOSA = Integer.parseInt(prop.getProperty("prop.global.comm.port_network_ifa_mosa"));
            portNetworkIFAandGCS  = Integer.parseInt(prop.getProperty("prop.global.comm.port_network_ifa_gcs"));
            portNetworkMOSAandGCS = Integer.parseInt(prop.getProperty("prop.global.comm.port_network_mosa_gcs"));
            portNetworkSOA        = Integer.parseInt(prop.getProperty("prop.global.comm.port_network_soa"));
            
            hasDB                 = Boolean.parseBoolean(prop.getProperty("prop.gcs.od.hasDB"));
            hostOD                = prop.getProperty("prop.gcs.od.host_od");
            portOD                = prop.getProperty("prop.gcs.od.port_od_gcs");
            userEmail             = prop.getProperty("prop.gcs.od.user_email");
            
            hasCamera             = Boolean.parseBoolean(prop.getProperty("prop.hw.has_camera"));
            hasSonar              = Boolean.parseBoolean(prop.getProperty("prop.hw.has_sonar"));
            hasPowerModule        = Boolean.parseBoolean(prop.getProperty("prop.hw.has_powermodule"));
            hasTemperatureSensor  = Boolean.parseBoolean(prop.getProperty("prop.hw.has_temperature_sensor"));
            hasBuzzer             = Boolean.parseBoolean(prop.getProperty("prop.hw.has_buzzer"));
            hasParachute          = Boolean.parseBoolean(prop.getProperty("prop.hw.has_parachute"));
            hasLED                = Boolean.parseBoolean(prop.getProperty("prop.hw.has_led"));
            hasSpraying           = Boolean.parseBoolean(prop.getProperty("prop.hw.has_spraying"));      
            
            dirCamera             = prop.getProperty("prop.hw.sensor.camera.dir");
            dirSonar              = prop.getProperty("prop.hw.sensor.sonar.dir");
            levelMinimumBattery   = Integer.parseInt(prop.getProperty("prop.hw.sensor.powermodule.level_min_battery"));
            dirTemperatureSensor  = prop.getProperty("prop.hw.sensor.temperature.dir");
            levelMaximumTemperature = Integer.parseInt(prop.getProperty("prop.hw.sensor.temperature.level_max_temperature"));
            
            dirBuzzer             = prop.getProperty("prop.hw.actuator.buzzer.dir");
            dirParachute          = prop.getProperty("prop.hw.actuator.parachute.dir");
            dirLED                = prop.getProperty("prop.hw.actuator.led.dir");
            dirSpraying           = prop.getProperty("prop.hw.actuator.spraying.dir");
            
            return true;
        } catch (FileNotFoundException ex){     
            StandardPrints.printMsgError2("Error [FileNotFoundException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgError2("Error [IOException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        } catch (NumberFormatException ex) {
            StandardPrints.printMsgError2("Error [NumberFormatException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean checkReadFields(){
        if (typeCC == null || 
                (!typeCC.equals(TypeCC.EDISON) && 
                !typeCC.equals(TypeCC.RASPBERRY))){
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
                (!operationMode.equals(TypeOperationMode.SITL_LOCAL) &&
                !operationMode.equals(TypeOperationMode.SITL_CC) &&
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
        return true;
    }
    
    public boolean parseToVariables(){
        try{
            
            return true;
        }catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            ex.printStackTrace();
            return false;
        }
    }
    
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

    public double getFreqUpdateDataAP() {
        return freqUpdateDataAP;
    }
    
    public double getAltRelMission() {
        return altRelMission;
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

    public String getHostIFA() {
        return hostIFA;
    }
    
    public String getHostMOSA() {
        return hostMOSA;
    }
    
    public String getHostSOA() {
        return hostSOA;
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
    
    public int getPortNetworkSOA() {
        return portNetworkSOA;
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

    public String getDirCamera() {
        return dirCamera;
    }

    public String getDirSonar() {
        return dirSonar;
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
    
    public String getDirBuzzer() {
        return dirBuzzer;
    }
    
    public String getDirParachute() {
        return dirParachute;
    }
    
    public String getDirLED() {
        return dirLED;
    }
    
    public String getDirSpraying() {
        return dirSpraying;
    }

}