package uav.gcs.map;

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
import marte.swing.google.maps.GoogleMapsScene;
import uav.gcs.GCS;
import uav.gcs.struct.Drone;
import uav.generic.util.UtilGeo;

/**
 * @author Jesimar Arantes
 */
public class PanelPlotGoogleMaps extends JPanel {

    private GoogleMapsScene api;
    private final String file = "./html/maps.html";
    private double latDrone;
    private double lngDrone;
    private final ReaderRoute routeIFA;
    private final ReaderRoute routeMOSA;
    private final double lngBase = GCS.pointGeo.getLng();
    private final double latBase = GCS.pointGeo.getLat();

    public PanelPlotGoogleMaps() {
        routeIFA = new ReaderRoute();
        routeMOSA = new ReaderRoute();
    }

    public void init() {
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
            this.setSize(800, 600);
            fxPanel.setPreferredSize(new Dimension(this.getWidth() - 30, this.getHeight() - 80));
            PanelPlotGoogleMaps panel = this;
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    fxPanel.setPreferredSize(new Dimension(panel.getWidth() - 30, panel.getHeight() - 80));
                    SwingUtilities.updateComponentTreeUI(panel);
                }
            });
            this.setVisible(true);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private ReaderMap mapIFA;

    public void drawMap() {
        String pathMap = "../Modules-IFA/MPGA4s/map.sgl";
        if (pathMap.equals("")) {
            System.out.println("The name of map file isn't valid.");
        }
        mapIFA = new ReaderMap(pathMap);
        mapIFA.read();
        for (int i = 0; i < mapIFA.getSizeNFZ(); i++) {
            double vetX[] = mapIFA.getVetorX_NFZ(i);
            double vetY[] = mapIFA.getVetorY_NFZ(i);
            Point2D points[] = new Point2D[vetX.length];
            for (int j = 0; j < vetX.length; j++) {
                points[j] = new Point2D.Double(
                        UtilGeo.convertYtoLatitude(latBase, vetY[j]),
                        UtilGeo.convertXtoLongitude(lngBase, latBase, vetX[j])
                );
            }
            api.addPolygon("#000000", 0.8, 2, "#FFFFFF", 0.35, points);
        }
        for (int i = 0; i < mapIFA.getSizePenalty(); i++) {
            double vetX[] = mapIFA.getVetorX_Penalty(i);
            double vetY[] = mapIFA.getVetorY_Penalty(i);
            Point2D points[] = new Point2D[vetX.length];
            for (int j = 0; j < vetX.length; j++) {
                points[j] = new Point2D.Double(
                        UtilGeo.convertYtoLatitude(latBase, vetY[j]),
                        UtilGeo.convertXtoLongitude(lngBase, latBase, vetX[j])
                );
            }
            api.addPolygon("#E47833", 0.8, 2, "#E47833", 0.35, points);
        }
        for (int i = 0; i < mapIFA.getSizeBonus(); i++) {
            double vetX[] = mapIFA.getVetorX_Bonus(i);
            double vetY[] = mapIFA.getVetorY_Bonus(i);
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
    
    public void waitingForRoutes(){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                int i = 0; 
                while (true) {
                    try {
                        Thread.sleep(500);
                        String pathRouteMOSA = "../Modules-MOSA/HGA4m/route3D" + i +  ".txt";
                        File fileMOSA = new File(pathRouteMOSA);
                        if (fileMOSA.exists()){
                            routeMOSA.readHGA(fileMOSA);
                            for (int j = 0; j < routeMOSA.getRoute3D().size(); j++) {
                                if (j + 1 < routeMOSA.getRoute3D().size()) {
                                    Point2D points[] = new Point2D[2];
                                    points[0] = new Point2D.Double(
                                        UtilGeo.convertYtoLatitude(latBase, routeMOSA.getRoute3D().getPosition3D(j).getY()),
                                        UtilGeo.convertXtoLongitude(lngBase, latBase, routeMOSA.getRoute3D().getPosition3D(j).getX())
                                    );
                                    points[1] = new Point2D.Double(
                                        UtilGeo.convertYtoLatitude(latBase, routeMOSA.getRoute3D().getPosition3D(j + 1).getY()),
                                        UtilGeo.convertXtoLongitude(lngBase, latBase, routeMOSA.getRoute3D().getPosition3D(j + 1).getX())
                                    );
                                    api.addLine("#00FF00", 0.8, 2, points);
                                }
                            }
                            i++;
                        }
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        String pathRouteMOSA = "../Modules-MOSA/CCQSP4m/route3D.txt";
                        File fileMOSA = new File(pathRouteMOSA);
                        if (fileMOSA.exists()){
                            routeMOSA.read(fileMOSA);
                            for (int i = 0; i < routeMOSA.getRoute3D().size(); i++) {
                                if (i + 1 < routeMOSA.getRoute3D().size()) {
                                    Point2D points[] = new Point2D[2];
                                    points[0] = new Point2D.Double(
                                        UtilGeo.convertYtoLatitude(latBase, routeMOSA.getRoute3D().getPosition3D(i).getY()),
                                        UtilGeo.convertXtoLongitude(lngBase, latBase, routeMOSA.getRoute3D().getPosition3D(i).getX())
                                    );
                                    points[1] = new Point2D.Double(
                                        UtilGeo.convertYtoLatitude(latBase, routeMOSA.getRoute3D().getPosition3D(i + 1).getY()),
                                        UtilGeo.convertXtoLongitude(lngBase, latBase, routeMOSA.getRoute3D().getPosition3D(i + 1).getX())
                                    );
                                    api.addLine("#00FF00", 0.8, 2, points);
                                }
                            }
                            break;
                        }
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        String pathRouteIFA = "../Modules-IFA/MPGA4s/route.txt";
                        File fileIFA = new File(pathRouteIFA);
                        if (fileIFA.exists()){
                            routeIFA.read(fileIFA);
                            for (int i = 0; i < routeIFA.getRoute3D().size(); i++) {
                                if (i + 1 < routeIFA.getRoute3D().size()) {
                                    Point2D points[] = new Point2D[2];
                                    points[0] = new Point2D.Double(
                                        UtilGeo.convertYtoLatitude(latBase, routeIFA.getRoute3D().getPosition3D(i).getY()),
                                        UtilGeo.convertXtoLongitude(lngBase, latBase, routeIFA.getRoute3D().getPosition3D(i).getX())
                                    );
                                    points[1] = new Point2D.Double(
                                        UtilGeo.convertYtoLatitude(latBase, routeIFA.getRoute3D().getPosition3D(i + 1).getY()),
                                        UtilGeo.convertXtoLongitude(lngBase, latBase, routeIFA.getRoute3D().getPosition3D(i + 1).getX())
                                    );
                                    api.addLine("#FF0000", 0.8, 2, points);
                                }
                            }
                            break;
                        }
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }

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

    /*
    private static int count_markers = 0;
    
    public PanelPlotGoogleMaps() {
        try {
            final GoogleMapsScene api = GoogleMapsScene.launch(new File("./html/maps.html"), (String) null);
            
            JFrame frame = new JFrame("Google Maps JavaScript API on a swing JPanel");
            
            final JFXPanel fxPanel = new JFXPanel(){
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
//                g.setColor(Color.WHITE);
//                g.fillRect(15, 55, 420, 90);
//                g.setColor(Color.BLACK);
//                g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 13));
//                g.drawString("INSTRUCTIONS:", 20, 70);
//                g.drawString("0) status.started = "+started, 20, 95);
                }
            };
            
            api.attach(fxPanel);
            
            JButton btmSetFixedCenter = new JButton("Center");
            btmSetFixedCenter.addActionListener((event)->{
                api.setCenter(-27.573252, -48.537125);
            });
            
            JButton btmAddRandomMarker = new JButton("Marker");
            btmAddRandomMarker.addActionListener((event)->{
                double dlat = (1-Math.random()*2)/1000.0;
                double dlng = (1-Math.random()*2)/1000.0;
                double lat = -27.573252+dlat;
                double lng = -48.537125+dlng;
                String key = ""+(count_markers++);
                api.addMarker(lat, lng, key, null);
            });
            
            JButton btmAddRandomPoly = new JButton("Poly");
            btmAddRandomPoly.addActionListener((event)->{
                //Create a square in a random position
                double size = 0.0003;
                double dlat = (1-Math.random()*2)/1000.0;
                double dlng = (1-Math.random()*2)/1000.0;
                double lat = -27.573252+dlat;
                double lng = -48.537125+dlng;
                
                api.addPolygon("#FF0000", 0.8, 2, "#FF0000", 0.35,
                        new Point2D.Double(lat+size, lng+size),
                        new Point2D.Double(lat+size, lng-size),
                        new Point2D.Double(lat-size, lng-size),
                        new Point2D.Double(lat-size, lng+size)
                );
            });
            
            frame.setLayout(new FlowLayout());
            frame.add(btmSetFixedCenter);
            frame.add(btmAddRandomMarker);
            frame.add(btmAddRandomPoly);
            
            frame.add(fxPanel);
            
            frame.setSize(800, 600);
            fxPanel.setPreferredSize(new Dimension(frame.getWidth()-30, frame.getHeight()-80));
            
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    fxPanel.setPreferredSize(new Dimension(frame.getWidth()-30, frame.getHeight()-80));
                    SwingUtilities.updateComponentTreeUI(frame);
                }
            });
            
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
     */
}
