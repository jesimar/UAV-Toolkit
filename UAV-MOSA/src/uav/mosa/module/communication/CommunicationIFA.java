package uav.mosa.module.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.module.comm.Client;
import uav.generic.module.comm.Communication;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.states.StateCommunication;

/**
 * The class controls communication with IFA.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 * @see Communication
 * @see Client
 */
public class CommunicationIFA extends Communication implements Client{         
    
    private final Drone drone;
    private final ReaderFileConfig config;
    private boolean startMission;
    
    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @since version 2.0.0
     */
    public CommunicationIFA(Drone drone){      
        this.drone = drone;
        this.stateCommunication = StateCommunication.WAITING;
        this.config = ReaderFileConfig.getInstance();
        this.startMission = false;
    }
    
    /**
     * Connect with the server
     * @since version 4.0.0
     */
    @Override
    public void connectServer(){        
        try{
            StandardPrints.printMsgEmph("MOSA connecting in the IFA ...");
            socket = new Socket(config.getHostIFA(), config.getPortNetworkIFAandMOSA());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            StandardPrints.printMsgEmph("MOSA connected in IFA");
        }catch(IOException ex){
            StandardPrints.printMsgWarning("Warning [IOException] connectClient()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        }
    }
    
    /**
     * Treats the data to be received
     * @since version 2.0.0
     */
    @Override
    public void receiveData(){
        stateCommunication = StateCommunication.LISTENING;
        StandardPrints.printMsgEmph("MOSA listening to the connection with IFA ...");
        Executors.newSingleThreadExecutor().execute(new Runnable(){            
            @Override
            public void run(){
                try{
                    while (true){
                        if (input != null){
                            String answer = input.readLine();
                            if (answer != null){
                                StandardPrints.printMsgYellow("Data IFA: " + answer);
    //                            if (answer.contains(TypeMsgCommunication.IFA_MOSA_HOMELOCATION)){
    //                                String home = answer.substring(23);                               
    //                                String h[] = home.split(", ");
    //                                double lat = Double.parseDouble(h[0]);
    //                                double lng = Double.parseDouble(h[1]);
    //                                double alt = Double.parseDouble(h[2]);
    //                                drone.defineHomeLocation(lat, lng, alt);
    //                            } else 
                                if (answer.equals(TypeMsgCommunication.IFA_MOSA_START)){
                                    startMission = true;
                                    sendData(TypeMsgCommunication.MOSA_IFA_STARTED);
                                } else if (answer.equals(TypeMsgCommunication.IFA_MOSA_STOP)){
                                    sendData(TypeMsgCommunication.MOSA_IFA_STOPPED);
                                    Thread.sleep(100);
                                    System.exit(1);
                                }
                            } 
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
                    }
                } catch (InterruptedException ex){
                    StandardPrints.printMsgError2("Error [InterruptedException] receiveData()");
                    ex.printStackTrace();
                    stateCommunication = StateCommunication.DISABLED;
                } catch (IOException ex) {
                    StandardPrints.printMsgError2("Error [IOException] receiveData()");
                    ex.printStackTrace();
                    stateCommunication = StateCommunication.DISABLED;
                } 
            }
        });
    }     

    public boolean isStartMission() {
        return startMission;
    }

}
