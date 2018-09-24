package uav.gcs.communication;

/**
 *
 * @author Jesimar S. Arantes
 */
public abstract class Communication {
    
    public abstract void receiveData();
    
    public abstract void sendData(String msg);
    
    public abstract boolean isConnected();
    
    public abstract void close();
    
}
