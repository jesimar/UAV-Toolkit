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
import uav.generic.struct.ReaderFileConfigGlobal;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.states.StateCommunication;

/**
 *
 * @author Jesimar S. Arantes
 */
public class CommunicationControl {
    
    private ServerSocket server;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private final Drone drone;
    private StateCommunication stateCommunication;
    private boolean mosaDisabled;
    private final ReaderFileConfigGlobal configGlobal;

    public CommunicationControl(Drone drone) {
        this.drone = drone;
        stateCommunication = StateCommunication.WAITING;
        mosaDisabled = false;
        configGlobal = ReaderFileConfigGlobal.getInstance();
    }

    public void startServerIFA() {
        try {
            StandardPrints.printMsgEmph("waiting a connection from MOSA ...");
            server = new ServerSocket(configGlobal.getPortNetworkIFAandMOSA());
            socket = server.accept();//wait the connection
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
                            if (answer.equals(TypeMsgCommunication.MOSA_IFA_INITIALIZED)){
                                sendData(TypeMsgCommunication.IFA_MOSA_HOMELOCATION + 
                                        drone.getHomeLocation().string());
                                sendData(TypeMsgCommunication.IFA_MOSA_START);
                            } else if (answer.equals(TypeMsgCommunication.MOSA_IFA_STARTED)){
                                //Não precisa fazer nada
                            } else if (answer.equals(TypeMsgCommunication.MOSA_IFA_STOPPED)){
                                //Não precisa fazer nada
                            } else if (answer.equals(TypeMsgCommunication.MOSA_IFA_DISABLED)){
                                mosaDisabled = true;
                            }
                        } else {
                            Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
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
    
    public StateCommunication getStateCommunication() {
        return stateCommunication;
    } 

    public boolean isMosaDisabled() {
        return mosaDisabled;
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

}
