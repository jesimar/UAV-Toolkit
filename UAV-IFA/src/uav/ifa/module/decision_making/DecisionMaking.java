package uav.ifa.module.decision_making;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
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
    private String typeAction = "";
    
    public DecisionMaking(Drone drone, DataAcquisition dataAcquisition) {
        this.config = ReaderFileConfig.getInstance();
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;
        this.stateReplanning = StateReplanning.WAITING;
    }
    
    public void actionToDoSomething(Failure failure) {
        stateReplanning = StateReplanning.REPLANNING;
        if (config.getSystemExec().equals(Constants.SYS_EXEC_REPLANNER)){
            if (failure.typeOfFailure != null){
                switch (failure.typeOfFailure) {
                    case FAIL_AP_POWEROFF:
                        openParachute();
                        break;
                    case FAIL_AP_EMERGENCY:
                        openParachute();
                        break;
                    case FAIL_GPS:
                        openParachute();
                        break;
                    case FAIL_SYSTEM_IFA:
                        openParachute();
                        break;
                    case FAIL_BATTERY:
                        execEmergencyLanding();
                        break;
                    case FAIL_SYSTEM_MOSA:
                        execEmergencyLanding();
                        break;
                    case FAIL_BASED_TIME:
                        //land(-22.00593264981567,-47.89870966454083);
                        //landVertical();
                        //RTL();
                        //openParachute();
                        execEmergencyLanding();
                        break;
                    case FAIL_BASED_INSERT_FAILURE:
                        if (typeAction.equals("MPGA")){
                            execEmergencyLanding();
                        } else if (typeAction.equals("LAND")){
                            landVertical();
                        } else if (typeAction.equals("RTL")){
                            RTL();
                        }                        
                        break;
                    default:
                        break;
                }
            }
        }
        stateReplanning = StateReplanning.READY;
    }
    
    private void execEmergencyLanding(){
        boolean itIsOkEmergencyLanding = emergenyLanding();
        if (!itIsOkEmergencyLanding){
            openParachute();
        }
    }

    private boolean emergenyLanding() {
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
            int i = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);
                if (i > 1){
                    mission.addWaypoint(new Waypoint(Command.CMD_WAYPOINT, lat, lng, alt));  
                }
                i++;
            }
            mission.addWaypoint(new Waypoint(Command.CMD_LAND, lat, lng, 0.0)); 
            mission.printMission();
            if (mission.getMission().size() > 0){
                dataAcquisition.setMission(mission);
            }
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException] emergenyLanding()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] emergenyLanding()");
            return false;
        }
    }
    
    //Este comando vai ate a posicao especificada e entao pousa verticalmente
    //quando o veiculo eh um multi-rotor.
    private void land(double lat, double lng){ 
        StandardPrints.printMsgEmph("decison making -> land");
        Waypoint wpt = new Waypoint(Command.CMD_LAND, lat, lng, 0.0);
        dataAcquisition.setWaypoint(new WaypointJSON(wpt));
    }
    
    private void landVertical(){ 
        StandardPrints.printMsgEmph("decison making -> land vertical");
        Waypoint wpt = new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0);
        dataAcquisition.setWaypoint(new WaypointJSON(wpt));
    }
    
    private void RTL(){
        StandardPrints.printMsgEmph("decison making -> rtl");
        Waypoint wpt = new Waypoint(Command.CMD_RTL, 0.0, 0.0, 0.0);        
        dataAcquisition.setWaypoint(new WaypointJSON(wpt));
    }
    
    //melhorar no futuro
    //Desarmar o motor e entao abrir o paraquedas.
    private void openParachute(){
        StandardPrints.printMsgEmph("decison making -> open parachute");
        Waypoint wpt = new Waypoint(Command.CMD_LAND_VERTICAL, 0.0, 0.0, 0.0);//retirar essa linha
        dataAcquisition.setWaypoint(new WaypointJSON(wpt));//retirar essa linha
        
        try {
            boolean print = true;
            File f = new File(config.getDirOpenParachute());
            final Process comp = Runtime.getRuntime().exec(config.getCmdExecOpenParachute(), null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    if (print) {
                        while (sc.hasNextLine()) {
                            System.out.println(sc.nextLine());
                        }
                    }
                    sc.close();
                }
            });
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] openParachute()");
        }
    }
    
    public StateReplanning getStateReplanning() {
        return stateReplanning;
    }
    
    public void setTypeAction(String action){
        this.typeAction = action;
    }
    
}
