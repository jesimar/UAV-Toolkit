package uav.od;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Classe que faz o controle da comunicação com GCS.
 * @author Jesimar S. Arantes
 */
public class CommunicationGCS {
    
    private final int PORT = 5557;
    private final Drone drone;
    
    private ServerSocket server;
    private Socket socket;
    private BufferedReader input;
    
    /**
     * Class contructor.
     * @param drone instance of the drone
     */
    public CommunicationGCS(Drone drone) {
        this.drone = drone;
    }

    //aqui talvez tenha que colocar em uma thread para disvincular a app da fernanda.
    public void startServerOD() {
        System.out.println("waiting a connection to UAV-GCS ...");
        try {
            server = new ServerSocket(PORT);
            socket = server.accept();//wait the connection
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("OD connected in GCS ...");
        } catch (IOException ex) {
            System.out.println("Warning [IOException] startServerGCS()");
            ex.printStackTrace();
        }
    }

    public void receiveData() {
        System.out.println("listening to the connection of UAV-GCS ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Data: " + drone.title());
                    while (true) {
                        if (input != null){
                            String answer = input.readLine();
                            if (answer != null) {
                                String v[] = answer.split(";");
                                drone.date = v[0];
                                drone.hour = v[1];
                                drone.time = Double.parseDouble(v[2]);
                                drone.lat = Double.parseDouble(v[3]);
                                drone.lng = Double.parseDouble(v[4]);
                                drone.alt_rel = Double.parseDouble(v[5]);
                                drone.alt_abs = Double.parseDouble(v[6]);
                                drone.voltage = Double.parseDouble(v[7]);
                                drone.current = Double.parseDouble(v[8]);
                                drone.level = Double.parseDouble(v[9]);
                                drone.pitch = Double.parseDouble(v[10]);
                                drone.yaw = Double.parseDouble(v[11]);
                                drone.roll = Double.parseDouble(v[12]);
                                drone.vx = Double.parseDouble(v[13]);
                                drone.vy = Double.parseDouble(v[14]);
                                drone.vz = Double.parseDouble(v[15]);
                                drone.fixType = Integer.parseInt(v[16]);
                                drone.satellitesVisible = Integer.parseInt(v[17]);
                                drone.eph = Integer.parseInt(v[18]);
                                drone.epv = Integer.parseInt(v[19]);
                                drone.heading = Double.parseDouble(v[20]);
                                drone.groundspeed = Double.parseDouble(v[21]);
                                drone.airspeed = Double.parseDouble(v[22]);
                                drone.nextWaypoint = Integer.parseInt(v[23]);
                                drone.countWaypoint = Integer.parseInt(v[24]);
                                drone.distToHome = Double.parseDouble(v[25]);
                                drone.distToCurrentWpt = Double.parseDouble(v[26]);
                                drone.mode = v[27];
                                drone.systemStatus = v[28];
                                drone.armed = Boolean.parseBoolean(v[29]);
                                drone.isArmable = Boolean.parseBoolean(v[30]);
                                drone.ekfOk = Boolean.parseBoolean(v[31]);
                                drone.typeFailure = v[32];
                                System.out.println("Data: " + drone.toString());
                            } else {
                                Thread.sleep(500);
                            }
                        }else{
                            Thread.sleep(1000);
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
    
    public boolean isConnected(){
        if (input == null){
            return false;
        }else{
            return true;
        }
    }

    public void close() {
        try {
            input.close();
            socket.close();
            server.close();
        } catch (IOException ex) {
            System.out.println("Warning [IOException] close()");
            ex.printStackTrace();
        }
    }
}
