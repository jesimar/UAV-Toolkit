package uav.ifa.module.communication_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.module.sensors_actuators.BuzzerControl;
import uav.generic.module.sensors_actuators.CameraControl;
import uav.generic.module.sensors_actuators.LEDControl;
import uav.generic.module.sensors_actuators.ParachuteControl;
import uav.generic.module.sensors_actuators.SprayingControl;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeInputCommand;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.reader.ReaderFileConfigGlobal;
import uav.ifa.module.decision_making.DecisionMaking;

/**
 * Classe que faz o controle da comunicação com GCS.
 * @author Jesimar S. Arantes
 */
public class CommunicationGCS {
    
    private ServerSocket server;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private boolean hasFailure;
    private boolean hasFailureBadWeather;
    private boolean hasReceiveRouteGCS;
    private String routeReplannerGCS;
    private String typeAction;
    private final ReaderFileConfigGlobal configGlobal;
    private final Drone drone;
    private final DecisionMaking decisonMaking;

    /**
     * Class contructor.
     * @param drone instance of the drone
     * @param decisonMaking instance of the DecisionMaking
     */
    public CommunicationGCS(Drone drone, DecisionMaking decisonMaking) {
        this.drone = drone;
        this.decisonMaking = decisonMaking;
        configGlobal = ReaderFileConfigGlobal.getInstance();
        hasFailure = false;
        hasFailureBadWeather = false;
        hasReceiveRouteGCS = false;
        typeAction = "";
    }

    public void startServerIFA() {
        StandardPrints.printMsgEmph("waiting a connection to UAV-GCS ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(configGlobal.getPortNetworkIFAandGCS());
                    socket = server.accept();//wait the connection
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = new PrintWriter(socket.getOutputStream(), true);
                    StandardPrints.printMsgEmph("GCS connected in IFA ...");
                } catch (IOException ex) {
                    StandardPrints.printMsgWarning("Warning [IOException] startServerGCS()");
                    ex.printStackTrace();
                }
            }
        });
    }

    public void receiveData() {
        StandardPrints.printMsgEmph("listening to the connection of UAV-GCS ...");
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
                                } else if (answer.contains("mission")){
                                    hasReceiveRouteGCS = true;
                                    routeReplannerGCS = answer;
                                } else if (answer.contains("cmd: ")){
                                    decisonMaking.interpretCommand(answer);
                                }
                            }
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
                    }
                } catch (InterruptedException ex) {
                    
                } catch (IOException ex) {
                    
                }
            }
        });
    }
    
    public void sendDataDrone() {
        StandardPrints.printMsgEmph("sending data to the connection of UAV-GCS ...");
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
                    StandardPrints.printMsgWarning("Warning [InterruptedException] sendData()");
                    ex.printStackTrace();
                } 
            }
        });
    }
    
    public void sendDataReplannerInGCS(String attributes){
        if (output != null){
            output.println(TypeMsgCommunication.IFA_GCS_REPLANER + attributes);
        }
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

    public void close() {
        try {
            output.close();
            input.close();
            socket.close();
            server.close();
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] close()");
            ex.printStackTrace();
        }
    }
}
