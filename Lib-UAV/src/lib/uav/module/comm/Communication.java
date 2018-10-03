package lib.uav.module.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import lib.uav.struct.states.StateCommunication;

/**
 * The class models communication using socket.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public abstract class Communication {
    
    public Socket socket;
    public BufferedReader input;
    public PrintWriter output;
    public StateCommunication stateCommunication;
    
    /**
     * Treats the data to be received
     * @since version 4.0.0
     */
    public abstract void receiveData();
    
    /**
     * Method that sends the data
     * @param msg the message to be sent
     * @since version 4.0.0
     */
    public void sendData(String msg){
        if (output != null){
            output.println(msg);
        }
    }
    
    /**
     * Checks the status of the communication
     * @return {@code true} if connected {@code false} otherwise
     * @since version 4.0.0
     */
    public boolean isConnected() {
        if (input == null) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Close the communication
     * @since version 4.0.0
     */
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
