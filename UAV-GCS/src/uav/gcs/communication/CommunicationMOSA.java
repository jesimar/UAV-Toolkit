package uav.gcs.communication;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Executors;
import uav.gcs.planner.CCQSP4m;
import uav.gcs.planner.HGA4m;
import uav.gcs.planner.Planner;
import uav.gcs.struct.Drone;
import uav.generic.struct.constants.TypeMsgCommunication;
import uav.generic.struct.constants.TypePlanner;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.reader.ReaderFileConfig;
import uav.generic.struct.reader.UtilRoute;

/**
 * @author Jesimar S. Arantes
 */
public class CommunicationMOSA extends Communication{

    private final ReaderFileConfig config;
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    private final Drone drone;
    private final String HOST;
    private final int PORT;

    private boolean isRunningPlanner;

    public CommunicationMOSA(Drone drone, String host, int port) {
        config = ReaderFileConfig.getInstance();
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

    @Override
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
                                if (answer.contains(TypeMsgCommunication.IFA_GCS_PLANNER)) {
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

    @Override
    public void sendData(String msg) {
        output.println(msg);
    }

    @Override
    public boolean isConnected() {
        if (input == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
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
        Planner planner = null;
        if (v[0].equals(TypePlanner.HGA4M)) {
            planner = new HGA4m(drone, v[1], v[2], v[3], v[4], v[5],
                    v[6], v[7], v[8], v[9], v[10], v[11], v[12], v[13]);
            planner.clearLogs();
            int size = Integer.parseInt(v[2]);
            boolean finish = false;
            int nRoute = 0;
            while (nRoute < size - 1 && !finish) {
                System.out.println("route: " + nRoute);
                boolean respMission = ((HGA4m) planner).execMission(nRoute);
                if (!respMission) {
                    sendData("failure");
                    return;
                }
                nRoute++;
                if (nRoute == size - 1) {
                    finish = true;
                }
            }
            Mission mission = new Mission();
            nRoute = 0;
            while (nRoute < size - 1) {
                String path = v[5] + "routeGeo" + nRoute + ".txt";
                if (config.hasRouteSimplifier()){
                    UtilRoute.execRouteSimplifier(path, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                    path = config.getDirRouteSimplifier() + "output-simplifier.txt";               
                }
                boolean respFile = UtilRoute.readFileRoute(mission, path, nRoute, size);
                if (!respFile) {
                    sendData("failure");
                    return;
                }
                nRoute++;
            }
            mission.printMission();
            sendData(new Gson().toJson(mission));
        } else if (v[0].equals(TypePlanner.CCQSP4M)) {
            planner = new CCQSP4m(drone, v[1], v[2], v[3], v[4], v[5], v[6], v[7]);
            planner.clearLogs();
            boolean respMission = ((CCQSP4m) (planner)).execMission();
            if (!respMission) {
                sendData("failure");
                return;
            }
            Mission mission = new Mission();
            String path = v[3] + "routeGeo.txt";
            
            if (config.hasRouteSimplifier()){
                UtilRoute.execRouteSimplifier(path, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                path = config.getDirRouteSimplifier() + "output-simplifier.txt";               
            }
            
            boolean respFile = UtilRoute.readFileRoute(mission, path);
            if (!respFile) {
                sendData("failure");
                return;
            }
            mission.printMission();
            sendData(new Gson().toJson(mission));            
        }
        isRunningPlanner = false;
    }

    public boolean isRunningPlanner() {
        return isRunningPlanner;
    }

}
