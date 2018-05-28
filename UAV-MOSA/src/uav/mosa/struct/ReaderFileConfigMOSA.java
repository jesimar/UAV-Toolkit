package uav.mosa.struct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeLocalExecPlanner;
import uav.generic.struct.constants.TypePlanner;
import uav.generic.struct.constants.TypeSystemExecMOSA;

/**
 * Classe que lê o arquivo com gerais do sistema MOSA.
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfigMOSA {
    
    private static final ReaderFileConfigMOSA instance = new ReaderFileConfigMOSA();
    
    private final String nameFile = "./config-mosa.properties";
    private Properties prop;

    //global
    private String systemExec;
    
    //planner
    private String localExecPlanner;
    private String typePlanner;
    private String methodPlanner;
    private String cmdExecPlanner;
    private String dirPlanner;
    private String missionProcessingLocation;
    private String fileWaypointsMission;
    private String timeExec;
    private String delta;
    private String maxVelocity;
    private String maxControl;
    
    //fixed route    
    private String dirFixedRoute;
    private String fileFixedRoute;   
    private boolean isDynamicFixedRoute;
    private String fileFixedRouteDyn;
    
    /**
     * Class constructor.
     */
    private ReaderFileConfigMOSA(){
        
    }
    
    public static ReaderFileConfigMOSA getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            prop.load(new FileInputStream(nameFile));
            
            systemExec                = prop.getProperty("prop.global.system_exec");
            
            localExecPlanner          = prop.getProperty("prop.planner.local_exec");
            methodPlanner             = prop.getProperty("prop.planner.method");
            cmdExecPlanner            = prop.getProperty("prop.planner.cmd_exec");
            missionProcessingLocation = prop.getProperty("prop.planner.mission_processing_location");
            fileWaypointsMission      = prop.getProperty("prop.planner.file_waypoints_mission");         
            timeExec                  = prop.getProperty("prop.planner.time_exec");
            delta                     = prop.getProperty("prop.planner.delta");
            maxVelocity               = prop.getProperty("prop.planner.max_velocity");
            maxControl                = prop.getProperty("prop.planner.max_control");                        
            
            dirFixedRoute             = prop.getProperty("prop.fixed_route.dir");
            fileFixedRoute            = prop.getProperty("prop.fixed_route.file_waypoints");
            isDynamicFixedRoute       = Boolean.parseBoolean(prop.getProperty("prop.fixed_route.is_dynamic"));
            fileFixedRouteDyn         = prop.getProperty("prop.fixed_route.file_waypoints_dyn");
                                 
            return true;
        } catch (FileNotFoundException ex){     
            StandardPrints.printMsgError2("Error [FileNotFoundException] ReaderLoadConfig()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgError2("Error [IOException] ReaderLoadConfig()");
            return false;
        } 
    }
    
    public boolean checkReadFields(){        
        if (systemExec == null || 
                (!systemExec.equals(TypeSystemExecMOSA.FIXED_ROUTE) && 
                 !systemExec.equals(TypeSystemExecMOSA.PLANNER))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of execution not valid");
            return false;
        }
        if (localExecPlanner == null || 
                (!localExecPlanner.equals(TypeLocalExecPlanner.ONBOARD) &&
                 !localExecPlanner.equals(TypeLocalExecPlanner.OFFBOARD))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of local exec method not valid");
            return false;
        }
        if (methodPlanner == null || 
                (!methodPlanner.equals(TypePlanner.HGA4M) &&
                 !methodPlanner.equals(TypePlanner.CCQSP4M))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of method not valid");
            return false;
        }        
        return true;
    }
    
    public boolean parseToVariables(){
        try{
            if (methodPlanner.equals(TypePlanner.HGA4M)){
                typePlanner = TypePlanner.HGA4M;
                dirPlanner = "../Modules-MOSA/HGA4m/";
            }else if (methodPlanner.equals(TypePlanner.CCQSP4M)){
                typePlanner = TypePlanner.CCQSP4M;
                dirPlanner = "../Modules-MOSA/CCQSP4m/";
            }
            return true;
        }catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            return false;
        }
    }
    
    public String getSystemExec() {
        return systemExec;
    }
    
    public boolean isDynamicFixedRoute() {
        return isDynamicFixedRoute;
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
    
    public String getMissionProcessingLocation() {
        return missionProcessingLocation;
    }

    public String getFileWaypointsMission() {
        return fileWaypointsMission;
    }

    public String getTimeExec(int i) {
        if (!timeExec.contains("[")){
            return timeExec;
        }else{
            String str = timeExec.replace("[", "");
            str = str.replace("]", "");
            str = str.replace(" ", "");
            String v[] = str.split(",");
            return v[i];
        }
    }
    
    public String getTimeExec(){
        return timeExec;
    }

    public String getDelta() {
        return delta;
    }

    public String getMaxVelocity() {
        return maxVelocity;
    }

    public String getMaxControl() {
        return maxControl;
    }
    
    public String getDirFixedRoute() {
        return dirFixedRoute;
    }

    public String getFileFixedRoute() {
        return fileFixedRoute;
    }
    
    public String getFileFixedRouteDyn() {
        return fileFixedRouteDyn;
    }
}