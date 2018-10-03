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
import java.util.concurrent.Executors;
import javafx.embed.swing.JFXPanel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import lib.uav.reader.ReaderFileConfig;
import lib.uav.struct.constants.TypePlanner;
import lib.uav.struct.constants.TypeSystemExecIFA;
import lib.uav.struct.constants.TypeSystemExecMOSA;
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
    private final ReaderRoute routeMOSASimplifier;
    private ReaderMap map;
    private final double lngBase = GCS.pointGeo.getLng();
    private final double latBase = GCS.pointGeo.getLat();
    private boolean firstTime = true;

    /**
     * Class constructor
     * @since version 3.0.0
     */
    public PanelPlotGoogleMaps() {
        config = ReaderFileConfig.getInstance();
        routeIFA = new ReaderRoute();
        routeMOSA = new ReaderRoute();
        routeMOSASimplifier = new ReaderRoute();
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
                    int j = 0;
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirPlanner() + "route3D" + j + ".txt";
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                routeMOSA.readHGA(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00");
                                j++;
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
                                routeMOSA.read(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00");
                                break;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }
            });
        }
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)
                && config.getTypePlanner().equals(TypePlanner.PATH_PLANNER4M)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    int j = 0;
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteMOSA = config.getDirPlanner() + "route3D" + j + ".txt";
                            File fileMOSA = new File(pathRouteMOSA);
                            if (fileMOSA.exists()) {
                                routeMOSA.readAStar(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00");
                                j++;
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
                                routeMOSA.readGeo(fileMOSA);
                                addRouteInAPIxy(routeMOSA, "#00FF00");
                                repaint();
                                break;
                            }
                        } catch (InterruptedException ex) {

                        }
                    }
                }

            });
        }
        if (config.getSystemExecIFA().equals(TypeSystemExecIFA.REPLANNER)) {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                            String pathRouteIFA = config.getDirReplanner() + "route.txt";
                            File fileIFA = new File(pathRouteIFA);
                            if (fileIFA.exists()) {
                                routeIFA.read(fileIFA);
                                addRouteInAPIxy(routeIFA, "#FF0000");
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
                                routeMOSASimplifier.read(fileSimplifier);
                                addRouteInAPIlatlng(routeMOSASimplifier, "#000000");
                                break;
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
    
    private void addRouteInAPIxy(ReaderRoute route, String color){
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
                api.addLine(color, 0.8, 2, points);
            }
        }
    }
    
    private void addRouteInAPIlatlng(ReaderRoute route, String color){
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
                api.addLine(color, 0.8, 2, points);
            }
        }
    }
}
