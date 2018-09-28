package uav.ifa.module.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.module.actuators.BuzzerControl;
import uav.generic.module.sensors.CameraControl;
import uav.generic.module.actuators.LEDControl;
import uav.generic.module.actuators.ParachuteControl;
import uav.generic.module.actuators.SprayingControl;
import uav.generic.module.comm.Communication;
import uav.generic.module.comm.Server;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeInputCommand;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.struct.states.StateCommunication;
import uav.ifa.module.decision_making.Controller;

/**
 * Classe que faz o controle da comunicação com GCS.
 * @author Jesimar S. Arantes
 */
public class CommunicationGCS extends Communication implements Server{
    
    private ServerSocket server;
    private final ReaderFileConfig config;
    private final Drone drone;
    private final Controller controller;
    private boolean hasFailure;
    private boolean hasFailureBadWeather;
    private boolean hasReceiveRouteGCS;
    private String routeReplannerGCS;
    private String typeAction;
    

    /**
     * Class contructor.
     * @param drone instance of the drone
     * @param controller instance of the DecisionMaking
     */
    public CommunicationGCS(Drone drone, Controller controller) {
        this.drone = drone;
        this.controller = controller;
        this.stateCommunication = StateCommunication.WAITING;
        this.config = ReaderFileConfig.getInstance();
        this.hasFailure = false;
        this.hasFailureBadWeather = false;
        this.hasReceiveRouteGCS = false;
        this.typeAction = "";
    }

    @Override
    public void startServer() {
        StandardPrints.printMsgEmph("IFA waiting the connection to UAV-GCS ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(config.getPortNetworkIFAandGCS());
                    socket = server.accept();//wait the connection
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = new PrintWriter(socket.getOutputStream(), true);
                    StandardPrints.printMsgEmph("IFA connected in UAV-GCS");
                } catch (IOException ex) {
                    StandardPrints.printMsgWarning("Warning [IOException] startServer()");
                    ex.printStackTrace();
                    stateCommunication = StateCommunication.DISABLED;
                }
            }
        });
    }

    @Override
    public void receiveData() {
        stateCommunication = StateCommunication.LISTENING;
        StandardPrints.printMsgEmph("IFA listening to the connection with UAV-GCS ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (input != null){
                            String answer = input.readLine();
                            if (answer != null) {
                                StandardPrints.printMsgYellow("Data: " + answer);
                                answer = answer.toLowerCase();
                                if (answer.equals(TypeInputCommand.CMD_BAD_WEATHER)){
                                    hasFailureBadWeather = true;
                                    typeAction = TypeInputCommand.CMD_BAD_WEATHER;
                                } else if (answer.equals(TypeInputCommand.CMD_EMERGENCY_LANDING)){
                                    hasFailure = true;
                                    typeAction = TypeInputCommand.CMD_EMERGENCY_LANDING;
                                } else if (answer.equals(TypeInputCommand.CMD_LAND)){
                                    hasFailure = true;
                                    typeAction = TypeInputCommand.CMD_LAND;
                                } else if (answer.equals(TypeInputCommand.CMD_RTL)){
                                    hasFailure = true;
                                    typeAction = TypeInputCommand.CMD_RTL;
                                } else if (answer.equals(TypeInputCommand.CMD_BUZZER)){
                                    BuzzerControl buzzer = new BuzzerControl();
                                    buzzer.turnOnBuzzer();
                                } else if (answer.equals(TypeInputCommand.CMD_ALARM)){
                                    BuzzerControl buzzer = new BuzzerControl();
                                    buzzer.turnOnAlarm();
                                } else if (answer.equals(TypeInputCommand.CMD_PICTURE)){
                                    CameraControl camera = new CameraControl();
                                    camera.takeAPicture();
                                } else if (answer.equals(TypeInputCommand.CMD_VIDEO)){
                                    CameraControl camera = new CameraControl();
                                    camera.makeAVideo();
                                } else if (answer.equals(TypeInputCommand.CMD_PHOTO_IN_SEQUENCE)){
                                    CameraControl camera = new CameraControl();
                                    camera.photoInSequence();
                                } else if (answer.equals(TypeInputCommand.CMD_LED)){
                                    LEDControl led = new LEDControl();
                                    led.turnOnLED();
                                } else if (answer.equals(TypeInputCommand.CMD_BLINK)){
                                    LEDControl led = new LEDControl();
                                    led.blinkLED();
                                } else if (answer.equals(TypeInputCommand.CMD_SPRAYING)){
                                    SprayingControl spraying = new SprayingControl();
                                    spraying.openSpraying();
                                } else if (answer.equals(TypeInputCommand.CMD_OPEN_PARACHUTE)){
                                    ParachuteControl parachute = new ParachuteControl();
                                    parachute.open();
                                } else if (answer.contains(TypeInputCommand.CMD_MISSION)){
                                    hasReceiveRouteGCS = true;
                                    routeReplannerGCS = answer;
                                } else if (answer.contains("cmd: ")){
                                    controller.interpretCommand(answer);
                                }
                            }
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
                    }
                } catch (InterruptedException ex) {
                    stateCommunication = StateCommunication.DISABLED;
                } catch (IOException ex) {
                    stateCommunication = StateCommunication.DISABLED;
                }
            }
        });
    }

    @Override
    public void close() {
        super.close();
        try {
            server.close();
            stateCommunication = StateCommunication.DISABLED;
        } catch (IOException ex) {
            System.err.println("Warning [IOException] close()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        } catch (Exception ex) {
            System.err.println("Warning [Exception] close()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        }
    }
    
    public void sendDataDrone() {
        StandardPrints.printMsgEmph("IFA sending data drone to UAV-GCS ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (output != null){
                            output.println(TypeMsgCommunication.IFA_GCS_INFO + drone.toString());
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
                    }
                } catch (InterruptedException ex) {
                    StandardPrints.printMsgWarning("Warning [InterruptedException] sendDataDrone()");
                    ex.printStackTrace();
                    stateCommunication = StateCommunication.DISABLED;
                } 
            }
        });
    }
    
    public boolean hasFailure(){
        return hasFailure;
    }
    
    public boolean hasFailureBadWeather(){
        return hasFailureBadWeather;
    }

    public boolean hasReceiveRouteGCS() {
        return hasReceiveRouteGCS;
    }
    
    public String getRouteReplannerGCS(){
        return routeReplannerGCS;
    }
    
    public String getTypeAction(){
        return typeAction;
    }
    
}
