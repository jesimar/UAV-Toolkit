package uav.ifa.module.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import lib.uav.hardware.aircraft.Drone;
import lib.uav.module.comm.Communication;
import lib.uav.module.comm.Server;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.struct.constants.Constants;
import lib.uav.struct.constants.TypeMsgCommunication;
import lib.uav.struct.states.StateCommunication;

/**
 * The class controls communication with MOSA.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 * @see Communication
 * @see Server
 */
public class CommunicationMOSA extends Communication implements Server{
    
    private ServerSocket server;
    
    private final Drone drone;    
    private final ReaderFileConfig config;
    private boolean mosaDisabled;

    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @since version 2.0.0
     */
    public CommunicationMOSA(Drone drone) {
        this.drone = drone;
        this.stateCommunication = StateCommunication.WAITING;
        this.mosaDisabled = false;
        this.config = ReaderFileConfig.getInstance();
    }

    /**
     * Start the server
     * @since version 4.0.0
     */
    @Override
    public void startServer() {
        try {
            StandardPrints.printMsgEmph("IFA waiting the connection to MOSA ...");
            server = new ServerSocket(config.getPortNetworkIFAandMOSA());
            socket = server.accept();//wait the connection
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            StandardPrints.printMsgEmph("IFA connected in MOSA");
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] startServer()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        }
    }

    /**
     * Treats the data to be received
     * @since version 2.0.0
     */
    @Override
    public void receiveData() {
        stateCommunication = StateCommunication.LISTENING;
        StandardPrints.printMsgEmph("IFA listening to the connection with MOSA ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (input != null && socket.isConnected()){//conferir linha
                            String answer = input.readLine();
                            if (answer != null) {
                                StandardPrints.printMsgYellow("Data MOSA: " + answer);
                                if (answer.equals(TypeMsgCommunication.MOSA_IFA_INITIALIZED)){
//                                    sendData(TypeMsgCommunication.IFA_MOSA_HOMELOCATION + 
//                                            drone.getHomeLocation().string());
                                    sendData(TypeMsgCommunication.IFA_MOSA_START);
                                } else if (answer.equals(TypeMsgCommunication.MOSA_IFA_STARTED)){
                                    //No need to do anything
                                } else if (answer.equals(TypeMsgCommunication.MOSA_IFA_STOPPED)){
                                    //No need to do anything
                                } else if (answer.equals(TypeMsgCommunication.MOSA_IFA_DISABLED)){
                                    mosaDisabled = true;
                                }
                            }
                        } 
                        Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
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

    /**
     * Close the communication
     * @since version 2.0.0
     */
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

    /**
     * Is MOSA disabled
     * @return {@code true} if MOSa is disabled
     *         {@code false} otherwise
     * @since version 2.0.0
     */
    public boolean isMosaDisabled() {
        return mosaDisabled;
    }

}
