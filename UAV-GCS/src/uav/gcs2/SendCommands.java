package uav.gcs2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * @author Jesimar S. Arantes
 */
public class SendCommands {

    private Socket socket;
    private PrintWriter output; 
    
    private final String HOST;
    private final int PORT;
    
    public SendCommands(String host, int port){
        this.HOST = host;
        this.PORT = port;
    }
    
    public void connectClient(){    
        System.out.print("Trying connect with IFA");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                        socket = new Socket(HOST, PORT);
                        output = new PrintWriter(socket.getOutputStream(), true);
                        System.out.println("\nUAV GCS connected in IFA ...");
                        break;
                    }catch(IOException ex){
                        
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
