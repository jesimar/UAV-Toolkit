package uav.generic.module.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import uav.generic.struct.states.StateCommunication;

/**
 *
 * @author Jesimar S. Arantes
 */
public abstract class Communication {
    
    public Socket socket;
    public BufferedReader input;
    public PrintWriter output;
    public StateCommunication stateCommunication;
    
    public abstract void receiveData();
    
    public void sendData(String msg){
        if (output != null){
            output.println(msg);
        }
    }
    
    public boolean isConnected() {
        if (input == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public void close(){
        try {
            output.close();
            input.close();            
            socket.close();
            stateCommunication = StateCommunication.DISABLED;
        } catch (IOException ex) {
            System.err.println("Error [IOException] close()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        } catch (Exception ex) {
            System.err.println("Error [Exception] close()");
            ex.printStackTrace();
            stateCommunication = StateCommunication.DISABLED;
        }
    }  
    
    public StateCommunication getStateCommunication() {
        return stateCommunication;
    } 
    
}
