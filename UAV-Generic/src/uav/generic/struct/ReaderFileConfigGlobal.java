package uav.generic.struct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeAircraft;
import uav.generic.struct.constants.TypeOperationMode;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfigGlobal {
    
    private static final ReaderFileConfigGlobal instance = new ReaderFileConfigGlobal();
    
    private Properties prop;
    private InputStream input;

    private String operationMode;
    private String typeAircraft;
    private String freqUpdateDataAP;
    private double freqUpdateData;
    private String altitudeRelative;
    private double altitudeRelativeMission;
    private String dirFiles;
    private String fileGeoBase;
    
    private String hostIFA;
    private String portNetworkIFAandMOSA;
        
    //sensor camera
    private String hasCamera;
    private String dirCamera;
    private String cmdExecCameraPicture;
    private String cmdExecCameraVideo;
    private String fileWaypointsCamera;
    
    //sensor sonar
    private String hasSonar;
    private String dirSonar;
    private String cmdExecSonar;
    
    //atuactor buzzer
    private String hasBuzzer;
    private String dirBuzzer;
    private String cmdExecBuzzer;
    private String cmdExecAlarm;
    private String fileWaypointsBuzzer;
    
    //atuactor open-parachute
    private String hasParachute;
    private String dirParachute;
    private String cmdExecOpenParachute;
    
    //sensor power module
    private String hasPowerModule;
    private String levelMinimumBattery;
    private int levelMinBattery;

    private ReaderFileConfigGlobal() {
        
    }
    
    public static ReaderFileConfigGlobal getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            input = new FileInputStream("../Modules-Global/config-global.properties");
            prop.load(input);
            
            operationMode            = prop.getProperty("prop.global.operation_mode");
            typeAircraft             = prop.getProperty("prop.global.type_aircraft");
            freqUpdateDataAP         = prop.getProperty("prop.global.freq_update_data_ap");
            altitudeRelative         = prop.getProperty("prop.global.altitude_relative_mission");
            dirFiles                 = prop.getProperty("prop.global.dir_files");
            fileGeoBase              = prop.getProperty("prop.global.file_geo_base");
            hostIFA                 = prop.getProperty("prop.global.host_ifa");
            portNetworkIFAandMOSA    = prop.getProperty("prop.global.port_network_ifa_mosa");
            
            hasCamera                = prop.getProperty("prop.hardware.has_camera");
            hasSonar                 = prop.getProperty("prop.hardware.has_sonar");
            hasBuzzer                = prop.getProperty("prop.hardware.has_buzzer");
            hasParachute             = prop.getProperty("prop.hardware.has_parachute");
            hasPowerModule           = prop.getProperty("prop.hardware.has_powermodule");
            
            dirCamera                = prop.getProperty("prop.camera.dir");
            cmdExecCameraPicture     = prop.getProperty("prop.camera.cmd_exec_picture");
            cmdExecCameraVideo       = prop.getProperty("prop.camera.cmd_exec_video"); 
            fileWaypointsCamera      = prop.getProperty("prop.camera.file_waypoints_camera");
                                    
            dirSonar                 = prop.getProperty("prop.sonar.dir");
            cmdExecSonar             = prop.getProperty("prop.sonar.cmd_exec_sonar");
            
            dirBuzzer                = prop.getProperty("prop.buzzer.dir");
            cmdExecBuzzer            = prop.getProperty("prop.buzzer.cmd_exec_buzzer");
            cmdExecAlarm             = prop.getProperty("prop.buzzer.cmd_exec_alarm"); 
            fileWaypointsBuzzer      = prop.getProperty("prop.buzzer.file_waypoints_buzzer");
            
            dirParachute             = prop.getProperty("prop.parachute.dir");
            cmdExecOpenParachute     = prop.getProperty("prop.parachute.cmd_exec");
            
            levelMinimumBattery      = prop.getProperty("prop.powermodule.level_minimum_battery");
            return true;
        } catch (FileNotFoundException ex){     
            StandardPrints.printMsgError2("Error [FileNotFoundException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgError2("Error [IOException] ReaderLoadConfig()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean checkReadFields(){
        if (typeAircraft == null || 
                (!typeAircraft.equals(TypeAircraft.FIXED_WING) && 
                !typeAircraft.equals(TypeAircraft.ROTARY_WING))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of aircraft not valid");
            return false;
        }
        if (operationMode == null || 
                (!operationMode.equals(TypeOperationMode.SITL_LOCAL) &&
                !operationMode.equals(TypeOperationMode.SITL_EDISON) && 
                !operationMode.equals(TypeOperationMode.REAL_FLIGHT))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of operation mode not valid");
            return false;
        }
        return true;
    }
    
    public boolean parseToVariables(){
        try{
            levelMinBattery = Integer.parseInt(levelMinimumBattery);
            freqUpdateData = Double.parseDouble(freqUpdateDataAP);   
            altitudeRelativeMission = Double.parseDouble(altitudeRelative);
            return true;
        }catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public String getOperationMode() {
        return operationMode;
    }
    
    public String getTypeAircraft() {
        return typeAircraft;
    }

    public double getFreqUpdateData() {
        return freqUpdateData;
    }
    
    public double getAltitudeRelativeMission() {
        return altitudeRelativeMission;
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
        return Integer.parseInt(portNetworkIFAandMOSA);
    }
    
    public boolean hasCamera() {
        return Boolean.parseBoolean(hasCamera);
    }

    public boolean hasSonar() {
        return Boolean.parseBoolean(hasSonar);
    }

    public boolean hasBuzzer() {
        return Boolean.parseBoolean(hasBuzzer);
    }

    public boolean hasParachute() {
        return Boolean.parseBoolean(hasParachute);
    }
    
    public boolean hasPowerModule() {
        return Boolean.parseBoolean(hasPowerModule);
    }
    
    public String getDirCamera() {
        return dirCamera;
    }
    
    public String getCmdExecCameraPicture() {
        return cmdExecCameraPicture;
    }

    public String getCmdExecCameraVideo() {
        return cmdExecCameraVideo;
    }
    
    public String getFileWaypointsCamera() {
        return fileWaypointsCamera;
    }

    public String getDirSonar() {
        return dirSonar;
    }

    public String getCmdExecSonar() {
        return cmdExecSonar;
    }      
                
    public String getDirBuzzer() {
        return dirBuzzer;
    }

    public String getCmdExecBuzzer() {
        return cmdExecBuzzer;
    }
    
    public String getCmdExecAlarm() {
        return cmdExecAlarm;
    }
    
    public String getFileWaypointsBuzzer() {
        return fileWaypointsBuzzer;
    }    
    
    public String getDirParachute() {
        return dirParachute;
    }

    public String getCmdExecOpenParachute() {
        return cmdExecOpenParachute;
    }
    
    public int getLevelMinimumBattery() {
        return levelMinBattery;
    }

}