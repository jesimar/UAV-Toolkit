package uav.mosa.struct;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lib.color.StandardPrints;
import uav.generic.struct.constants.TypeActionAfterFinishMission;
import uav.generic.struct.constants.TypeController;
import uav.generic.struct.constants.TypePlanner;
import uav.generic.struct.constants.TypeSystemExecMOSA;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderFileConfig {
    
    private static final ReaderFileConfig instance = new ReaderFileConfig();
    private Properties prop;
    private InputStream input;

    //global
    private String systemExec;
    private String actionAfterFinishMission;
    
    //planner
    private String methodPlanner;
    private String dirPlanner;
    private String cmdExecPlanner;
    private String localCalcMission;
    private String fileWaypointsMission;
    private String timeExec;
    private String delta;
    private String maxVelocity;
    private String maxControl;
    
    //fixed route    
    private String dirFixedRoute;
    private String fileFixedRoute;   
    private String fixedRouteIsDynamic;
    private String fileFixedRouteDyn;
    
    //controller
    private String typeController;
    private String dirController;
    private String cmdExecController;
    
    private boolean isDynamicFixedRoute;
    
    private ReaderFileConfig(){
        
    }
    
    public static ReaderFileConfig getInstance() {
        return instance;
    }
    
    public boolean read(){
        try {
            prop = new Properties();
            input = new FileInputStream("./config-mosa.properties");
            prop.load(input);
            
            systemExec               = prop.getProperty("prop.global.system_exec");
            actionAfterFinishMission = prop.getProperty("prop.global.action_after_finish_mission");
            
            methodPlanner            = prop.getProperty("prop.planner.method");            
            dirPlanner               = prop.getProperty("prop.planner.dir");
            cmdExecPlanner           = prop.getProperty("prop.planner.cmd_exec");
            localCalcMission         = prop.getProperty("prop.planner.local_calc_mission");
            fileWaypointsMission     = prop.getProperty("prop.planner.file_waypoints_mission");         
            timeExec                 = prop.getProperty("prop.planner.time_exec");
            delta                    = prop.getProperty("prop.planner.delta");
            maxVelocity              = prop.getProperty("prop.planner.max_velocity");
            maxControl               = prop.getProperty("prop.planner.max_control");                        
            
            dirFixedRoute            = prop.getProperty("prop.fixed_route.dir");
            fileFixedRoute           = prop.getProperty("prop.fixed_route.file_waypoints");
            fixedRouteIsDynamic      = prop.getProperty("prop.fixed_route.is_dynamic");
            fileFixedRouteDyn        = prop.getProperty("prop.fixed_route.file_waypoints_dyn");
            
            typeController           = prop.getProperty("prop.controller.type_controller");
            dirController            = prop.getProperty("prop.controller.dir");
            cmdExecController        = prop.getProperty("prop.controller.cmd_exec");
                                 
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
                !systemExec.equals(TypeSystemExecMOSA.PLANNER) && 
                !systemExec.equals(TypeSystemExecMOSA.CONTROLLER))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of execution not valid");
            return false;
        }
        if (methodPlanner == null || 
                (!methodPlanner.equals(TypePlanner.HGA4M))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of method not valid");
            return false;
        }
        if (actionAfterFinishMission == null || 
                (!actionAfterFinishMission.equals(TypeActionAfterFinishMission.CMD_NONE) && 
                !actionAfterFinishMission.equals(TypeActionAfterFinishMission.CMD_LAND) && 
                !actionAfterFinishMission.equals(TypeActionAfterFinishMission.CMD_RTL))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] action after finish mission not valid");
            return false;
        }
        if (typeController == null ||
                (!typeController.equals(TypeController.VOICE) && 
                !typeController.equals(TypeController.TEXT) && 
                !typeController.equals(TypeController.KEYBOARD))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type controller not valid");
            return false;
        }
        return true;
    }
    
    public boolean parseToVariables(){
        try{
            isDynamicFixedRoute = Boolean.parseBoolean(fixedRouteIsDynamic);
            return true;
        }catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            return false;
        }
    }
    
    public String getSystemExec() {
        return systemExec;
    }
    
    public String getActionAfterFinishMission() {
        return actionAfterFinishMission;
    }
    
    public boolean isDynamicFixedRoute() {
        return isDynamicFixedRoute;
    }
    
    public String getMethodPlanner() {
        return methodPlanner;
    }

    public String getDirPlanner() {
        return dirPlanner;
    }

    public String getCmdExecPlanner() {
        return cmdExecPlanner;
    } 
    
    public String getLocalCalcMission() {
        return localCalcMission;
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

    public String getTypeController() {
        return typeController;
    }

    public String getDirController() {
        return dirController;
    }

    public String getCmdExecController() {
        return cmdExecController;
    }
    
}