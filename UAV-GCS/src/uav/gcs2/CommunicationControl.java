package uav.gcs2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * @author Jesimar S. Arantes
 */
public class CommunicationControl {

    private Socket socket;
    private PrintWriter output; 
    private BufferedReader input;
    
    private final String HOST;
    private final int PORT;
    
    public CommunicationControl(String host, int port){
        this.HOST = host;
        this.PORT = port;
    }
    
    public void connectClient(){    
        System.out.println("Trying connect with IFA");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                        socket = new Socket(HOST, PORT);
                        output = new PrintWriter(socket.getOutputStream(), true);
                        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        System.out.println("UAV GCS connected in IFA ...");
                        break;
                    }catch(IOException ex){
                        
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }
    
    public String latlng = "0,0";
    
    public void receiveData() {
        System.out.println("Trying to listen the connection of UAV-IFA ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (input != null){
                            String answer = input.readLine();                            
                            if (answer != null) {
                                System.out.println("Data : " + answer);
                                latlng = answer;
                            } 
                            Thread.sleep(100);
                        }else{
                            Thread.sleep(500);
                        }
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Warning [InterruptedException] receiveData()");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    System.out.println("Warning [IOException] receiveData()");
                    ex.printStackTrace();
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
            input.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Warning [IOException] close()");
            ex.printStackTrace();
        }
    }
}
