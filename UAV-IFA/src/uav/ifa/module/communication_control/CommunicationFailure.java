package uav.ifa.module.communication_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeInputCommand;

/**
 *
 * @author Jesimar S. Arantes
 */
public class CommunicationFailure {
    
    private ServerSocket server;
    private Socket socket;
    private BufferedReader input;

    private boolean failure = false;
    private String typeAction = "";

    public CommunicationFailure() {
    }

    public void startServerFailure() {
        try {
            StandardPrints.printMsgEmph("waiting a connection to UAV-Insert-Failure ...");
            server = new ServerSocket(Constants.PORT_COMMUNICATION_BETWEEN_IFA_IF);
            socket = server.accept();//wait the connection
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] startServerFailure()");
            ex.printStackTrace();
        }
    }

    public void receiveData() {
        StandardPrints.printMsgEmph("listening to the connection of UAV-Insert-Failure ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String answer = input.readLine();
                        if (answer != null) {
                            StandardPrints.printMsgYellow("Data FAILURE: " + answer);
                            answer = answer.toLowerCase();
                            if (answer.equals(TypeInputCommand.CMD_MPGA)){
                                failure = true;
                                typeAction = TypeInputCommand.CMD_MPGA;
                            } else if (answer.equals(TypeInputCommand.CMD_LAND)){
                                failure = true;
                                typeAction = TypeInputCommand.CMD_LAND;
                            } else if (answer.equals(TypeInputCommand.CMD_RTL)){
                                failure = true;
                                typeAction = TypeInputCommand.CMD_RTL;
                            }
                        } else {
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
    
    public boolean getFailure(){
        return failure;
    }
    
    public String getTypeAction(){
        return typeAction;
    }

    public void close() {
        try {
            input.close();
            socket.close();
            server.close();
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] close()");
            ex.printStackTrace();
        }
    }
}
