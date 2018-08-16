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
import uav.gcs.replanner.DE4s;
import uav.gcs.replanner.GA4s;
import uav.gcs.replanner.GH4s;
import uav.gcs.replanner.MPGA4s;
import uav.gcs.replanner.MS4s;
import uav.gcs.replanner.Replanner;
import uav.gcs.struct.Drone;
import uav.generic.struct.Waypoint;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.constants.TypeReplanner;
import uav.generic.struct.constants.TypeWaypoint;
import uav.generic.struct.mission.Mission;
import uav.generic.util.UtilString;

/**
 * @author Jesimar S. Arantes
 */
public class CommunicationIFA {

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;    

    private final Drone drone;
    private final String HOST;
    private final int PORT;
    
    private boolean isRunningReplanner;

    public CommunicationIFA(Drone drone, String host, int port) {
        this.drone = drone;
        this.HOST = host;
        this.PORT = port;
        this.isRunningReplanner = false;
    }

    public void connectServerIFA() {
        System.out.println("Trying connect with IFA");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        socket = new Socket(HOST, PORT);
                        output = new PrintWriter(socket.getOutputStream(), true);
                        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        System.out.println("UAV GCS connected in IFA ...");
                        break;
                    } catch (IOException ex) {

                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }

    public void receiveData() {
        System.out.println("Trying to listen the connection of UAV-IFA ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (input != null) {
                            String answer = input.readLine();
                            if (answer != null) {
                                if (answer.contains(TypeMsgCommunication.IFA_GCS_INFO)) {
                                    answer = answer.substring(14);
                                    readInfoIFA(answer);
                                } else if (answer.contains(TypeMsgCommunication.IFA_GCS_REPLANNER)) {
                                    answer = answer.substring(19);
                                    replannerInGCS(answer);
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

    private void readInfoIFA(String answer) {
        String v[] = answer.split(";");
        drone.date = v[0];
        drone.hour = v[1];
        drone.time = Double.parseDouble(v[2]);
        drone.gps.lat = Double.parseDouble(v[3]);
        drone.gps.lng = Double.parseDouble(v[4]);
        drone.barometer.alt_rel = Double.parseDouble(v[5]);
        drone.barometer.alt_abs = Double.parseDouble(v[6]);
        drone.battery.voltage = Double.parseDouble(v[7]);
        drone.battery.current = Double.parseDouble(v[8]);
        drone.battery.level = Double.parseDouble(v[9]);
        drone.attitude.pitch = Double.parseDouble(v[10]);
        drone.attitude.yaw = Double.parseDouble(v[11]);
        drone.attitude.roll = Double.parseDouble(v[12]);
        drone.velocity.vx = Double.parseDouble(v[13]);
        drone.velocity.vy = Double.parseDouble(v[14]);
        drone.velocity.vz = Double.parseDouble(v[15]);
        drone.gpsinfo.fixType = Integer.parseInt(v[16]);
        drone.gpsinfo.satellitesVisible = Integer.parseInt(v[17]);
        drone.gpsinfo.eph = Integer.parseInt(v[18]);
        drone.gpsinfo.epv = Integer.parseInt(v[19]);
        drone.sensorUAV.heading = Double.parseDouble(v[20]);
        drone.sensorUAV.groundspeed = Double.parseDouble(v[21]);
        drone.sensorUAV.airspeed = Double.parseDouble(v[22]);
        drone.nextWaypoint = Integer.parseInt(v[23]);
        drone.countWaypoint = Integer.parseInt(v[24]);
        drone.distanceToHome = Double.parseDouble(v[25]);
        drone.distanceToCurrentWaypoint = Double.parseDouble(v[26]);
        drone.statusUAV.mode = v[27];
        drone.statusUAV.systemStatus = v[28];
        drone.statusUAV.armed = Boolean.parseBoolean(v[29]);
        drone.statusUAV.isArmable = Boolean.parseBoolean(v[30]);
        drone.statusUAV.ekfOk = Boolean.parseBoolean(v[31]);
        drone.typeFailure = v[32];
        drone.sonar.distance = v[33].equals("NONE") ? -1.0 : Double.parseDouble(v[33]);
        drone.temperature.temperature = v[34].equals("NONE") ? -1.0 : Double.parseDouble(v[34]);
    }

    private void replannerInGCS(String answer) {
        isRunningReplanner = true;
        String v[] = answer.split(";");
        Replanner replanner = null;
        
        if (v[0].equals(TypeReplanner.GH4S)) {
            replanner =   new GH4s(drone, v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8]);
        } else if (v[0].equals(TypeReplanner.GA4S)) {
            replanner =   new GA4s(drone, v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8]);
        } else if (v[0].equals(TypeReplanner.MPGA4S)) {
            replanner = new MPGA4s(drone, v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8]);
        } else if (v[0].equals(TypeReplanner.MS4S)) {
            replanner =   new MS4s(drone, v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8]);
        } else if (v[0].equals(TypeReplanner.DE4S)) {
            replanner =   new DE4s(drone, v[1], v[2], v[3], v[4], v[5], v[6], v[7], v[8]);
        }else{
            System.out.println("Error: " + v[0]);
            System.out.println("Error: " + answer);
        }
        
        replanner.clearLogs();
        boolean itIsOkExec = replanner.exec();
        if (!itIsOkExec) {
            sendData("failure");
            return;
        } 
        Mission mission = new Mission();
        String path = v[3] + "routeGeo.txt";
        boolean resp = readFileRoute(mission, path, 0);
        if (!resp) {
            sendData("failure");
            return;
        }
        mission.printMission();
        Gson gson = new Gson();
        String jsonMission = gson.toJson(mission);
        sendData(jsonMission);
        isRunningReplanner = false;
    }
    
    private boolean readFileRoute(Mission wps, String path, int time) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            double lat = 0.0;
            double lng = 0.0;
            double alt = 0.0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);
                if (time > 1) {
                    wps.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
                }
                time++;
            }
            if (wps.getMission().size() > 0) {
                wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
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

    public boolean isRunningReplanner() {
        return isRunningReplanner;
    }
        
}
