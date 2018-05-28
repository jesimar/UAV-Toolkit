package uav.gcs.communication;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import uav.gcs.planner.HGA4m;
import uav.gcs.planner.Planner;
import uav.gcs.struct.Drone;
import uav.generic.struct.Waypoint;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.constants.TypeWaypoint;
import uav.generic.struct.mission.Mission;
import uav.generic.util.UtilString;

/**
 * @author Jesimar S. Arantes
 */
public class CommunicationMOSA {

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;    

    private final Drone drone;
    private final String HOST;
    private final int PORT;
    
    private boolean isRunningPlanner;

    public CommunicationMOSA(Drone drone, String host, int port) {
        this.drone = drone;
        this.HOST = host;
        this.PORT = port;
        this.isRunningPlanner = false;
    }

    public void connectServerMOSA() {
        System.out.println("Trying connect with MOSA");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        socket = new Socket(HOST, PORT);
                        output = new PrintWriter(socket.getOutputStream(), true);
                        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        System.out.println("UAV GCS connected in MOSA ...");
                        break;
                    } catch (IOException ex) {

                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }

    public void receiveData() {
        System.out.println("Trying to listen the connection of UAV-MOSA ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (input != null) {
                            String answer = input.readLine();
                            if (answer != null) {
                                if (answer.contains(TypeMsgCommunication.IFA_GCS_PLANER)) {
                                    answer = answer.substring(17);
                                    plannerInGCS(answer);
                                }
                            }
                            Thread.sleep(100);
                        } else {
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

    public void sendData(String msg) {
        output.println(msg);
    }

    public boolean isConnected() {
        if (input == null) {
            return false;
        } else {
            return true;
        }
    }

    public void close() {
        try {
            output.close();
            input.close();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Warning [IOException] close()");
            ex.printStackTrace();
        }
    }
    
    private void plannerInGCS(String answer) {
        isRunningPlanner = true;
        String v[] = answer.split(";");
        Planner planner = new HGA4m(drone, v[0], v[1], v[2], v[3], v[4], v[5],
                v[6], v[7], v[8], v[9], v[10], v[11], v[12], v[13]);
        planner.clearLogs();
        int size = Integer.parseInt(v[1]);
        boolean finish = false;
        int nRoute = 0;
        while (nRoute < size - 1 && !finish){
            System.out.println("route: " + nRoute);
            boolean respMission = planner.execMission(nRoute);
            if (!respMission){
                sendData("failure");
                return;
            }
            nRoute++;
            if (nRoute == size - 1){
                finish = true;
            }
        }
        
        Mission mission = new Mission();
        nRoute = 0;
        while (nRoute < size - 1){
            String path = v[4] + "routeGeo" + nRoute + ".txt";                
            boolean respFile = readFileRoute(mission, path, nRoute, size);
            if (!respFile){
                sendData("failure");
                return;
            }
            nRoute++;
        }
        mission.printMission();
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        sendData(jsonMission);
        isRunningPlanner = false;
    }
    
    private boolean readFileRoute(Mission wps, String path, int nRoute, int size) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            boolean firstTime = true;
            double lat = 0.0;
            double lng = 0.0;
            double alt = 0.0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);
                if (firstTime && (nRoute == 0 || nRoute == -2)){
                    wps.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, alt));
                    firstTime = false;
                }
                wps.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
            }
            if (wps.getMission().size() > 0){
                if (nRoute == size - 2){
                    wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException]: readFileRoute()");
            return false;
        } catch (IOException ex) {
            System.out.println("Warning [IOException]: readFileRoute()");
            return false;
        }
    }

    public boolean isRunningPlanner() {
        return isRunningPlanner;
    }
    
}
