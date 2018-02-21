package uav.ifa.module.communication_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;

/**
 *
 * @author jesimar
 */
public class CommunicationFailure {
    
    private ServerSocket server;
    private Socket socket;
    private BufferedReader input;

    private final int PORT;
    private final int SLEEP_TIME_RECEIVE_MSG = 200;
    private boolean failure = false;
    private String typeAction = "";

    public CommunicationFailure() {
        this.PORT = 5556;
    }

    public CommunicationFailure(int port) {
        this.PORT = port;
    }

    public void startServerFailure() {
        try {
            StandardPrints.printMsgEmph("waiting a connection to UAV-Insert-Failure ...");
            server = new ServerSocket(PORT);
            socket = server.accept();//aguarda conexao
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
                            if (answer.equals("MPGA")){
                                failure = true;
                                typeAction = "MPGA";
                            } else if (answer.equals("LAND")){
                                failure = true;
                                typeAction = "LAND";
                            } else if (answer.equals("RTL")){
                                failure = true;
                                typeAction = "RTL";
                            }
                        } else {
                            Thread.sleep(SLEEP_TIME_RECEIVE_MSG);
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
    
    public boolean getFailure(){
        return failure;
    }
    
    public String getTypeAction(){
        return typeAction;
    }
}
