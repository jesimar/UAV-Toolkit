package uav.ifa.module.decision_making;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import lib.color.StandardPrints;
import uav.generic.module.data_communication.DataCommunication;
import uav.generic.module.sensors_actuators.ParachuteControl;
import uav.generic.util.UtilString;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.struct.constants.TypeWaypoint;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.Waypoint;
import uav.generic.struct.constants.TypeAircraft;
import uav.generic.struct.constants.TypeInputCommand;
import uav.generic.struct.constants.TypeReplanner;
import uav.generic.struct.constants.TypeSystemExecIFA;
import uav.generic.struct.reader.ReaderFileConfigGlobal;
import uav.generic.struct.states.StateReplanning;
import uav.ifa.module.communication_control.CommunicationGCS;
import uav.ifa.module.path_replanner.DE4s;
import uav.ifa.module.path_replanner.FixedRoute4s;
import uav.ifa.module.path_replanner.GA4s;
import uav.ifa.module.path_replanner.GA_GA_4s;
import uav.ifa.module.path_replanner.GA_GH_4s;
import uav.ifa.module.path_replanner.GH4s;
import uav.ifa.module.path_replanner.MPGA4s;
import uav.ifa.module.path_replanner.Replanner;
import uav.ifa.struct.Failure;
import uav.ifa.struct.ReaderFileConfigIFA;

/**
 * Classe que faz as tomadas de decisão do sistema IFA.
 *
 * @author Jesimar S. Arantes
 */
public class DecisionMaking {

    private final Drone drone;
    private final DataCommunication dataAcquisition;
    private final ReaderFileConfigIFA configLocal;
    private final ReaderFileConfigGlobal configGlobal;
    private Replanner replanner;
    private StateReplanning stateReplanning;
    private String typeAction = "";

    /**
     * Class constructor.
     *
     * @param drone instance of the aircraft
     * @param dataAcquisition object to send commands to drone
     */
    public DecisionMaking(Drone drone, DataCommunication dataAcquisition) {
        this.configLocal = ReaderFileConfigIFA.getInstance();
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
        this.drone = drone;
        this.dataAcquisition = dataAcquisition;
        this.stateReplanning = StateReplanning.WAITING;
    }
    
    public void actionToDoSomethingOffboard(Failure failure, CommunicationGCS communicationGCS){
        stateReplanning = StateReplanning.REPLANNING;
        if (configLocal.getSystemExec().equals(TypeSystemExecIFA.REPLANNER)) {
            if (failure.getTypeFailure() != null) {
                switch (failure.getTypeFailure()) {
                    case FAIL_LOW_BATTERY:
                        execEmergencyLandingOffboard(communicationGCS);
                        break;
                    case FAIL_SYSTEM_MOSA:
                        execEmergencyLandingOffboard(communicationGCS);
                        break;
                    case FAIL_BASED_INSERT_FAILURE:
                        if (typeAction.equals(TypeInputCommand.CMD_EMERGENCY_LANDING)) {
                            execEmergencyLandingOffboard(communicationGCS);
                        } 
                        break;
                    default:
                        break;
                }
            }
        }
        stateReplanning = StateReplanning.READY;
    }
    
    private void execEmergencyLandingOffboard(CommunicationGCS communicationGCS) {
        boolean itIsOkEmergencyLanding = emergenyLandingOffboard(communicationGCS);
        if (!itIsOkEmergencyLanding) {
            if (configGlobal.hasParachute()) {
                openParachute();
            } else {
                landVertical();
            }
        }
    }
    
    private boolean emergenyLandingOffboard(CommunicationGCS communicationGCS) {
        double navSpeed = drone.getListParameters().getValue("WPNAV_SPEED");
        dataAcquisition.changeNavigationSpeed(navSpeed / 10);

        String attributes = configGlobal.getDirFiles() + ";" + configGlobal.getFileGeoBase() 
                + ";" + configLocal.getDirReplanner() + ";" + configLocal.getCmdExecReplanner()
                + ";" + configLocal.getTypeAltitudeDecay() + ";" + configLocal.getTimeExec()
                + ";" + configLocal.getQtdWaypoints() + ";" + configLocal.getDelta();
        communicationGCS.sendDataReplannerInGCS(attributes);
        
        boolean hasResult = false;
        do{
            hasResult = communicationGCS.hasReceiveRouteGCS();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                
            }
        }while(!hasResult);
        
        String msgRoute = communicationGCS.getRouteReplannerGCS();
        
        dataAcquisition.changeNavigationSpeed(navSpeed);
        
        if (msgRoute.equals("failure")){
            System.out.println("Route GCS [Failure]: " + msgRoute);
            return false;
        }else{
            System.out.println("Route GCS [Sucess]: " + msgRoute);
            dataAcquisition.setMission(msgRoute);
            return true;
        }
    }

    public void actionToDoSomethingOnboard(Failure failure) {
        stateReplanning = StateReplanning.REPLANNING;
        if (configLocal.getSystemExec().equals(TypeSystemExecIFA.REPLANNER)) {
            if (failure.getTypeFailure() != null) {
                switch (failure.getTypeFailure()) {
                    case FAIL_AP_POWEROFF:
                        if (configGlobal.hasParachute()) {
                            openParachute();
                        } else {
                            if (configGlobal.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
                                landVertical();
                            }
                        }
                        break;
                    case FAIL_AP_EMERGENCY:
                        if (configGlobal.hasParachute()) {
                            openParachute();
                        } else {
                            if (configGlobal.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
                                landVertical();
                            }
                        }
                        break;
                    case FAIL_AP_CRITICAL:
                        execEmergencyLanding();
                        break;
                    case FAIL_GPS:
                        if (configGlobal.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
                            landVertical();
                        } else {
                            if (configGlobal.hasParachute()) {
                                openParachute();
                            } 
                        }
                        break;
                    case FAIL_ENGINE:
                        if (configGlobal.hasParachute()) {
                            openParachute();
                        } else {
                            if (configGlobal.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
                                landVertical();
                            }
                        }
                        break;
                    case FAIL_SYSTEM_IFA:
                        if (configGlobal.hasParachute()){
                            openParachute();
                        } else {
                            if (configGlobal.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
                                landVertical();
                            }
                        }
                        break;
                    case FAIL_LOW_BATTERY:
                        execEmergencyLanding();
                        break;
                    case FAIL_BATTERY_OVERHEATING:
                        execEmergencyLanding();
                        break;
                    case FAIL_SYSTEM_MOSA:
                        execEmergencyLanding();
                        break;
                    case FAIL_BASED_INSERT_FAILURE:
                        if (typeAction.equals(TypeInputCommand.CMD_EMERGENCY_LANDING)) {
                            execEmergencyLanding();
                        } else if (typeAction.equals(TypeInputCommand.CMD_LAND)) {
                            if (configGlobal.getTypeAircraft().equals(TypeAircraft.ROTARY_WING)){
                                landVertical();
                            }
                        } else if (typeAction.equals(TypeInputCommand.CMD_RTL)) {
                            RTL();
                        }
                        break;
                    case FAIL_BAD_WEATHER:
                        RTL();
                    default:
                        break;
                }
            }
        } else if (configLocal.getSystemExec().equals(TypeSystemExecIFA.FIXED_ROUTE)) {
            boolean respF = sendFixedRouteStaticToDrone();
            if (respF) {
                stateReplanning = StateReplanning.READY;
                StandardPrints.printMsgEmph("send fixed mission with success");
            } else {
                stateReplanning = StateReplanning.DISABLED;
                StandardPrints.printMsgWarning("send fixed mission to drone failure");
                return;
            }
        }
        stateReplanning = StateReplanning.READY;
    }

    /**
     * Este comando chama o algoritmo de pouso emergencial caso algo dê errado
     * então o paraquedas é disparado.
     */
    private void execEmergencyLanding() {
        boolean itIsOkEmergencyLanding = emergenyLanding();
        if (!itIsOkEmergencyLanding) {
            if (configGlobal.hasParachute()) {
                openParachute();
            } else {
                landVertical();
            }
        }
    }

    /**
     * Este comando calcula uma rota de pouso emergencial usando algum algoritmo
     * configurado para isso, então passa a nova rota para o AutoPilot.
     *
     * @return true - se ocorrer tudo bem.
     * <br> false - caso contrário.
     */
    private boolean emergenyLanding() {
        double navSpeed = drone.getListParameters().getValue("WPNAV_SPEED");
        dataAcquisition.changeNavigationSpeed(navSpeed / 10);

        StandardPrints.printMsgEmph("decison making -> emergeny landing: " + typeAction);
        if (configLocal.getTypeReplanner().equals(TypeReplanner.GH4S)) {
            replanner = new GH4s(drone);
        } else if (configLocal.getTypeReplanner().equals(TypeReplanner.GA4S)) {
            replanner = new GA4s(drone);
        } else if (configLocal.getTypeReplanner().equals(TypeReplanner.MPGA4S)) {
            replanner = new MPGA4s(drone);
        } else if (configLocal.getTypeReplanner().equals(TypeReplanner.DE4S)) {
            replanner = new DE4s(drone);
        } else if (configLocal.getTypeReplanner().equals(TypeReplanner.GA_GA_4S)) {
            replanner = new GA_GA_4s(drone);
        } else if (configLocal.getTypeReplanner().equals(TypeReplanner.GA_GH_4S)) {
            replanner = new GA_GH_4s(drone);
        } else if (configLocal.getTypeReplanner().equals(TypeReplanner.FIXED_ROUTE4s)) {
            replanner = new FixedRoute4s(drone);
        }
        replanner.clearLogs();
        boolean itIsOkExec = replanner.exec();
        if (!itIsOkExec) {
            return false;
        }

        dataAcquisition.changeNavigationSpeed(navSpeed);
        
        Mission mission = new Mission();
        String path = configLocal.getDirReplanner() + "routeGeo.txt";

        if (configLocal.getTypeReplanner().equals(TypeReplanner.GA_GA_4S)) {
            System.out.println("TypeReplanner.GA_GA_4S");
            path = "../Modules-IFA/GA4s/" + "routeGeo.txt";
        } else if (configLocal.getTypeReplanner().equals(TypeReplanner.GA_GH_4S)) {
            String best = ((GA_GH_4s) replanner).bestMethod();
            if (best.equals("GA")) {
                path = "../Modules-IFA/GA4s/" + "routeGeo.txt";
            } else if (best.equals("GH")) {
                path = "../Modules-IFA/GH4s/" + "routeGeo.txt";
            } else {
                return false;
            }
        }

        boolean resp = readFileRoute(mission, path, 0);
        if (!resp) {
            return false;
        }
        if (mission.getMission().size() > 0) {
            mission.printMission();
            dataAcquisition.setMission(mission);
        }
        return true;
    }

    /**
     * Este comando guia a aeronave até a posição especificada e então pousa
     * verticalmente quando o veículo eh um multi-rotor.
     *
     * @param lat latitude da região onde ocorrerá o pouso.
     * @param lng longitude da região onde ocorrerá o pouso.
     */
    private void land(double lat, double lng) {
        StandardPrints.printMsgEmph("decison making -> land");
        Waypoint wpt = new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0);
        dataAcquisition.setWaypoint(wpt);
    }

    /**
     * Este comando pousa a aeronave verticalmente no local em que a aeronave
     * estava quando esse comando foi chamado.
     */
    private void landVertical() {
        StandardPrints.printMsgEmph("decison making -> land vertical");
        Waypoint wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
        dataAcquisition.setWaypoint(wpt);
    }

    /**
     * Este comando faz a aeronave voltar ao ponto de lançamento, para isso a
     * aeronave primeiramente sobe até a altitude RTL_ALT então volta ao local
     * de lançamento e por fim pousa na vertical quando é um multi-rotor.
     */
    private void RTL() {
        StandardPrints.printMsgEmph("decison making -> rtl");
        Waypoint wpt = new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);
        dataAcquisition.setWaypoint(wpt);
    }

    /**
     * Este comando é responsável por fazer o disparo do paraquedas. Melhorar no
     * futuro: Deve-se desarmar o motor e entao abrir o paraquedas.
     */
    private void openParachute() {
        StandardPrints.printMsgEmph("decison making -> open parachute");
        ParachuteControl parachute = new ParachuteControl();
        boolean isOpen = parachute.open();
        if (!isOpen) {
            stateReplanning = StateReplanning.DISABLED;
        }
    }

    /**
     * Retorna o estado do planejador [WAITING, REPLANNING, READY, DISABLED].
     *
     * @return estado do planejador.
     */
    public StateReplanning getStateReplanning() {
        return stateReplanning;
    }

    /**
     * Define o tipo de ação executada pelo sistema de decisão do IFA [CMD_MPGA,
     * CMD_LAND, CMD_RTL].
     *
     * @param action representa a ação a ser executada.
     */
    public void setTypeAction(String action) {
        this.typeAction = action;
    }

    private boolean sendFixedRouteStaticToDrone() {
        StandardPrints.printMsgEmph("send fixed route static");
        String path = configLocal.getDirFixedRoute() + configLocal.getFileFixedRoute();
        Mission route = new Mission();
        boolean resp = readFileRoute(route, path, 2);
        if (!resp) {
            return false;
        }
        route.printMission();
        if (route.getMission().size() > 0) {
            dataAcquisition.setMission(route);
        }
        return true;
    }

    private boolean readFileRoute(Mission wps, String path, int time) {
        try {
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
                if (time > 1) {
                    wps.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
                }
                time++;
            }
            if (wps.getMission().size() > 0) {
                wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
            }
            return true;
        } catch (FileNotFoundException ex) {
            StandardPrints.printMsgWarning("Warning [FileNotFoundException]: readFileRoute()");
            return false;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException]: readFileRoute()");
            return false;
        }
    }

}
