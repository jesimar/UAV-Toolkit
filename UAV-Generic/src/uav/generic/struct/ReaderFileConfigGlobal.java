package uav.generic.struct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeAircraft;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfigGlobal {
    
    private static final ReaderFileConfigGlobal instance = new ReaderFileConfigGlobal();
    private Properties prop;
    private InputStream input;

    private String typeAircraft;
    private String freqUpdateDataAP;
    private String altitudeRelative;
    private String dirFiles;
    private String fileGeoBase;
    
    //sonar
    private String dirSonar;
    private String cmdExecSonar;
    
    //camera
    private String dirCamera;
    private String cmdExecCameraPicture;
    private String cmdExecCameraVideo;
    
    //buzzer
    private String dirBuzzer;
    private String cmdExecBuzzer;
    private String cmdExecAlarm;
    private String fileWaypointsBuzzer;
    private String fileWaypointsCamera;
    
    //open-parachute
    private String dirParachute;
    private String cmdExecOpenParachute;
    
    private double freqUpdateData;
    private double altitudeRelativeMission;

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
            
            typeAircraft             = prop.getProperty("prop.global.type_aircraft");
            freqUpdateDataAP         = prop.getProperty("prop.global.freq_update_data_ap");
            altitudeRelative         = prop.getProperty("prop.global.altitude_relative_mission");
            dirFiles                 = prop.getProperty("prop.global.dir");
            fileGeoBase              = prop.getProperty("prop.global.file_geo_base");
            fileWaypointsBuzzer      = prop.getProperty("prop.global.file_waypoints_buzzer");
            fileWaypointsCamera      = prop.getProperty("prop.global.file_waypoints_camera");
            
            dirSonar                 = prop.getProperty("prop.sonar.dir");
            cmdExecSonar             = prop.getProperty("prop.sonar.cmd_exec_sonar");
            
            dirCamera                = prop.getProperty("prop.camera.dir");
            cmdExecCameraPicture     = prop.getProperty("prop.camera.cmd_exec_picture");
            cmdExecCameraVideo       = prop.getProperty("prop.camera.cmd_exec_video");            
            
            dirBuzzer                = prop.getProperty("prop.buzzer.dir");
            cmdExecBuzzer            = prop.getProperty("prop.buzzer.cmd_exec_buzzer");
            cmdExecAlarm             = prop.getProperty("prop.buzzer.cmd_exec_alarm");            
            
            dirParachute             = prop.getProperty("prop.parachute.dir");
            cmdExecOpenParachute     = prop.getProperty("prop.parachute.cmd_exec");
            
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
            StandardPrints.printMsgError2("Error [[file ./config.properties]] name of aircraft not valid");
            return false;
        }       
        return true;
    }
    
    public boolean parseToVariables(){
        try{
            freqUpdateData = Double.parseDouble(freqUpdateDataAP);   
            altitudeRelativeMission = Double.parseDouble(altitudeRelative);
            return true;
        }catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            ex.printStackTrace();
            return false;
        }
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

    public String getDirSonar() {
        return dirSonar;
    }

    public String getCmdExecSonar() {
        return cmdExecSonar;
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
    
    public String getFileWaypointsCamera() {
        return fileWaypointsCamera;
    }
    
    public String getDirParachute() {
        return dirParachute;
    }

    public String getCmdExecOpenParachute() {
        return cmdExecOpenParachute;
    }
    
}