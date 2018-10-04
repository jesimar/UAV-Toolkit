package uav.gcs.communication;

import com.google.gson.Gson;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import lib.uav.module.comm.Client;
import lib.uav.module.comm.Communication;
import lib.uav.module.comm.Server;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.struct.constants.Constants;
import lib.uav.struct.constants.TypeMsgCommunication;
import lib.uav.struct.constants.TypePlanner;
import lib.uav.struct.mission.Mission;
import lib.uav.struct.states.StateCommunication;
import lib.uav.util.UtilRoute;
import uav.gcs.planner.AStar4m;
import uav.gcs.planner.CCQSP4m;
import uav.gcs.planner.HGA4m;
import uav.gcs.planner.Planner;
import uav.gcs.struct.Drone;

/**
 * The class controls communication with MOSA.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 * @see Communication
 * @see Server
 */
public class CommunicationMOSA extends Communication implements Client{

    private InputStream in;
    private final ReaderFileConfig config;
    private final Drone drone;
    private boolean isRunningPlanner;
    private JPanel panelCameraData;

    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @since version 4.0.0
     */
    public CommunicationMOSA(Drone drone, JPanel panelCameraData) {
        this.drone = drone;
        this.panelCameraData = panelCameraData;
        this.stateCommunication = StateCommunication.WAITING;
        this.config = ReaderFileConfig.getInstance();
        this.isRunningPlanner = false;
    }

    /**
     * Connect with the server
     * @since version 4.0.0
     */
    @Override
    public void connectServer() {
        System.out.println("UAV-GCS trying connect with MOSA ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        socket = new Socket(config.getHostMOSA(), config.getPortNetworkMOSAandGCS());
                        output = new PrintWriter(socket.getOutputStream(), true);
                        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        in = socket.getInputStream();
                        System.out.println("UAV-GCS connected in MOSA");
                        break;
                    } catch (IOException ex) {
                        stateCommunication = StateCommunication.DISABLED;
                    } catch (InterruptedException ex) {
                        stateCommunication = StateCommunication.DISABLED;
                    }
                }
            }
        });
    }
    
    int index = 0;

    /**
     * Treats the data to be received
     * @since version 2.0.0
     */
    @Override
    public void receiveData() {
        stateCommunication = StateCommunication.LISTENING;
        System.out.println("UAV-GCS trying to listen the connection of MOSA ...");
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (input != null) {
                            String answer = input.readLine();
                            if (answer != null) {
                                if (answer.contains(TypeMsgCommunication.MOSA_GCS_PLANNER)) {
                                    answer = answer.substring(18);
                                    long timeInit = System.currentTimeMillis();
                                    execPlannerInGCS(answer);
                                    long timeFinal = System.currentTimeMillis();
                                    long time = timeFinal - timeInit;
                                    System.out.println("Time in Planning (ms): " + time);
                                }
                            }
                        } 
                        if (in != null){
                            try{
                                byte[] sizeAr = new byte[4];
                                in.read(sizeAr);
                                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                                byte[] imageAr = new byte[size];
                                in.read(imageAr);
                                BufferedImage buffImage = ImageIO.read(new ByteArrayInputStream(imageAr));
                                ImageIcon image = new ImageIcon(buffImage);
                                JLabel labelImg = new JLabel(image);                              
                                int dim = 500;
                                int w = buffImage.getWidth();
                                int h = buffImage.getHeight();
                                System.out.println("w: " + w);
                                System.out.println("h: " + h);
                                int width = Math.min(w, dim);
                                int height = Math.min(h, (dim*h)/w);
                                System.out.println("width: " + width);
                                System.out.println("height: " + height);
                                labelImg.setIcon(new ImageIcon(image.getImage()
                                        .getScaledInstance(width, 
                                                height, Image.SCALE_DEFAULT)));                                
                                labelImg.setVisible(true);
                                panelCameraData.removeAll();
                                panelCameraData.add(labelImg);                            
                                ImageIO.write(buffImage, "jpg", new File("picture" + index +".jpg"));
                                System.out.println("image received!!!");
                                index++;
                            }catch (NegativeArraySizeException ex){
                                System.out.println("Warning [NegativeArraySizeException] try again");
                            } catch (Exception ex){
                                System.out.println("Warning [Exception] try again");
                            } 
                        }
                        Thread.sleep(Constants.TIME_TO_SLEEP_BETWEEN_MSG);
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Warning [InterruptedException] receiveData()");
                    ex.printStackTrace();
                    stateCommunication = StateCommunication.DISABLED;
                } catch (IOException ex) {
                    System.out.println("Warning [IOException] receiveData()");
                    ex.printStackTrace();
                    stateCommunication = StateCommunication.DISABLED;
                } 
            }
        });
    }
    
    public boolean isRunningPlanner() {
        return isRunningPlanner;
    }

    /**
     * Execute the path planner in GCS.
     * @param answer a string with the parameters to exec planner.
     * @since version 4.0.0
     */
    private void execPlannerInGCS(String answer) {
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
                    sendData(TypeMsgCommunication.UAV_ROUTE_FAILURE);
                    isRunningPlanner = false;
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
                boolean respFile = UtilRoute.readFileRouteMOSA(mission, path, nRoute, size);
                if (!respFile) {
                    sendData(TypeMsgCommunication.UAV_ROUTE_FAILURE);
                    isRunningPlanner = false;
                    return;
                }
                nRoute++;
            }
            mission.printMission();
            sendData(new Gson().toJson(mission));
        } else if (v[0].equals(TypePlanner.CCQSP4M)) {
            planner = new CCQSP4m(drone, v[1], v[2], v[3], v[4], v[5], v[6], v[7]);
            planner.clearLogs();
            long timeInit = System.currentTimeMillis();
            boolean respMission = ((CCQSP4m) (planner)).execMission();
            long timeFinal = System.currentTimeMillis();
            long time = timeFinal - timeInit;
            System.out.println("Time in Planning execMission (ms): " + time);
            if (!respMission) {
                sendData(TypeMsgCommunication.UAV_ROUTE_FAILURE);
                isRunningPlanner = false;
                return;
            }
            Mission mission = new Mission();
            String path = v[3] + "routeGeo.txt";
            
            if (config.hasRouteSimplifier()){
                UtilRoute.execRouteSimplifier(path, config.getDirRouteSimplifier(), 
                            config.getFactorRouteSimplifier(), ";");
                path = config.getDirRouteSimplifier() + "output-simplifier.txt";               
            }
            
            boolean respFile = UtilRoute.readFileRouteMOSA(mission, path);
            if (!respFile) {
                sendData(TypeMsgCommunication.UAV_ROUTE_FAILURE);
                isRunningPlanner = false;
                return;
            }
            mission.printMission();
            sendData(new Gson().toJson(mission));            
        } else if (v[0].equals(TypePlanner.A_STAR4M)) {
            planner = new AStar4m(drone, v[1], v[3], v[4], v[5], v[6], v[7]);
            planner.clearLogs();
            int size = Integer.parseInt(v[2]);
            boolean finish = false;
            int nRoute = 0;
            while (nRoute < size - 1 && !finish) {
                System.out.println("route: " + nRoute);
                boolean respMission = ((AStar4m) planner).execMission(nRoute);
                if (!respMission) {
                    sendData(TypeMsgCommunication.UAV_ROUTE_FAILURE);
                    isRunningPlanner = false;
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
                boolean respFile = UtilRoute.readFileRouteMOSA(mission, path, nRoute, size);
                if (!respFile) {
                    sendData(TypeMsgCommunication.UAV_ROUTE_FAILURE);
                    isRunningPlanner = false;
                    return;
                }
                nRoute++;
            }
            mission.printMission();
            sendData(new Gson().toJson(mission));
        } 
        isRunningPlanner = false;
    }

}
