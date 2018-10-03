package uav.ifa.module.decision_making;

import lib.color.StandardPrints;
import lib.uav.hardware.aircraft.Drone;
import lib.uav.module.actuators.ParachuteControl;
import lib.uav.module.comm.DataAcquisition;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.struct.Waypoint;
import lib.uav.struct.constants.TypeAircraft;
import lib.uav.struct.constants.TypeInputCommand;
import lib.uav.struct.constants.TypeMsgCommunication;
import lib.uav.struct.constants.TypeOperationMode;
import lib.uav.struct.constants.TypeReplanner;
import lib.uav.struct.constants.TypeSystemExecIFA;
import lib.uav.struct.constants.TypeWaypoint;
import lib.uav.struct.mission.Mission;
import lib.uav.struct.states.StateReplanning;
import lib.uav.util.UtilRoute;
import uav.ifa.module.communication.CommunicationGCS;
import uav.ifa.module.path_replanner.DE4s;
import uav.ifa.module.path_replanner.PrePlanned4s;
import uav.ifa.module.path_replanner.GA4s;
import uav.ifa.module.path_replanner.GA_GA_4s;
import uav.ifa.module.path_replanner.GA_GH_4s;
import uav.ifa.module.path_replanner.GH4s;
import uav.ifa.module.path_replanner.MPGA4s;
import uav.ifa.module.path_replanner.MS4s;
import uav.ifa.module.path_replanner.Replanner;
import uav.ifa.struct.Failure;

/**
 * The class makes the decision making of the IFA system.
 * @author Jesimar S. Arantes
 * @since version 1.0.0
 */
public class DecisionMaking {

    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private final ReaderFileConfig config;
    private Replanner replanner;
    private StateReplanning stateReplanning;
    private String typeAction = "";

    /**
     * Class constructor.
     * @param drone instance of the aircraft
     * @param dataAcquisition object to send commands to drone
     * @since version 1.0.0
     */
    public DecisionMaking(Drone drone, DataAcquisition dataAcquisition) {
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;
        this.stateReplanning = StateReplanning.WAITING;
        this.config = ReaderFileConfig.getInstance();
    }

    /**
     * Action to run an security strategy onboard.
     * @param failure type of failure occurred
     * @since version 4.0.0
     */
    public void actionForSafetyOnboard(Failure failure) {
        stateReplanning = StateReplanning.REPLANNING;
        boolean resp;
        if (config.getSystemExecIFA().equals(TypeSystemExecIFA.FIXED_ROUTE)) {
            resp = sendMissionEmergencyBasedFixedRoute();
            if (resp) {
                stateReplanning = StateReplanning.READY;
                StandardPrints.printMsgEmph("send fixed route -> success");
                return;
            } else {
                stateReplanning = StateReplanning.DISABLED;
                StandardPrints.printMsgWarning("send fixed route -> failure");
                return;
            }
        } else if (config.getSystemExecIFA().equals(TypeSystemExecIFA.REPLANNER) ||
                config.getSystemExecIFA().equals(TypeSystemExecIFA.CONTROLLER)) {
            if (failure.getTypeFailure() != null) {
                switch (failure.getTypeFailure()) {
                    case FAIL_AP_POWEROFF:
                        if (config.hasParachute()) {
                            openParachute();
                            return;
                        } else {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        }
                        break;
                    case FAIL_AP_EMERGENCY:
                        if (config.hasParachute()) {
                            openParachute();
                            return;
                        } else {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        }
                        break;
                    case FAIL_AP_CRITICAL:
                        resp = execEmergencyLandingOnboard();
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    case FAIL_GPS:
                        if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                            resp = sendLandVertical();
                            if (resp){
                                stateReplanning = StateReplanning.READY;
                            }else{
                                stateReplanning = StateReplanning.DISABLED;
                            }
                            return;
                        } else {
                            if (config.hasParachute()) {
                                openParachute();
                                return;
                            }
                        }
                        break;
                    case FAIL_ENGINE:
                        if (config.hasParachute()) {
                            openParachute();
                            return;
                        } else {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        }
                        break;
                    case FAIL_SYSTEM_IFA:
                        if (config.hasParachute()) {
                            openParachute();
                            return;
                        } else {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        }
                        break;
                    case FAIL_LOW_BATTERY:
                        resp = execEmergencyLandingOnboard();
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    case FAIL_BATTERY_OVERHEATING:
                        resp = execEmergencyLandingOnboard();
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    case FAIL_SYSTEM_MOSA:
                        resp = execEmergencyLandingOnboard();
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    case FAIL_BASED_INSERT_FAILURE:
                        if (typeAction.equals(TypeInputCommand.CMD_EMERGENCY_LANDING)) {
                            resp = execEmergencyLandingOnboard();
                            if (resp){
                                stateReplanning = StateReplanning.READY;
                            }else{
                                stateReplanning = StateReplanning.DISABLED;
                            }
                            return;
                        } else if (typeAction.equals(TypeInputCommand.CMD_LAND)) {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        } else if (typeAction.equals(TypeInputCommand.CMD_RTL)) {
                            resp = sendRTL();
                            if (resp){
                                stateReplanning = StateReplanning.READY;
                            }else{
                                stateReplanning = StateReplanning.DISABLED;
                            }
                            return;
                        }
                        break;
                    case FAIL_BAD_WEATHER:
                        resp = sendRTL();
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    default:
                        break;
                }
            }
        }
        stateReplanning = StateReplanning.READY;
    }
    
    /**
     * Action to run an security strategy offboard.
     * @param failure type of failure occurred
     * @param communicationGCS the communication with the GCS.
     * @since version 4.0.0
     */
    public void actionForSafetyOffboard(Failure failure, CommunicationGCS communicationGCS) {
        stateReplanning = StateReplanning.REPLANNING;
        boolean resp;
        if (config.getSystemExecIFA().equals(TypeSystemExecIFA.FIXED_ROUTE)) {//feito no proprio cc (offboard nao faz sentido)
            resp = sendMissionEmergencyBasedFixedRoute();
            if (resp) {
                stateReplanning = StateReplanning.READY;
                StandardPrints.printMsgEmph("send fixed route -> success");
                return;
            } else {
                stateReplanning = StateReplanning.DISABLED;
                StandardPrints.printMsgWarning("send fixed route -> failure");
                return;
            }
        } else if (config.getSystemExecIFA().equals(TypeSystemExecIFA.REPLANNER)) {
            if (failure.getTypeFailure() != null) {
                switch (failure.getTypeFailure()) {
                    case FAIL_AP_POWEROFF: //feito no proprio cc (offboard nao faz sentido)
                        if (config.hasParachute()) {
                            openParachute();
                            return;
                        } else {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        }
                        break;
                    case FAIL_AP_EMERGENCY: //feito no proprio cc (offboard nao faz sentido)
                        if (config.hasParachute()) {
                            openParachute();
                            return;
                        } else {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        }
                        break;
                    case FAIL_AP_CRITICAL:
                        resp = execEmergencyLandingOffboard(communicationGCS);
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    case FAIL_GPS: //feito no proprio cc (offboard nao faz sentido)
                        if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                            resp = sendLandVertical();
                            if (resp){
                                stateReplanning = StateReplanning.READY;
                            }else{
                                stateReplanning = StateReplanning.DISABLED;
                            }
                            return;
                        } else {
                            if (config.hasParachute()) {
                                openParachute();
                                return;
                            }
                        }
                        break;
                    case FAIL_ENGINE: //feito no proprio cc (offboard nao faz sentido)
                        if (config.hasParachute()) {
                            openParachute();
                            return;
                        } else {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        }
                        break;
                    case FAIL_SYSTEM_IFA: //feito no proprio cc (offboard nao faz sentido)
                        if (config.hasParachute()) {
                            openParachute();
                            return;
                        } else {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        }
                        break;
                    case FAIL_LOW_BATTERY:
                        resp = execEmergencyLandingOffboard(communicationGCS);
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    case FAIL_BATTERY_OVERHEATING:
                        resp = execEmergencyLandingOffboard(communicationGCS);
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    case FAIL_SYSTEM_MOSA:
                        resp = execEmergencyLandingOffboard(communicationGCS);
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    case FAIL_BASED_INSERT_FAILURE:
                        if (typeAction.equals(TypeInputCommand.CMD_EMERGENCY_LANDING)) {
                            resp = execEmergencyLandingOffboard(communicationGCS);
                            if (resp){
                                stateReplanning = StateReplanning.READY;
                            }else{
                                stateReplanning = StateReplanning.DISABLED;
                            }
                            return;
                        } else if (typeAction.equals(TypeInputCommand.CMD_LAND)) {
                            if (config.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)) {
                                resp = sendLandVertical();
                                if (resp){
                                    stateReplanning = StateReplanning.READY;
                                }else{
                                    stateReplanning = StateReplanning.DISABLED;
                                }
                                return;
                            }
                        } else if (typeAction.equals(TypeInputCommand.CMD_RTL)) {
                            resp = sendRTL();
                            if (resp){
                                stateReplanning = StateReplanning.READY;
                            }else{
                                stateReplanning = StateReplanning.DISABLED;
                            }
                            return;
                        }
                        break;
                    case FAIL_BAD_WEATHER: //feito no proprio cc (offboard nao faz sentido)
                        resp = sendRTL();
                        if (resp){
                            stateReplanning = StateReplanning.READY;
                        }else{
                            stateReplanning = StateReplanning.DISABLED;
                        }
                        return;
                    default:
                        break;
                }
            }
        }
        stateReplanning = StateReplanning.READY;
    }

    /**
     * Action to open the parachute.
     * TO DO: Deve-se desarmar o motor e entao abrir o paraquedas.
     * @return {@code true} if success, 
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean openParachute() {
        StandardPrints.printMsgEmph("decison making -> open parachute");
        ParachuteControl parachute = new ParachuteControl();
        boolean isOpen = parachute.open();
        if (isOpen) {
//            Waypoint wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
//            dataAcquisition.setWaypoint(wpt);
            stateReplanning = StateReplanning.READY;
            return true;
        }else{
            stateReplanning = StateReplanning.DISABLED;
            return false;
        }
    }
    
    /**
     * Defines the type of action performed by the IFA decision system.
     * Possible values: [CMD_EMERGENCY_LANDING, CMD_LAND, CMD_RTL].
     * @param action represents the action to be performed
     * @since version 1.1.0
     */
    public void setTypeAction(String action) {
        this.typeAction = action;
    }

    /**
     * Returns the state of the planner.
     * Possible values: [WAITING, REPLANNING, READY, DISABLED].
     * @return the state of the planner
     * @since version 1.0.0
     */
    public StateReplanning getStateReplanning() {
        return stateReplanning;
    }
    
    /**
     * Run the emergency landing algorithm in case something goes wrong, 
     * then the parachute is opened [Onboard].
     * @return {@code true} if success,
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    private boolean execEmergencyLandingOnboard() {
        boolean resp = sendMissionEmergencyBasedReplannerOnboard();
        if (!resp) {
            if (config.hasParachute()) {
                openParachute();
            } else {
                sendLandVertical();
            }
        }
        return resp;
    }

    /**
     * This command calculates an emergency landing route using some algorithm 
     * configured for this, then passes the new route to AutoPilot [Onboard].
     * @return {@code true} if success,
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    private boolean sendMissionEmergencyBasedReplannerOnboard() {
        double navSpeed = drone.getInfo().getListParameters().getValue("WPNAV_SPEED");
        dataAcquisition.setNavigationSpeed(navSpeed / 10);

        StandardPrints.printMsgEmph("decison making -> emergeny landing: " + typeAction);
        if (config.getTypeReplanner().equals(TypeReplanner.GH4S)) {
            replanner = new GH4s(drone);
        } else if (config.getTypeReplanner().equals(TypeReplanner.GA4S)) {
            replanner = new GA4s(drone);
        } else if (config.getTypeReplanner().equals(TypeReplanner.MPGA4S)) {
            replanner = new MPGA4s(drone);
        } else if (config.getTypeReplanner().equals(TypeReplanner.MS4S)) {
            replanner = new MS4s(drone);
        } else if (config.getTypeReplanner().equals(TypeReplanner.DE4S)) {
            replanner = new DE4s(drone);
        } else if (config.getTypeReplanner().equals(TypeReplanner.GA_GA_4S)) {
            replanner = new GA_GA_4s(drone);
        } else if (config.getTypeReplanner().equals(TypeReplanner.GA_GH_4S)) {
            replanner = new GA_GH_4s(drone);
        } else if (config.getTypeReplanner().equals(TypeReplanner.PRE_PLANNED4s)) {
            replanner = new PrePlanned4s(drone);
        }
        replanner.clearLogs();
        boolean itIsOkExec = replanner.exec();
        if (!itIsOkExec) {
            return false;
        }

        dataAcquisition.setNavigationSpeed(navSpeed);

        Mission mission = new Mission();
        String path = config.getDirReplanner() + "routeGeo.txt";

        if (config.getTypeReplanner().equals(TypeReplanner.GA_GA_4S)) {
            System.out.println("TypeReplanner.GA_GA_4S");
            path = "../Modules-IFA/GA4s/" + "routeGeo.txt";
        } else if (config.getTypeReplanner().equals(TypeReplanner.GA_GH_4S)) {
            String best = ((GA_GH_4s) replanner).bestMethod();
            if (best.equals("GA")) {
                path = "../Modules-IFA/GA4s/" + "routeGeo.txt";
            } else if (best.equals("GH")) {
                path = "../Modules-IFA/GH4s/" + "routeGeo.txt";
            } else {
                return false;
            }
        }

        boolean resp = UtilRoute.readFileRouteIFA(mission, path, 2);
        if (!resp) {
            return false;
        }
        if (mission.getMission().size() > 0) {
            mission.printMission();
            return dataAcquisition.setMission(mission);
        }
        return true;
    }
    
    /**
     * Run the emergency landing algorithm in case something goes wrong, 
     * then the parachute is opened [Offboard]
     * @return {@code true} if success,
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    private boolean execEmergencyLandingOffboard(CommunicationGCS communicationGCS) {
        boolean resp = sendMissionEmergencyBasedReplannerOffboard(communicationGCS);
        if (!resp) {
            if (config.hasParachute()) {
                openParachute();
            } else {
                sendLandVertical();
            }
        }
        return resp;
    }

    /**
     * This command calculates an emergency landing route using some algorithm 
     * configured for this, then passes the new route to AutoPilot [Offboard].
     * @param communicationGCS the communication with the GCS
     * @return {@code true} if success, 
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    private boolean sendMissionEmergencyBasedReplannerOffboard(CommunicationGCS communicationGCS) {
        double navSpeed = drone.getInfo().getListParameters().getValue("WPNAV_SPEED");
        dataAcquisition.setNavigationSpeed(navSpeed/10);

        String attributes = config.getTypeReplanner() 
                + ";" + config.getDirFiles() 
                + ";" + config.getFileGeoBase()
                + ";" + config.getDirReplanner() 
                + ";" + config.getCmdExecReplanner()
                + ";" + config.getTypeAltitudeDecayReplanner()
                + ";" + config.getTimeExecReplanner()
                + ";" + config.getNumberWaypointsReplanner() 
                + ";" + config.getDeltaReplanner();
        communicationGCS.sendData(TypeMsgCommunication.IFA_GCS_REPLANNER + attributes);
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {

            }
        } while (!communicationGCS.hasReceiveRouteGCS());

        String msgRoute = communicationGCS.getRouteReplannerGCS();

        dataAcquisition.setNavigationSpeed(navSpeed);

        if (msgRoute.equals(TypeMsgCommunication.UAV_ROUTE_FAILURE)) {
            System.out.println("Route GCS [Failure]: " + msgRoute);
            return false;
        } else {
            return dataAcquisition.setMission(msgRoute);
        }
    }
    
    /**
     * Send emergency route based fixed route to autopilot.
     * Note: Same method to Onboard and Offboard mode.
     * @return {@code true} if success, 
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    private boolean sendMissionEmergencyBasedFixedRoute() {
        StandardPrints.printMsgEmph("decison making -> fixed route");
        String path = config.getDirFixedRouteIFA()+ config.getFileFixedRouteIFA();
        Mission mission = new Mission();
        boolean resp = UtilRoute.readFileRouteIFA(mission, path, 0);
        if (!resp) {
            return false;
        }
        mission.printMission();
        if (mission.getMission().size() > 0) {
            return dataAcquisition.setMission(mission);
        }
        return true;
    }

    /**
     * This command guides the aircraft to the specified position and then lands 
     * vertically when the vehicle is a multi-rotor.
     * @param lat latitude where you want to land
     * @param lng longitude where you want to land
     * @return {@code true} if success, 
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    private boolean sendLand(double lat, double lng) {
        StandardPrints.printMsgEmph("decison making -> land");
        Waypoint wpt = new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0);
        return dataAcquisition.setWaypoint(wpt);
    }

    /**
     * This command lands the aircraft vertically where it is.
     * @return {@code true} if success, 
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    private boolean sendLandVertical() {
        StandardPrints.printMsgEmph("decison making -> land vertical");
        Waypoint wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
        return dataAcquisition.setWaypoint(wpt);
    }

    /**
     * This command causes the aircraft Return To Launch, so the aircraft first 
     * climbs to the altitude RTL_ALT, then returns to the launch site and finally 
     * lands vertically when it is a multi-rotor.
     * @return {@code true} if success, 
     *         {@code false} otherwise
     * @since version 4.0.0
     */
    private boolean sendRTL() {
        if (config.hasPowerModule()
                || !config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)) {
            if (drone.getSensors().getBattery().level > drone.getInfo().getEstimatedConsumptionBatForRTL()){
                StandardPrints.printMsgEmph("decison making -> RTL");
                Waypoint wpt = new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);
                return dataAcquisition.setWaypoint(wpt);
            }else{
                StandardPrints.printMsgEmph("battery is not enough to do RTL.");
                StandardPrints.printMsgEmph("decison making -> RTL -> changed to -> land vertical");
                sendLandVertical();
                return false;
            }
        }else{
            StandardPrints.printMsgEmph("decison making -> RTL");
            Waypoint wpt = new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);
            return dataAcquisition.setWaypoint(wpt);
        }
    }

}
