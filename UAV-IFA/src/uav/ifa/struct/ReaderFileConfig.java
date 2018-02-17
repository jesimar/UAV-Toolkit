package uav.ifa.struct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.Constants;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfig {
    
    private static final ReaderFileConfig instance = new ReaderFileConfig();
    private Properties prop;
    private InputStream input;

    //global
    private String nameDrone;
    private String systemExec;
    private String freqUpdateDataAP;
    private String fileLogAircraft;
    private String levelBatteryToFail;
    private String timeToFail;
    
    //replanner
    private String methodRePlanner;
    private String dirRePlanner;
    private String cmdExecRePlanner;
    private String fileGeoBase;    
    private String timeExec;
    private String qtdWaypoints;
    private String delta; 
    private String typeAltitudeDecay;
    
    //alarm
    private String dirAlarm;
    private String cmdExecAlarm;
    
    //alarm
    private String dirOpenParachute;
    private String cmdExecOpenParachute;
    
    private double freqUpdateData;
    private int timeToFailure;
    private int levelMinBatteryToFail;

    private ReaderFileConfig() {
        
    }
    
    public static ReaderFileConfig getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            input = new FileInputStream("./config.properties");
            prop.load(input);
            
            nameDrone                = prop.getProperty("prop.global.name_aircraft");
            systemExec               = prop.getProperty("prop.global.system_exec");
            freqUpdateDataAP         = prop.getProperty("prop.global.freq_update_data_ap");
            fileLogAircraft          = prop.getProperty("prop.global.file_log_aircraft");
            levelBatteryToFail       = prop.getProperty("prop.global.level_min_battery_to_fail");
            timeToFail               = prop.getProperty("prop.global.time_to_fail");
            
            methodRePlanner          = prop.getProperty("prop.replanner.method");
            dirRePlanner             = prop.getProperty("prop.replanner.dir");
            cmdExecRePlanner         = prop.getProperty("prop.replanner.cmd_exec");
            fileGeoBase              = prop.getProperty("prop.replanner.file_geo_base");
            timeExec                 = prop.getProperty("prop.replanner.time_exec");
            qtdWaypoints             = prop.getProperty("prop.replanner.qtd_waypoints");
            delta                    = prop.getProperty("prop.replanner.delta");
            typeAltitudeDecay        = prop.getProperty("prop.replanner.type_altitude_decay");
            
            dirAlarm                 = prop.getProperty("prop.alarm.dir");
            cmdExecAlarm             = prop.getProperty("prop.alarm.cmd_exec");
            
            dirOpenParachute         = prop.getProperty("prop.openparachute.dir");
            cmdExecOpenParachute     = prop.getProperty("prop.openparachute.cmd_exec");
            
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
    
    public void printProperties(){
        System.out.println(nameDrone);
        System.out.println(systemExec);
        System.out.println(freqUpdateDataAP);
        System.out.println(fileLogAircraft);        
        System.out.println(levelBatteryToFail);
        System.out.println(timeToFail);
        
        System.out.println(methodRePlanner);
        System.out.println(dirRePlanner);
        System.out.println(cmdExecRePlanner);
        System.out.println(fileGeoBase);               
        System.out.println(timeExec);
        System.out.println(qtdWaypoints);
        System.out.println(delta);
        System.out.println(typeAltitudeDecay);    
        
        System.out.println(dirAlarm);
        System.out.println(cmdExecAlarm);
        
        System.out.println(dirOpenParachute);
        System.out.println(cmdExecOpenParachute);
    }
    
    public boolean checkReadFields(){
        if (nameDrone == null || 
                (!nameDrone.equals(Constants.NAME_DRONE_ARARINHA) && 
                !nameDrone.equals(Constants.NAME_DRONE_iDRONE_ALPHA))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] name of aircraft not valid");
            return false;
        }
        if (systemExec == null || 
                !systemExec.equals(Constants.SYS_EXEC_REPLANNER)){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of replanner not valid");
            return false;
        }
        if (methodRePlanner == null || 
                (!methodRePlanner.equals(Constants.METHOD_REPLANNER_GH4s) &&
                !methodRePlanner.equals(Constants.METHOD_REPLANNER_GA4s) &&
                !methodRePlanner.equals(Constants.METHOD_REPLANNER_MPGA4s) && 
                !methodRePlanner.equals(Constants.METHOD_REPLANNER_DE4s))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of method not valid");
            return false;
        }
        if (typeAltitudeDecay == null ||
                (!typeAltitudeDecay.equals(Constants.TYPE_ALTITUDE_DECAY_CONSTANTE) && 
                !typeAltitudeDecay.equals(Constants.TYPE_ALTITUDE_DECAY_LINEAR) &&
                !typeAltitudeDecay.equals(Constants.TYPE_ALTITUDE_DECAY_LOG))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type altitude decay not valid");
            return false;
        }
        return true;
    }
    
    public boolean parseToVariables(){
        try{
            freqUpdateData = Double.parseDouble(freqUpdateDataAP);
            levelMinBatteryToFail = Integer.parseInt(levelBatteryToFail);
            timeToFailure = Integer.parseInt(timeToFail);
            return true;
        }catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            ex.printStackTrace();
            return false;
        }
    }
    
    public String getNameDrone() {
        return nameDrone;
    }
    
    public String getSystemExec() {
        return systemExec;
    }

    public double getFreqUpdateData() {
        return freqUpdateData;
    }   

    public String getFileLogAircraft() {
        return fileLogAircraft;
    }
    
    public int getLevelMinBatteryToFail() {
        return levelMinBatteryToFail;
    }
    
    public int getTimeToFailure() {
        return timeToFailure;
    }
    
    public String getMethodRePlanner() {
        return methodRePlanner;
    }

    public String getDirRePlanner() {
        return dirRePlanner;
    }

    public String getCmdExecRePlanner() {
        return cmdExecRePlanner;
    }

    public String getFileGeoBase() {
        return fileGeoBase;
    }

    public String getTimeExec() {
        return timeExec;
    }

    public String getQtdWaypoints() {
        return qtdWaypoints;
    }

    public String getDelta() {
        return delta;
    }
    
    public String getTypeAltitudeDecay() {
        return typeAltitudeDecay;
    }
    
    public String getDirAlarm() {
        return dirAlarm;
    }

    public String getCmdExecAlarm() {
        return cmdExecAlarm;
    }
    
    public String getDirOpenParachute() {
        return dirOpenParachute;
    }

    public String getCmdExecOpenParachute() {
        return cmdExecOpenParachute;
    }
}