package uav.ifa.module.decision_making;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import lib.color.StandardPrints;
import uav.generic.module.data_acquisition.DataAcquisition;
import uav.generic.struct.Command;
import uav.generic.struct.Constants;
import uav.generic.struct.Mission;
import uav.generic.struct.Waypoint;
import uav.generic.struct.WaypointJSON;
import uav.generic.util.UtilString;
import uav.hardware.aircraft.Drone;
import uav.ifa.struct.ReaderFileConfig;
import uav.ifa.module.path_replanner.DE4s;
import uav.ifa.module.path_replanner.GA4s;
import uav.ifa.module.path_replanner.GH4s;
import uav.ifa.module.path_replanner.MPGA4s;
import uav.ifa.module.path_replanner.Replanner;
import uav.ifa.struct.Failure;
import uav.ifa.struct.states.StateReplanning;

/**
 *
 * @author jesimar
 */
public class DecisionMaking {

    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final ReaderFileConfig config;
    private Replanner replanner;
    private StateReplanning stateReplanning;
    
    public DecisionMaking(Drone drone, DataAcquisition dataAcquisition) {
        this.config = ReaderFileConfig.getInstance();
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;
        this.stateReplanning = StateReplanning.WAITING;
    }
    
    public void actionToDoSomething(Failure failure) {
        stateReplanning = StateReplanning.REPLANNING;
        if (config.getSystemExec().equals(Constants.SYS_EXEC_REPLANNER)){            
            boolean itIsOkEmergencyLanding = emergenyLanding(failure);
            if (!itIsOkEmergencyLanding){
                openParachute();
            }
//            RTL();
//            land(lat, lng);
//            landVertical();
//            openParachute();
        }
        stateReplanning = StateReplanning.READY;
    }

    private boolean emergenyLanding(Failure failure) {
        StandardPrints.printMsgEmph("decison making -> emergeny landing");
        switch (config.getMethodRePlanner()) {
            case "GH4s":
                replanner = new GH4s(drone);
                break;
            case "GA4s":
                replanner = new GA4s(drone);
                break;
            case "MPGA4s":
                replanner = new MPGA4s(drone);
                break;
            case "DE4s":
                replanner = new DE4s(drone);
                break;
            default:
                break;
        }
        replanner.clearLogs();
        boolean itIsOkExec = replanner.exec();
        if (!itIsOkExec){
            return false;
        }
        try{
            Mission mission = new Mission();
            String path = config.getDirRePlanner() + "routeGeo.txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            double lat = 0.0;
            double lng = 0.0;
            double alt = 0.0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);            
                mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, lat, lng, alt));            
            }
            mission.addWaypoint(new Waypoint(Command.CMD_LAND, lat, lng, 0.0)); 
            mission.printMission();
            dataAcquisition.setMission(mission);
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException] emergenyLanding()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] emergenyLanding()");
            return false;
        }
    }
    
    //Nao funciona ainda
    //melhorar no futuro
    public void openParachute(){
        StandardPrints.printMsgEmph("decison making -> open parachute");
        Waypoint wpt = new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0);
        dataAcquisition.setWaypoint(new WaypointJSON(wpt));
    }
    
    //Nao funciona ainda
    private void RTL(){
        StandardPrints.printMsgEmph("decison making -> rtl");
        Waypoint wpt = new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0);        
        dataAcquisition.setWaypoint(new WaypointJSON(wpt));
    }
    
    //Nao funciona ainda
    private void land(double lat, double lng){ 
        StandardPrints.printMsgEmph("decison making -> land");
        Waypoint wpt = new Waypoint(Command.CMD_LAND, lat, lng, 0.0);
        dataAcquisition.setWaypoint(new WaypointJSON(wpt));
    }
    
    //Nao funciona ainda
    private void landVertical(){ 
        StandardPrints.printMsgEmph("decison making -> land vertical");
        Waypoint wpt = new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0);
        dataAcquisition.setWaypoint(new WaypointJSON(wpt));
    }
    
    public StateReplanning getStateReplanning() {
        return stateReplanning;
    }
    
}
