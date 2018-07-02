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
    private int portNetworkIFAandMOSA;
    private int portNetworkIFAandGCS;
    private int portNetworkMOSAandGCS;
    private String hostSOA;
    private int portNetworkSOA;
        
    //sensor camera
    private boolean hasCamera;
    private String dirCamera;
    
    //sensor sonar
    private boolean hasSonar;
    private String dirSonar;
    
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
    
    //sensor power module
    private boolean hasPowerModule;
    private int levelMinimumBattery;
    
    //sensor temperature
    private boolean hasTemperatureSensor;
    private int levelMaximumTemperature;

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
            hostIFA               = prop.getProperty("prop.global.host_ifa");
            portNetworkIFAandMOSA = Integer.parseInt(prop.getProperty("prop.global.port_network_ifa_mosa"));
            portNetworkIFAandGCS  = Integer.parseInt(prop.getProperty("prop.global.port_network_ifa_gcs"));
            portNetworkMOSAandGCS = Integer.parseInt(prop.getProperty("prop.global.port_network_mosa_gcs"));
            hostSOA               = prop.getProperty("prop.global.host_soa");
            portNetworkSOA        = Integer.parseInt(prop.getProperty("prop.global.port_network_soa"));
            
            hasCamera             = Boolean.parseBoolean(prop.getProperty("prop.hardware.has_camera"));
            hasSonar              = Boolean.parseBoolean(prop.getProperty("prop.hardware.has_sonar"));
            hasBuzzer             = Boolean.parseBoolean(prop.getProperty("prop.hardware.has_buzzer"));
            hasParachute          = Boolean.parseBoolean(prop.getProperty("prop.hardware.has_parachute"));
            hasLED                = Boolean.parseBoolean(prop.getProperty("prop.hardware.has_led"));
            hasSpraying           = Boolean.parseBoolean(prop.getProperty("prop.hardware.has_spraying"));
            hasPowerModule        = Boolean.parseBoolean(prop.getProperty("prop.hardware.has_powermodule"));
            hasTemperatureSensor  = Boolean.parseBoolean(prop.getProperty("prop.hardware.has_temperature_sensor"));
                    
            dirCamera             = prop.getProperty("prop.camera.dir");
                                    
            dirSonar              = prop.getProperty("prop.sonar.dir");
            
            dirBuzzer             = prop.getProperty("prop.buzzer.dir");
            
            dirParachute          = prop.getProperty("prop.parachute.dir");
            
            dirLED                = prop.getProperty("prop.led.dir");
            dirSpraying           = prop.getProperty("prop.spraying.dir");
            
            levelMinimumBattery   = Integer.parseInt(prop.getProperty("prop.powermodule.level_minimum_battery"));
            
            levelMaximumTemperature = Integer.parseInt(prop.getProperty("prop.temperature_sensor.level_maximum_temperature"));
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
    
    public String getFileGeoBase() {
        return fileGeoBase;
    }

    public String getHostIFA() {
        return hostIFA;
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
    
    public String getHostSOA() {
        return hostSOA;
    }

    public int getPortNetworkSOA() {
        return portNetworkSOA;
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
                
    public String getDirBuzzer() {
        return dirBuzzer;
    }
    
    public String getFileFeatureMission() {
        return fileFeatureMission;
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
    
    public int getLevelMinimumBattery() {
        return levelMinimumBattery;
    }
    
    public int getLevelMaximumTemperature() {
        return levelMaximumTemperature;
    }

}