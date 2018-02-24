package uav.mosa.struct;

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
    private String actionAfterFinishMission;
    
    //planner
    private String methodPlanner;
    private String dirPlanner;
    private String cmdExecPlanner;
    private String localCalcMission;
    private String fileWaypointsMission; 
    private String fileGeoBase;    
    private String dynBetweenWpts;
    private String timeExec;
    private String delta;
    private String maxVelocity;
    private String maxControl;    
    private String altitudeRelativeMission;
    
    //fixed route    
    private String dirFixedRoute;
    private String fileFixedRoute;   
    private String fixedRouteIsDynamic;
    private String fileFixedRouteDyn;
    
    //controller
    private String typeController;
    private String dirController;
    private String cmdExecController;   
    
    //buzzer
    private String dirBuzzer;
    private String cmdExecBuzzer;
    private String fileWaypointsBuzzer;
    
    private double freqUpdateData;
    private double altitudeRelative;
    private boolean isDynBetweenWpts;
    private boolean isDynamicFixedRoute;
    
    private ReaderFileConfig(){
        
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
            actionAfterFinishMission = prop.getProperty("prop.global.action_after_finish_mission");
            freqUpdateDataAP         = prop.getProperty("prop.global.freq_update_data_ap");           
            
            methodPlanner            = prop.getProperty("prop.planner.method");            
            dirPlanner               = prop.getProperty("prop.planner.dir");
            cmdExecPlanner           = prop.getProperty("prop.planner.cmd_exec");
            localCalcMission         = prop.getProperty("prop.planner.local_calc_mission");
            fileWaypointsMission     = prop.getProperty("prop.planner.file_waypoints_mission");         
            fileGeoBase              = prop.getProperty("prop.planner.file_geo_base");
            dynBetweenWpts           = prop.getProperty("prop.planner.dyn_between_wpts");
            timeExec                 = prop.getProperty("prop.planner.time_exec");
            delta                    = prop.getProperty("prop.planner.delta");
            maxVelocity              = prop.getProperty("prop.planner.max_velocity");
            maxControl               = prop.getProperty("prop.planner.max_control");                        
            altitudeRelativeMission  = prop.getProperty("prop.planner.altitude_relative_mission");
            
            dirFixedRoute            = prop.getProperty("prop.fixed_route.dir");
            fileFixedRoute           = prop.getProperty("prop.fixed_route.file_waypoints");
            fixedRouteIsDynamic      = prop.getProperty("prop.fixed_route.is_dynamic");
            fileFixedRouteDyn        = prop.getProperty("prop.fixed_route.file_waypoints_dyn");
            
            typeController           = prop.getProperty("prop.controller.type_controller");
            dirController            = prop.getProperty("prop.controller.dir");
            cmdExecController        = prop.getProperty("prop.controller.cmd_exec");
            
            dirBuzzer                = prop.getProperty("prop.buzzer.dir");
            cmdExecBuzzer            = prop.getProperty("prop.buzzer.cmd_exec");
            fileWaypointsBuzzer      = prop.getProperty("prop.buzzer.file_waypoints_buzzer");
                        
            return true;
        } catch (FileNotFoundException ex){     
            StandardPrints.printMsgError2("Error [FileNotFoundException] ReaderLoadConfig()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgError2("Error [IOException] ReaderLoadConfig()");
            return false;
        }
    }
    
    public void printProperties(){
        System.out.println(nameDrone);
        System.out.println(systemExec);
        System.out.println(actionAfterFinishMission);
        System.out.println(freqUpdateDataAP);        
        
        System.out.println(methodPlanner);
        System.out.println(dirPlanner);
        System.out.println(cmdExecPlanner);
        System.out.println(localCalcMission);
        System.out.println(fileWaypointsMission);        
        System.out.println(fileGeoBase);               
        System.out.println(dynBetweenWpts);
        System.out.println(timeExec);
        System.out.println(delta);
        System.out.println(maxVelocity);
        System.out.println(maxControl);        
        System.out.println(altitudeRelativeMission);
                
        System.out.println(dirFixedRoute);
        System.out.println(fileFixedRoute);
        System.out.println(fixedRouteIsDynamic);  
        System.out.println(fileFixedRouteDyn);
        
        System.out.println(typeController);
        System.out.println(dirController);
        System.out.println(cmdExecController);
        
        System.out.println(dirBuzzer);
        System.out.println(cmdExecBuzzer);        
        System.out.println(fileWaypointsBuzzer);
    }
    
    public boolean checkReadFields(){
        if (nameDrone == null || 
                (!nameDrone.equals(Constants.NAME_DRONE_ARARINHA) && 
                !nameDrone.equals(Constants.NAME_DRONE_iDRONE_ALPHA))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] name of aircraft not valid");
            return false;
        }
        if (systemExec == null || 
                (!systemExec.equals(Constants.SYS_EXEC_FIXED_ROUTE) && 
                !systemExec.equals(Constants.SYS_EXEC_PLANNER) && 
                !systemExec.equals(Constants.SYS_EXEC_CONTROLLER))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of execution not valid");
            return false;
        }
        if (methodPlanner == null || 
                (!methodPlanner.equals(Constants.METHOD_PLANNER_HGA4m))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type of method not valid");
            return false;
        }
        if (actionAfterFinishMission == null || 
                (!actionAfterFinishMission.equals(Constants.CMD_NONE) && 
                !actionAfterFinishMission.equals(Constants.CMD_LAND) && 
                !actionAfterFinishMission.equals(Constants.CMD_RTL))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] action after finish mission not valid");
            return false;
        }
        if (typeController == null ||
                (!typeController.equals(Constants.TYPE_CONTROLLER_VOICE) && 
                !typeController.equals(Constants.TYPE_CONTROLLER_TEXT) && 
                !typeController.equals(Constants.TYPE_CONTROLLER_KEYBOARD))){
            StandardPrints.printMsgError2("Error [[file ./config.properties]] type controller not valid");
            return false;
        }
        return true;
    }
    
    public boolean parseToVariables(){
        try{
            altitudeRelative = Double.parseDouble(altitudeRelativeMission);
            freqUpdateData = Double.parseDouble(freqUpdateDataAP);
            isDynamicFixedRoute = Boolean.parseBoolean(fixedRouteIsDynamic);
            isDynBetweenWpts = Boolean.parseBoolean(dynBetweenWpts);
            return true;
        }catch (NumberFormatException ex){
            StandardPrints.printMsgError2("Error [NumberFormatException] parseToVariables()");
            return false;
        }
    }
    
    public String getNameDrone() {
        return nameDrone;
    }
    
    public String getSystemExec() {
        return systemExec;
    }
    
    public String getActionAfterFinishMission() {
        return actionAfterFinishMission;
    }

    public double getFreqUpdateData() {
        return freqUpdateData;
    }
    
    public boolean isDynamicFixedRoute() {
        return isDynamicFixedRoute;
    }
    
    public boolean isDynBetweenWpts() {
        return isDynBetweenWpts;
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

    public String getFileGeoBase() {
        return fileGeoBase;
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
    
    public double getAltitudeRelative() {
        return altitudeRelative;
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
    
    public String getDirBuzzer() {
        return dirBuzzer;
    }

    public String getCmdExecBuzzer() {
        return cmdExecBuzzer;
    }
    
    public String getFileWaypointsBuzzer() {
        return fileWaypointsBuzzer;
    }    
        
}