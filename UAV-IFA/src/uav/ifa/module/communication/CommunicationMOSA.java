package uav.ifa.module.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.module.comm.Communication;
import uav.generic.module.comm.Server;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.states.StateCommunication;

/**
 * Classe que faz o controle da comunicação com o sistema MOSA.
 * @author Jesimar S. Arantes
 */
public class CommunicationMOSA extends Communication implements Server{
    
    private ServerSocket server;
    
    private final Drone drone;    
    private final ReaderFileConfig config;
    private boolean mosaDisabled;

    /**
     * Class constructor
     * @param drone instance of the aircraft
     */
    public CommunicationMOSA(Drone drone) {
        this.drone = drone;
        this.stateCommunication = StateCommunication.WAITING;
        this.mosaDisabled = false;
        this.config = ReaderFileConfig.getInstance();
    }

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

    public boolean isMosaDisabled() {
        return mosaDisabled;
    }

}
