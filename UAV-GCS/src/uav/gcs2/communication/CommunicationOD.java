package uav.gcs2.communication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import uav.gcs2.struct.Drone;

/**
 * @author Jesimar S. Arantes
 */
public class CommunicationOD {

    private Socket socket;
    private PrintWriter output;
    
    private final String HOST;
    private final int PORT;
    
    public CommunicationOD(String host, int port){
        this.HOST = host;
        this.PORT = port;
    }
    
    public void connectServer(){    
        System.out.println("Trying connect with OD");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                        socket = new Socket(HOST, PORT);
                        output = new PrintWriter(socket.getOutputStream(), true);
                        System.out.println("UAV GCS connected in OD ...");
                        break;
                    }catch(IOException ex){
                        
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }   
    
    public void sendInfoDrone(Drone drone, CommunicationIFA communicationIFA){
        System.out.println("Trying send info to OD");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        if (output != null && communicationIFA.isConnected()){
                            output.println(drone.toString());
                            Thread.sleep(500);
                        }
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }

    public void sendData(String msg){
        output.println(msg);
    }
    
    public boolean isConnected(){
        if (output == null){
            return false;
        }else{
            return true;
        }
    }
    
    public void close(){
        try {
            output.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Warning [IOException] close()");
            ex.printStackTrace();
        }
    }
}
