package uav.ifa.module.communication_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.hardware.aircraft.Drone;
import uav.ifa.struct.states.StateCommunication;

/**
 *
 * @author jesimar
 */
public class CommunicationControl {
    
    private ServerSocket server;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private final Drone drone;
    private StateCommunication stateCommunication;
    private boolean mosaDisabled;

    private final int PORT;
    private final int SLEEP_TIME_RECEIVE_MSG = 100;    

    public CommunicationControl(Drone drone) {
        this.drone = drone;
        this.PORT = 5555;
        stateCommunication = StateCommunication.WAITING;
        mosaDisabled = false;
    }

    public CommunicationControl(Drone drone, int port) {
        this.drone = drone;
        this.PORT = port;
        stateCommunication = StateCommunication.WAITING;
        mosaDisabled = false;
    }

    public void startServerIFA() {
        try {
            StandardPrints.printMsgEmph("waiting a connection from MOSA ...");
            server = new ServerSocket(PORT);
            socket = server.accept();//aguarda conexao
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            StandardPrints.printMsgEmph("MOSA connected in IFA ...");
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] startServerIFA()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        }
    }

    public void receiveData() {
        stateCommunication = StateCommunication.LISTENING;
        StandardPrints.printMsgEmph("listening to the connection with MOSA...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String answer = input.readLine();
                        if (answer != null) {
                            StandardPrints.printMsgYellow("Data MOSA: " + answer);
                            if (answer.equals("StateMOSA.INITIALIZED")){
                                sendData("HomeLocation: " + drone.getHomeLocation().string());
                                sendData("MOSA.START");
                            } else if (answer.equals("MOSA.STARTED")){
                                //Nao precisa fazer nada
                            } else if (answer.equals("MOSA.STOPPED")){
                                //Nao precisa fazer nada
                            } else if (answer.equals("StateMOSA.DISABLED")){
                                mosaDisabled = true;
                            }
                        } else {
                            Thread.sleep(SLEEP_TIME_RECEIVE_MSG);
                        }
                    }
                } catch (InterruptedException ex) {
                    StandardPrints.printMsgWarning("Warning [InterruptedException] receiveData()");
                    ex.printStackTrace();
                    stateCommunication = StateCommunication.DISABLED;
                } catch (IOException ex) {
                    StandardPrints.printMsgWarning("Warning [IOException] receiveData()");
                    ex.printStackTrace();
                    stateCommunication = StateCommunication.DISABLED;
                }
            }
        });
    }

    public void sendData(String msg) {
        output.println(msg);
    }

    public void close() {
        try {
            output.close();
            input.close();
            socket.close();
            server.close();
            stateCommunication = StateCommunication.DISABLED;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] close()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        }
    }
    
    public StateCommunication getStateCommunication() {
        return stateCommunication;
    } 

    public boolean isMosaDisabled() {
        return mosaDisabled;
    }

}
