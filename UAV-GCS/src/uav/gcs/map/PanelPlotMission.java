package uav.gcs.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Executors;
import marte.swing.graphics.pkg2d.navigation.sPanelDraw;
import uav.gcs.GCS;
import uav.gcs.struct.Drone;
import uav.generic.util.UtilGeo;

/**
 *
 * @author Jesimar Arantes
 */
public class PanelPlotMission extends sPanelDraw {

    private static final double CONST_UNIT = 0.01; //in centimeters

    private static final Color COLOR_NFZ        = new Color(97, 97, 97);
    private static final Color COLOR_PENALTY    = new Color(255, 103, 61);
    private static final Color COLOR_BONUS      = new Color(103, 167, 255);
    private static final Color COLOR_ROUTE_IFA  = Color.RED;
    private static final Color COLOR_ROUTE_MOSA = Color.GREEN;
    private static final Color COLOR_DRONE      = Color.BLUE;
    private final ReaderMap mapIFA;
    private final ReaderRoute routeIFA;
    private final ReaderRoute routeMOSA;
    private double pxDrone;
    private double pyDrone;

    public PanelPlotMission() {
        super(Color.WHITE);
        String pathMap = "../Modules-IFA/MPGA4s/map.sgl";
        if (pathMap.equals("")) {
            System.out.println("The name of map file isn't valid.");
        }
        mapIFA = new ReaderMap(pathMap);
        mapIFA.read();
        routeIFA = new ReaderRoute();
        routeMOSA = new ReaderRoute();
    }

    public void setNewDimensions(int width, int height) {
        this.Config(width, height);
        this.restart_system();
        int xInit = Arrays.stream(mapIFA.getVetorX_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .min().getAsInt()
                ).min().getAsInt();
        int yInit = Arrays.stream(mapIFA.getVetorY_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .min().getAsInt()
                ).min().getAsInt();
        int xFinal = Arrays.stream(mapIFA.getVetorX_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .max().getAsInt()
                ).max().getAsInt();
        int yFinal = Arrays.stream(mapIFA.getVetorY_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .max().getAsInt()
                ).max().getAsInt();

        this.goTo(
                toUnit(xInit),
                toUnit(yInit),
                toUnit(xFinal - xInit),
                toUnit(yFinal - yInit)
        );
        this.repaint();
    }

    @Override
    protected void paintDynamicScene(Graphics2D g2) {
        //Draw Map
        g2.setColor(COLOR_NFZ);
        for (int i = 0; i < mapIFA.getSizeNFZ(); i++) {
            g2.fillPolygon(
                    Arrays.stream(mapIFA.getVetorX_NFZ(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    Arrays.stream(mapIFA.getVetorY_NFZ(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    mapIFA.getVetorX_NFZ(i).length);
        }
        g2.setColor(COLOR_PENALTY);
        for (int i = 0; i < mapIFA.getSizePenalty(); i++) {
            g2.fillPolygon(
                    Arrays.stream(mapIFA.getVetorX_Penalty(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    Arrays.stream(mapIFA.getVetorY_Penalty(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    mapIFA.getVetorX_Penalty(i).length);
        }
        g2.setColor(COLOR_BONUS);
        for (int i = 0; i < mapIFA.getSizeBonus(); i++) {
            g2.fillPolygon(
                    Arrays.stream(mapIFA.getVetorX_Bonus(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    Arrays.stream(mapIFA.getVetorY_Bonus(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    mapIFA.getVetorX_Bonus(i).length);
        }

        //Draw Routes
        if (routeIFA.isReady()){
            g2.setColor(COLOR_ROUTE_IFA);
            for (int i = 0; i < routeIFA.getRoute3D().size(); i++) {
                g2.fillOval(
                        toUnit(routeIFA.getRoute3D().getPosition3D(i).getX()) - 15,
                        toUnit(routeIFA.getRoute3D().getPosition3D(i).getY()) - 15,
                        30,
                        30
                );
                if (i + 1 < routeIFA.getRoute3D().size()) {
                    g2.drawLine(
                            toUnit(routeIFA.getRoute3D().getPosition3D(i).getX()),
                            toUnit(routeIFA.getRoute3D().getPosition3D(i).getY()),
                            toUnit(routeIFA.getRoute3D().getPosition3D(i + 1).getX()),
                            toUnit(routeIFA.getRoute3D().getPosition3D(i + 1).getY())
                    );
                }
            }
        }
        
        if (routeMOSA.isReady()){
            g2.setColor(COLOR_ROUTE_MOSA);
            for (int i = 0; i < routeMOSA.getRoute3D().size(); i++) {
                g2.fillOval(
                        toUnit(routeMOSA.getRoute3D().getPosition3D(i).getX()) - 15,
                        toUnit(routeMOSA.getRoute3D().getPosition3D(i).getY()) - 15,
                        30,
                        30
                );
                if (i < routeMOSA.getRoute3D().size() - 1) {
                    g2.drawLine(
                            toUnit(routeMOSA.getRoute3D().getPosition3D(i).getX()),
                            toUnit(routeMOSA.getRoute3D().getPosition3D(i).getY()),
                            toUnit(routeMOSA.getRoute3D().getPosition3D(i + 1).getX()),
                            toUnit(routeMOSA.getRoute3D().getPosition3D(i + 1).getY())
                    );
                }
            }
        }
        g2.setColor(COLOR_DRONE);
        g2.fillOval(toUnit(pxDrone)-30, toUnit(pyDrone)-30, 60, 60);
    }

    public static int toUnit(double value) {
        return Math.round((float) (value / CONST_UNIT));
    }

    public void waitingForRoutes() {
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
                            repaint();
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
                            repaint();
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
                            repaint();
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
                        double lat = drone.gps.lat;
                        double lng = drone.gps.lng;
                        pxDrone = UtilGeo.convertGeoToX(GCS.pointGeo, lng);
                        pyDrone = UtilGeo.convertGeoToY(GCS.pointGeo, lat);
                        repaint();
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }
}
