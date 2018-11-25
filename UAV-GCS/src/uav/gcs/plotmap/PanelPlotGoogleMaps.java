package uav.gcs.plotmap;

import uav.gcs.struct.ReaderMap;
import uav.gcs.struct.ReaderRoute;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.Executors;
import javafx.embed.swing.JFXPanel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.reader.ReaderFileMission;
import lib.uav.struct.constants.TypePlanner;
import lib.uav.struct.constants.TypeReplanner;
import lib.uav.struct.constants.TypeSystemExecIFA;
import lib.uav.struct.constants.TypeSystemExecMOSA;
import lib.uav.struct.mission.Mission;
import lib.uav.util.UtilGeo;
import marte.swing.google.maps.GoogleMapsScene;
import uav.gcs.GCS;
import uav.gcs.struct.Drone;

/**
 * The class that plots the map and routes using the Google Maps API.
 * @author Jesimar Arantes
 * @since version 3.0.0
 */
public class PanelPlotGoogleMaps extends JPanel {

    private GoogleMapsScene api;
    private final String file = "./html/maps.html";
    private final ReaderFileConfig config;
    private final ReaderRoute routeIFA;
    private final ReaderRoute routeMOSA;
    private final ReaderRoute routeMOSA2;
    private final ReaderRoute routeMOSASimplifier;
    private final ReaderRoute routeMOSABehavior;
    private final ReaderRoute routeMOSABehaviorFile;
    private ReaderMap map;
    private final double lngBase = GCS.pointGeo.getLng();
    private final double latBase = GCS.pointGeo.getLat();
    private boolean firstTime = true;
    
    private Mission wptsMission = null;
    private Mission wptsBuzzer = null;
    private Mission wptsCameraPicture = null;
    private Mission wptsCameraVideo = null;
    private Mission wptsCameraPhotoInSeq = null;
    private Mission wptsSpraying = null;

    /**
     * Class constructor
     * @since version 3.0.0
     */
    public PanelPlotGoogleMaps() {
        config = ReaderFileConfig.getInstance();
        routeIFA = new ReaderRoute();
        routeMOSA = new ReaderRoute();
        routeMOSA2 = new ReaderRoute();
        routeMOSASimplifier = new ReaderRoute();
        routeMOSABehavior = new ReaderRoute();
        routeMOSABehaviorFile = new ReaderRoute();
    }

    /**
     * Initializes Google Maps API.
     * @param width width value/dimension in x axes
     * @param height height value/dimension in y axes
     * @since version 4.0.0
     */
    public void init(int width, int height) {
        try {
            api = GoogleMapsScene.launch(new File(file), (String) null);
            final JFXPanel fxPanel = new JFXPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                }
            };
            api.attach(fxPanel);
            this.setLayout(new FlowLayout());
            this.add(fxPanel);
            this.setSize(width, height);
            fxPanel.setPreferredSize(new Dimension(this.getWidth() - 20, this.getHeight() - 10));
            PanelPlotGoogleMaps panel = this;
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    fxPanel.setPreferredSize(new Dimension(panel.getWidth() - 20, panel.getHeight() - 10));
                    SwingUtilities.updateComponentTreeUI(panel);
                }
            });
            this.setVisible(true);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Paint in screen the info about map.
     * @since version 3.0.0
     */
    public void drawMap() {
        String pathMap = config.getDirFiles() + "map-full.sgl";
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)){
            map = new ReaderMap(pathMap);
            map.read();
            for (int i = 0; i < map.getSizeNFZ(); i++) {
                double vetX[] = map.getVetX_NFZ(i);
                double vetY[] = map.getVetY_NFZ(i);
                Point2D points[] = new Point2D[vetX.length];
                for (int j = 0; j < vetX.length; j++) {
                    points[j] = new Point2D.Double(
                            UtilGeo.convertYtoLatitude(latBase, vetY[j]),
                            UtilGeo.convertXtoLongitude(lngBase, latBase, vetX[j])
                    );
                }
                api.addPolygon("#000000", 0.8, 2, "#FFFFFF", 0.35, points);
            }
            for (int i = 0; i < map.getSizePenalty(); i++) {
                double vetX[] = map.getVetX_Penalty(i);
                double vetY[] = map.getVetY_Penalty(i);
                Point2D points[] = new Point2D[vetX.length];
                for (int j = 0; j < vetX.length; j++) {
                    points[j] = new Point2D.Double(
                            UtilGeo.convertYtoLatitude(latBase, vetY[j]),
                            UtilGeo.convertXtoLongitude(lngBase, latBase, vetX[j])
                    );
                }
                api.addPolygon("#E47833", 0.8, 2, "#E47833", 0.35, points);
            }
            for (int i = 0; i < map.getSizeBonus(); i++) {
                double vetX[] = map.getVetX_Bonus(i);
                double vetY[] = map.getVetY_Bonus(i);
                Point2D points[] = new Point2D[vetX.length];
                for (int j = 0; j < vetX.length; j++) {
                    points[j] = new Point2D.Double(
                            UtilGeo.convertYtoLatitude(latBase, vetY[j]),
                            UtilGeo.convertXtoLongitude(lngBase, latBase, vetX[j])
                    );
                }
                api.addPolygon("#0000FF", 0.8, 2, "#0000FF", 0.35, points);
            }
            
            if (config.getTypePlanner().equals(TypePlanner.M_ADAPTIVE4M)) {
                for (int i = 0; i < 2; i++) {
                    Mission wptsScenicRegion = new Mission();
                    try {
                        ReaderFileMission.mapScenicRegion(
                                new File("../Missions/Thesis/Scenery04/map-scenic-region.txt"),
                                wptsScenicRegion,
                                "Map Scenic Region" + (i+1));
                    } catch (FileNotFoundException ex) {
                        
                    }
                    Point2D points[] = new Point2D[4];
                    for (int j = 0; j < points.length; j++) {
                        points[j] = new Point2D.Double(
                                wptsScenicRegion.getWaypoint(j).getLat(),
                                wptsScenicRegion.getWaypoint(j).getLng()
                        );
                    }
                    api.addPolygon("#FFFF00", 0.8, 2, "#FFFF00", 0.35, points);
                }
            }
        }
    }

    /**
     * The method wait until the routes are ready and then plots them on the screen.
     * @since version 3.0.0
     */
    public void waitingForRoutes() {
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)
                && config.getTypePlanner().equals(TypePlanner.HGA4M)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirPlanner() + "route3D" + i + ".txt";
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                Thread.sleep(100);
                                routeMOSA.readHGA(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00", 0.8, 2);
                                i++;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)
                && config.getTypePlanner().equals(TypePlanner.CCQSP4M)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirPlanner() + "route3D.txt";
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                Thread.sleep(100);
                                routeMOSA.read(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00", 0.8, 2);
                                break;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)
                && config.getTypePlanner().equals(TypePlanner.G_PATH_PLANNER4M)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirPlanner() + "route3D.txt";
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                Thread.sleep(100);
                                routeMOSA.read(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00", 0.8, 2);
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)
                && config.getTypePlanner().equals(TypePlanner.A_STAR4M)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirPlanner() + "route3D" + i + ".txt";
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                Thread.sleep(100);
                                routeMOSA.readAStar(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00", 0.8, 2);
                                i++;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.FIXED_ROUTE)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirFixedRouteMOSA() + config.getFileFixedRouteMOSA();
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                Thread.sleep(100);
                                routeMOSA.readGeo(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00", 0.8, 2);
                                repaint();
                                break;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }

            });
        }
        if (config.getSystemExecIFA().equals(TypeSystemExecIFA.REPLANNER) &&
                !config.getTypeReplanner().equals(TypeReplanner.G_PATH_REPLANNER4s)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteIFA = config.getDirReplanner() + "route.txt";
                            File fileIFA = new File(pathRouteIFA);
                            if (fileIFA.exists()) {
                                Thread.sleep(100);
                                routeIFA.read(fileIFA);
                                addRouteInAPIxy(routeIFA, "#FF0000", 0.8, 3);
                                break;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        if (config.getSystemExecIFA().equals(TypeSystemExecIFA.REPLANNER) &&
                config.getTypeReplanner().equals(TypeReplanner.G_PATH_REPLANNER4s)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteIFA = config.getDirReplanner() + "output.txt";
                            File fileIFA = new File(pathRouteIFA);
                            if (fileIFA.exists()) {
                                Thread.sleep(100);
                                routeIFA.read(fileIFA);
                                addRouteInAPIxy(routeIFA, "#FF0000", 0.8, 3);
                                break;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        if (config.hasRouteSimplifier()) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathSimplifier = config.getDirRouteSimplifier() + "output-simplifier.txt";
                            File fileSimplifier = new File(pathSimplifier);
                            if (fileSimplifier.exists()) {
                                Thread.sleep(100);
                                routeMOSASimplifier.read(fileSimplifier);
                                addRouteInAPIlatlng(routeMOSASimplifier, "#000000", 0.8, 2);
                                break;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER) && 
                config.getTypePlanner().equals(TypePlanner.M_ADAPTIVE4M)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirMissionPlannerMAdaptive4m()+ 
                                    config.getFileMissionPlannerMAdaptive4m();
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                Thread.sleep(100);
                                routeMOSABehaviorFile.readGeo(fileMOSA);
                                addRouteInAPIxy(routeMOSABehaviorFile, "#00FFFF", 0.8, 2);
                                addLineInAPIlatlng(
                                        -22.0020583440073, -47.9328060159805,
                                        -22.0020337728612, -47.9325215091913,
                                        "#00FF00", 0.8, 2);
                                break;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        String pathBehavior = config.getDirBehavior()+ "route-behavior.txt";
                        File fileBehavior = new File(pathBehavior);
                        if (fileBehavior.exists()) {
                            Thread.sleep(100);
                            routeMOSABehavior.readGeo(fileBehavior);
                            addRouteInAPIxy(routeMOSABehavior, "#FF00FF", 0.8, 2);
                            break;
                        }
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
        
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)
                && config.getTypePlanner().equals(TypePlanner.M_ADAPTIVE4M)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirPlanner() + "route3D" + i + ".txt";
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                Thread.sleep(100);
                                if (i == 0){
                                    routeMOSA.readHGA(fileMOSA);
                                    addRouteInAPIxy(routeMOSA, "#00FF00", 0.8, 2);
                                }else if (i == 3){
                                    routeMOSA2.readHGA(fileMOSA);
                                    addRouteInAPIxy(routeMOSA2, "#00FF00", 0.8, 2);
                                }
                                i = i + 3;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
    }

    /**
     * Plot the drone in real time
     * @param drone the instance of aircraft
     * @since version 3.0.0
     */
    public void plotDroneInRealTime(Drone drone) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        double latDrone = drone.gps.lat;
                        double lngDrone = drone.gps.lng;
                        if (latDrone != 0.0 && lngDrone != 0) {
                            if (firstTime) {
                                api.setCenter(latDrone, lngDrone);
                                firstTime = false;
                            }
                            api.delMarker("UAV");
                            api.addMarker(latDrone, lngDrone, "UAV", "uav");
                            repaint();
                        }
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }
    
    /**
     * Paint in screen the info about markers.
     * @since version 4.0.0
     */
    public void drawMarkers() {
        if (wptsMission != null){
            addPolyRect(wptsMission, "#808080", 1);
        }
        if (wptsBuzzer != null){
            addPolyRect(wptsBuzzer, "#FF0000", 0.5);
        }
        if (wptsCameraPicture != null){
            addPolyRect(wptsCameraPicture, "#FFC800", 0.5);
        }
        if (wptsCameraPhotoInSeq != null){
            addPolyRect(wptsCameraPhotoInSeq, "#FFC800", 0.5);
        }
        if (wptsCameraVideo != null){
            addPolyRect(wptsCameraVideo, "#FFC800", 0.5);
        }
        if (wptsSpraying != null){
            addPolyRect(wptsSpraying, "#FF00FF", 0.5);
        }
    }
    
    private void addRouteInAPIxy(ReaderRoute route, String color, 
            double strokeOpacity, double strokeWeight){
        for (int i = 0; i < route.getRoute3D().size(); i++) {
            if (i + 1 < route.getRoute3D().size()) {
                Point2D points[] = new Point2D[2];
                points[0] = new Point2D.Double(
                        UtilGeo.convertYtoLatitude(latBase, route.getRoute3D().getPosition(i).getY()),
                        UtilGeo.convertXtoLongitude(lngBase, latBase, route.getRoute3D().getPosition(i).getX())
                );
                points[1] = new Point2D.Double(
                        UtilGeo.convertYtoLatitude(latBase, route.getRoute3D().getPosition(i + 1).getY()),
                        UtilGeo.convertXtoLongitude(lngBase, latBase, route.getRoute3D().getPosition(i + 1).getX())
                );
                api.addLine(color, strokeOpacity, strokeWeight, points);
            }
        }
    }
    
    private void addRouteInAPIlatlng(ReaderRoute route, String color, 
            double strokeOpacity, double strokeWeight){
        for (int i = 0; i < route.getRoute3D().size(); i++) {
            if (i + 1 < route.getRoute3D().size()) {
                Point2D points[] = new Point2D[2];
                points[0] = new Point2D.Double(
                        route.getRoute3D().getPosition(i).getX(),
                        route.getRoute3D().getPosition(i).getY()
                );
                points[1] = new Point2D.Double(
                        route.getRoute3D().getPosition(i + 1).getX(),
                        route.getRoute3D().getPosition(i + 1).getY()
                );
                api.addLine(color, strokeOpacity, strokeWeight, points);
            }
        }
    }
    
    private void addLineInAPIlatlng(double lat1, double lng1, double lat2, double lng2, 
            String color, double strokeOpacity, double strokeWeight){
            Point2D points[] = new Point2D[2];
            points[0] = new Point2D.Double(lat1, lng1);
            points[1] = new Point2D.Double(lat2, lng2);
            api.addLine(color, strokeOpacity, strokeWeight, points);
    }
    
    private void addPolyRect(Mission wpts, String color, double factor){
        for (int i = 0; i < wpts.size(); i++){
            Point2D points[] = new Point2D[4];
            points[0] = new Point2D.Double(
                    wpts.getWaypoint(i).getLat()-0.000009*factor,
                    wpts.getWaypoint(i).getLng()-0.000009*factor
            );
            points[1] = new Point2D.Double(
                    wpts.getWaypoint(i).getLat()+0.000009*factor,
                    wpts.getWaypoint(i).getLng()-0.000009*factor
            );
            points[2] = new Point2D.Double(
                    wpts.getWaypoint(i).getLat()+0.000009*factor,
                    wpts.getWaypoint(i).getLng()+0.000009*factor
            );
            points[3] = new Point2D.Double(
                    wpts.getWaypoint(i).getLat()-0.000009*factor,
                    wpts.getWaypoint(i).getLng()+0.000009*factor
            );
            api.addPolygon(color, 0.8, 2, color, 0.35, points);
        }
    }
    
    public void setWptsMission(Mission wptsMission) {
        this.wptsMission = wptsMission;
    }       

    public void setWptsBuzzer(Mission wptsBuzzer) {
        this.wptsBuzzer = wptsBuzzer;
    }

    public void setWptsCameraPicture(Mission wptsCameraPicture) {
        this.wptsCameraPicture = wptsCameraPicture;
    }

    public void setWptsCameraVideo(Mission wptsCameraVideo) {
        this.wptsCameraVideo = wptsCameraVideo;
    }

    public void setWptsCameraPhotoInSeq(Mission wptsCameraPhotoInSeq) {
        this.wptsCameraPhotoInSeq = wptsCameraPhotoInSeq;
    }

    public void setWptsSpraying(Mission wptsSpraying) {
        this.wptsSpraying = wptsSpraying;
    }
}
