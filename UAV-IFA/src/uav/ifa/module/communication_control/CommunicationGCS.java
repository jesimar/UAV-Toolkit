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
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeInputCommand;
import uav.generic.struct.reader.ReaderFileConfigGlobal;

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
    private String typeAction;
    private final ReaderFileConfigGlobal configGlobal;
    private final Drone drone;

    /**
     * Class contructor.
     * @param drone instance of the drone
     */
    public CommunicationGCS(Drone drone) {
        this.drone = drone;
        configGlobal = ReaderFileConfigGlobal.getInstance();
        hasFailure = false;
        typeAction = "";
    }

    public void startServerGCS() {
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
                                StandardPrints.printMsgYellow("Data FAILURE: " + answer);
                                answer = answer.toLowerCase();
                                if (answer.equals(TypeInputCommand.CMD_EMERGENCY_LANDING)){
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
                                } else if (answer.equals(TypeInputCommand.CMD_LED)){
                                    LEDControl led = new LEDControl();
                                    led.blinkLED();
                                }
                            } else {
                                Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
                            }
                        }else{
                            Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
                        }
                    }
                } catch (InterruptedException ex) {
                    StandardPrints.printMsgWarning("Warning [InterruptedException] receiveData()");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    StandardPrints.printMsgWarning("Warning [IOException] receiveData()");
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public void sendData() {
        StandardPrints.printMsgEmph("sending data to the connection of UAV-GCS ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (output != null){
                            output.println(drone.getGPS().lat + ", " + drone.getGPS().lng);
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
    
    public boolean hasFailure(){
        return hasFailure;
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
