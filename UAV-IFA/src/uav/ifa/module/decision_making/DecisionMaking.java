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
import uav.generic.struct.constants.TypeInputCommand;
import uav.generic.struct.constants.TypeReplanner;
import uav.generic.struct.constants.TypeSystemExecIFA;
import uav.generic.struct.reader.ReaderFileConfigGlobal;
import uav.generic.struct.states.StateReplanning;
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
    
    public void actionToDoSomething(Failure failure) {
        stateReplanning = StateReplanning.REPLANNING;
        if (configLocal.getSystemExec().equals(TypeSystemExecIFA.REPLANNER)){
            if (failure.getTypeFailure() != null){
                switch (failure.getTypeFailure()) {
                    case FAIL_AP_POWEROFF:
                        if (configGlobal.hasParachute()){
                            openParachute();
                        } else{
                            landVertical();
                        }
                        break;
                    case FAIL_AP_EMERGENCY:
                        if (configGlobal.hasParachute()){
                            openParachute();
                        } else{
                            landVertical();
                        }
                        break;
                    case FAIL_GPS:
                        if (configGlobal.hasParachute()){
                            openParachute();
                        } else{
                            landVertical();
                        }
                        break;
                    case FAIL_ENGINE:
                        if (configGlobal.hasParachute()){
                            openParachute();
                        } else{
                            landVertical();
                        }
                        break;
                    case FAIL_SYSTEM_IFA:
                        if (configGlobal.hasParachute()){
                            openParachute();
                        } else{
                            landVertical();
                        }
                        break;
                    case FAIL_BATTERY:
                        execEmergencyLanding();
                        break;
                    case FAIL_SYSTEM_MOSA:
                        execEmergencyLanding();
                        break;
                    case FAIL_BASED_INSERT_FAILURE:
                        if (typeAction.equals(TypeInputCommand.CMD_EMERGENCY_LANDING)){
                            execEmergencyLanding();
                        } else if (typeAction.equals(TypeInputCommand.CMD_LAND)){
                            landVertical();
                        } else if (typeAction.equals(TypeInputCommand.CMD_RTL)){
                            RTL();
                        }
                        //land(-22.00593264981567,-47.89870966454083);             
                        break;
                    default:
                        break;
                }
            }
        }
        stateReplanning = StateReplanning.READY;
    }
    
    /**
     * Este comando chama o algoritmo de pouso emergencial caso algo dê errado
     * então o paraquedas é disparado.
     */
    private void execEmergencyLanding(){
        boolean itIsOkEmergencyLanding = emergenyLanding();
        if (!itIsOkEmergencyLanding){
            if (configGlobal.hasParachute()){
                openParachute();
            }else{
                landVertical();
            }
        }
    }

    /**
     * Este comando calcula uma rota de pouso emergencial usando algum algoritmo 
     * configurado para isso, então passa a nova rota para o AutoPilot.
     * @return true - se ocorrer tudo bem.
     * <br>    false - caso contrário.
     */
    private boolean emergenyLanding() {
        double navSpeed = drone.getListParameters().getValue("WPNAV_SPEED");
        dataAcquisition.changeNavigationSpeed(navSpeed/10);

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
        if (!itIsOkExec){
            return false;
        }
        
        dataAcquisition.changeNavigationSpeed(navSpeed);
        
        try{
            Mission mission = new Mission();
            String path = configLocal.getDirReplanner() + "routeGeo.txt";
            
            if (configLocal.getTypeReplanner().equals(TypeReplanner.GA_GA_4S)) {
                System.out.println("TypeReplanner.GA_GA_4S");
                path = "../Modules-IFA/GA4s/" + "routeGeo.txt";
            } else if (configLocal.getTypeReplanner().equals(TypeReplanner.GA_GH_4S)) {
                String best = ((GA_GH_4s)replanner).bestMethod();
                if (best.equals("GA")){
                    path = "../Modules-IFA/GA4s/" + "routeGeo.txt";
                } else if (best.equals("GH")) {
                    path = "../Modules-IFA/GH4s/" + "routeGeo.txt";
                } else {
                    return false;
                }
            }
            
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
                if (i > 1){//trabalhar nessa linha
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));  
                }
                i++;
            }
            if (mission.getMission().size() > 0){
                mission.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0)); 
                mission.printMission();
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
    
    /**
     * Este comando guia a aeronave até a posição especificada e então pousa 
     * verticalmente quando o veículo eh um multi-rotor.
     * @param lat latitude da região onde ocorrerá o pouso.
     * @param lng longitude da região onde ocorrerá o pouso.
     */
    private void land(double lat, double lng){ 
        StandardPrints.printMsgEmph("decison making -> land");
        Waypoint wpt = new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0);
        dataAcquisition.setWaypoint(wpt);
    }
    
    /**
     * Este comando pousa a aeronave verticalmente no local em que a aeronave 
     * estava quando esse comando foi chamado.
     */      
    private void landVertical(){ 
        StandardPrints.printMsgEmph("decison making -> land vertical");
        Waypoint wpt = new Waypoint(TypeWaypoint.LAND_VERTICAL, 0.0, 0.0, 0.0);
        dataAcquisition.setWaypoint(wpt);
    }
    
    /**
     * Este comando faz a aeronave voltar ao ponto de lançamento, para isso a 
     * aeronave primeiramente sobe até a altitude RTL_ALT então volta ao local
     * de lançamento e por fim pousa na vertical quando é um multi-rotor. 
     */
    private void RTL(){
        StandardPrints.printMsgEmph("decison making -> rtl");
        Waypoint wpt = new Waypoint(TypeWaypoint.RTL, 0.0, 0.0, 0.0);        
        dataAcquisition.setWaypoint(wpt);
    }
    
    /**
     * Este comando é responsável por fazer o disparo do paraquedas.
     * Melhorar no futuro: Deve-se desarmar o motor e entao abrir o paraquedas.
     */
    private void openParachute(){
        StandardPrints.printMsgEmph("decison making -> open parachute");
        ParachuteControl parachute = new ParachuteControl();
        boolean isOpen = parachute.open();
        if (!isOpen){
            stateReplanning = StateReplanning.DISABLED;
        }
    }
    
    /**
     * Retorna o estado do planejador [WAITING, REPLANNING, READY, DISABLED].
     * @return estado do planejador.
     */
    public StateReplanning getStateReplanning() {
        return stateReplanning;
    }
    
    /**
     * Define o tipo de ação executada pelo sistema de decisão do IFA
     * [CMD_MPGA, CMD_LAND, CMD_RTL].
     * @param action representa a ação a ser executada.
     */
    public void setTypeAction(String action){
        this.typeAction = action;
    }
    
}
