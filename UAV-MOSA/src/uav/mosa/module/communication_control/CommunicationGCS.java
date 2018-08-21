package uav.mosa.module.communication_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.reader.ReaderFileConfig;

/**
 * Classe que faz o controle da comunicação com GCS.
 * @author Jesimar S. Arantes
 */
public class CommunicationGCS {
    
    private ServerSocket server;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private boolean hasReceiveRouteGCS;
    private String routePlannerGCS;
    private final ReaderFileConfig config;

    /**
     * Class contructor.
     */
    public CommunicationGCS() {
        config = ReaderFileConfig.getInstance();
        hasReceiveRouteGCS = false;
    }

    public void startServerMOSA() {
        StandardPrints.printMsgEmph("waiting a connection to UAV-GCS ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(config.getPortNetworkMOSAandGCS());
                    socket = server.accept();//wait the connection
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    output = new PrintWriter(socket.getOutputStream(), true);
                    StandardPrints.printMsgEmph("GCS connected in MOSA ...");
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
                                if (answer.contains("mission")){
                                    hasReceiveRouteGCS = true;
                                    routePlannerGCS = answer;
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
    
    public void sendDataPlannerInGCS(String attributes){
        if (output != null){
            output.println(TypeMsgCommunication.IFA_GCS_PLANNER + attributes);
        }
    }

    public boolean hasReceiveRouteGCS() {
        return hasReceiveRouteGCS;
    }
    
    public String getRoutePlannerGCS(){
        return routePlannerGCS;
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
