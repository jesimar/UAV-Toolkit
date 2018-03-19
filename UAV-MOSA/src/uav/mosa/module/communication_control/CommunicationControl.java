package uav.mosa.module.communication_control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.struct.constants.Constants;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.states.StateCommunication;

/**
 *
 * @author Jesimar S. Arantes
 */
public class CommunicationControl {
    
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;         
    private final Drone drone;
    private StateCommunication stateCommunication;
    
    private boolean startMission;
    private boolean stopMission;
    
    public CommunicationControl(Drone drone){      
        this.drone = drone;
        stateCommunication = StateCommunication.WAITING;
        startMission = false;
        stopMission = false;
    }
    
    public void connectClient(){        
        try{
            StandardPrints.printMsgEmph("connecting in the IFA ...");
            socket = new Socket(Constants.HOST_IFA, Constants.PORT_COMMUNICATION_BETWEEN_IFA_MOSA);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            StandardPrints.printMsgEmph("MOSA connected in IFA ...");
        }catch(IOException ex){
            StandardPrints.printMsgWarning("Warning [IOException] connectClient()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        }
    }
    
    public void receiveData(){
        stateCommunication = StateCommunication.LISTENING;
        StandardPrints.printMsgEmph("listening to the connection with IFA...");
        Executors.newSingleThreadExecutor().execute(new Runnable(){            
            @Override
            public void run(){
                try{
                    while (true){
                        String answer = input.readLine();
                        if (answer != null){
                            StandardPrints.printMsgYellow("Data IFA: " + answer);
                            if (answer.contains(TypeMsgCommunication.IFA_MOSA_HOMELOCATION)){
                                String home = answer.substring(23);                               
                                String h[] = home.split(", ");
                                double lat = Double.parseDouble(h[0]);
                                double lng = Double.parseDouble(h[1]);
                                double alt = Double.parseDouble(h[2]);
                                drone.defineHomeLocation(lat, lng, alt);
                            } else if (answer.equals(TypeMsgCommunication.IFA_MOSA_START)){
                                startMission = true;
                                sendData(TypeMsgCommunication.MOSA_IFA_STARTED);
                            } else if (answer.equals(TypeMsgCommunication.IFA_MOSA_STOP)){
                                stopMission = true;
                                sendData(TypeMsgCommunication.MOSA_IFA_STOPPED);
                                Thread.sleep(100);
                                System.exit(0);
                            } 
                        }else{
                            Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
                        }                    
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
    
    public void sendData(String msg){
        output.println(msg);
    }
    
    public StateCommunication getStateCommunication() {
        return stateCommunication;
    }      

    public boolean isStartMission() {
        return startMission;
    }
    
    public boolean isStopMission() {
        return stopMission;
    }
    
    public void close(){
        try {
            output.close();
            input.close();            
            socket.close();
            stateCommunication = StateCommunication.DISABLED;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] close()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        }
    }

}
