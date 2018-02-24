package uav.insert.failure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author jesimar
 */
public class UAVInsertFailure {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Properties prop = new Properties();
            InputStream input = new FileInputStream("./config.properties");
            prop.load(input);

            UAVInsertFailure uavIF = new UAVInsertFailure(
                    prop.getProperty("prop.host"), 
                    Integer.parseInt(prop.getProperty("prop.port")));
            uavIF.connectClient();
            System.out.println("\nType of Actions for Failures:");
            System.out.println("\tMPGA");
            System.out.println("\tLAND");
            System.out.println("\tRTL");
            System.out.print("Read: ");
            Scanner sc = new Scanner(System.in);
            while (true){
                String str = sc.next();
                uavIF.sendData(str);
                if (str.equals("MPGA") || str.equals("LAND") || str.equals("RTL")){
                    break;
                }
            }
        } catch (FileNotFoundException ex){     
            System.out.println("Error [FileNotFoundException] ReaderLoadConfig()");
            ex.printStackTrace();
            System.exit(0);
        } catch (IOException ex) {
            System.out.println("Error [IOException] ReaderLoadConfig()");
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    private Socket socket;
    private PrintWriter output; 
    
    private final String HOST;
    private final int PORT;
    
    public UAVInsertFailure(String host, int port){
        this.HOST = host;
        this.PORT = port;
    }
    
    public void connectClient(){        
        try{
            System.out.println("connecting in the IFA ...");
            socket = new Socket(HOST, PORT);
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("UAV Insert Failure connected in IFA ...");
        }catch(IOException ex){
            System.out.println("Warning [IOException] connectClient()");
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public void sendData(String msg){
        output.println(msg);
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
