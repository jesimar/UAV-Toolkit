package uav.gcs.plotmap;

import uav.gcs.struct.ReaderMap;
import uav.gcs.struct.ReaderRoute;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.Executors;
import marte.swing.graphics.pkg2d.navigation.sPanelDraw;
import uav.gcs.GCS;
import uav.gcs.struct.Drone;
import uav.gcs.struct.EnabledResources;
import uav.generic.struct.constants.TypePlanner;
import uav.generic.struct.constants.TypeSystemExecIFA;
import uav.generic.struct.constants.TypeSystemExecMOSA;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.struct.geom.Position3D;
import uav.generic.struct.mission.Route3D;
import uav.generic.util.UtilGeo;

/**
 * The class that plots the map and routes using the Graphics2D.
 * @author Jesimar Arantes
 * @since version 3.0.0
 */
public class PanelPlotMission extends sPanelDraw {

    private static final double CONST_UNIT = 0.01; //in centimeters

    private static final Color COLOR_NFZ = new Color(97, 97, 97);
    private static final Color COLOR_PENALTY = new Color(255, 103, 61);
    private static final Color COLOR_BONUS = new Color(103, 167, 255);
    private static final Color COLOR_ROUTE_IFA = Color.RED;
    private static final Color COLOR_ROUTE_MOSA = Color.GREEN;
    private static final Color COLOR_ROUTE_MOSA_SIMP = Color.BLACK;
    private static final Color COLOR_ROUTE_DRONE = Color.GRAY;
    private static final Color COLOR_DRONE = Color.BLUE;
    private static final Color COLOR_MAX_DIST = new Color(0, 255, 0, 50);
    private final ReaderFileConfig config;
    private final EnabledResources enabledResources;
    
    private ReaderMap map;
    private final ReaderRoute routeIFA;
    private final ReaderRoute routeMOSA;
    private final ReaderRoute routeMOSASimplifier;
    private final Route3D routeDrone;
    private double pxDrone;
    private double pyDrone;
    private double maxDistReached;
    private boolean printDrone = false;

    /**
     * Class constructor
     * @since version 3.0.0
     */
    public PanelPlotMission() {
        super(Color.WHITE);
        config = ReaderFileConfig.getInstance();
        enabledResources = EnabledResources.getInstance();
        String pathMap = config.getDirFiles() + "map-full.sgl";
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER)){
            map = new ReaderMap(pathMap);
            map.read();
        }
        routeIFA = new ReaderRoute();
        routeMOSA = new ReaderRoute();
        routeMOSASimplifier = new ReaderRoute();
        routeDrone = new Route3D();
    }

    /**
     * Sets plot dimensions.
     * @param width width value/dimension in x axes
     * @param height height value/dimension in y axes
     * @since version 3.0.0
     */
    public void setNewDimensions(int width, int height) {
        this.Config(width, height);
        this.restart_system();
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.FIXED_ROUTE)){
            return;
        }
        if (map.getVetX_NFZ().length == 0) {
            return;
        }
        int xInit = Arrays.stream(map.getVetX_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .min().getAsInt()
                ).min().getAsInt();
        int yInit = Arrays.stream(map.getVetY_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .min().getAsInt()
                ).min().getAsInt();
        int xFinal = Arrays.stream(map.getVetX_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .max().getAsInt()
                ).max().getAsInt();
        int yFinal = Arrays.stream(map.getVetY_NFZ())
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

    /**
     * Paint in screen the info about map, mission, drone position.
     * @param g2 the graphics instance
     * @since version 3.0.0
     */
    @Override
    protected void paintDynamicScene(Graphics2D g2) {
        //Draw Map
        if (config.getSystemExecMOSA().equals(TypeSystemExecMOSA.PLANNER) && enabledResources.showMap){
            g2.setColor(COLOR_NFZ);
            for (int i = 0; i < map.getSizeNFZ(); i++) {
                g2.fillPolygon(
                        Arrays.stream(map.getVetX_NFZ(i)).mapToInt((v) -> toUnit(v)).toArray(),
                        Arrays.stream(map.getVetY_NFZ(i)).mapToInt((v) -> toUnit(v)).toArray(),
                        map.getVetX_NFZ(i).length);
            }
            g2.setColor(COLOR_PENALTY);
            for (int i = 0; i < map.getSizePenalty(); i++) {
                g2.fillPolygon(
                        Arrays.stream(map.getVetX_Penalty(i)).mapToInt((v) -> toUnit(v)).toArray(),
                        Arrays.stream(map.getVetY_Penalty(i)).mapToInt((v) -> toUnit(v)).toArray(),
                        map.getVetX_Penalty(i).length);
            }
            g2.setColor(COLOR_BONUS);
            for (int i = 0; i < map.getSizeBonus(); i++) {
                g2.fillPolygon(
                        Arrays.stream(map.getVetX_Bonus(i)).mapToInt((v) -> toUnit(v)).toArray(),
                        Arrays.stream(map.getVetY_Bonus(i)).mapToInt((v) -> toUnit(v)).toArray(),
                        map.getVetX_Bonus(i).length);
            }
        }

        //Draw Routes
        if (routeIFA.isReady() && enabledResources.showRouteReplanner) {
            g2.setColor(COLOR_ROUTE_IFA);
            for (int i = 0; i < routeIFA.getRoute3D().size(); i++) {
                g2.fillOval(
                        toUnit(routeIFA.getRoute3D().getPosition(i).getX()) - 15,
                        toUnit(routeIFA.getRoute3D().getPosition(i).getY()) - 15,
                        30,
                        30
                );
                if (i + 1 < routeIFA.getRoute3D().size()) {
                    g2.drawLine(
                            toUnit(routeIFA.getRoute3D().getPosition(i).getX()),
                            toUnit(routeIFA.getRoute3D().getPosition(i).getY()),
                            toUnit(routeIFA.getRoute3D().getPosition(i + 1).getX()),
                            toUnit(routeIFA.getRoute3D().getPosition(i + 1).getY())
                    );
                }
            }
        }

        if (routeMOSA.isReady() && enabledResources.showRoutePlanner) {
            g2.setColor(COLOR_ROUTE_MOSA);
            for (int i = 0; i < routeMOSA.getRoute3D().size(); i++) {
                g2.fillOval(
                        toUnit(routeMOSA.getRoute3D().getPosition(i).getX()) - 15,
                        toUnit(routeMOSA.getRoute3D().getPosition(i).getY()) - 15,
                        30,
                        30
                );
                if (i < routeMOSA.getRoute3D().size() - 1) {
                    g2.drawLine(
                            toUnit(routeMOSA.getRoute3D().getPosition(i).getX()),
                            toUnit(routeMOSA.getRoute3D().getPosition(i).getY()),
                            toUnit(routeMOSA.getRoute3D().getPosition(i + 1).getX()),
                            toUnit(routeMOSA.getRoute3D().getPosition(i + 1).getY())
                    );
                }
            }
        }
        if (routeMOSASimplifier.isReady() && enabledResources.showRouteSimplifier) {
            g2.setColor(COLOR_ROUTE_MOSA_SIMP);
            for (int i = 0; i < routeMOSASimplifier.getRoute3D().size(); i++) {
                g2.fillOval(
                        toUnit(routeMOSASimplifier.getRoute3D().getPosition(i).getX()) - 15,
                        toUnit(routeMOSASimplifier.getRoute3D().getPosition(i).getY()) - 15,
                        30,
                        30
                );
                if (i < routeMOSASimplifier.getRoute3D().size() - 1) {
                    g2.drawLine(
                            toUnit(routeMOSASimplifier.getRoute3D().getPosition(i).getX()),
                            toUnit(routeMOSASimplifier.getRoute3D().getPosition(i).getY()),
                            toUnit(routeMOSASimplifier.getRoute3D().getPosition(i + 1).getX()),
                            toUnit(routeMOSASimplifier.getRoute3D().getPosition(i + 1).getY())
                    );
                }
            }
        }
        if (enabledResources.showRouteDrone) {
            g2.setColor(COLOR_ROUTE_DRONE);
            for (int i = 0; i < routeDrone.size(); i++) {
                g2.fillOval(
                        toUnit(routeDrone.getPosition(i).getX()) - 15,
                        toUnit(routeDrone.getPosition(i).getY()) - 15,
                        30,
                        30
                );
                if (i < routeDrone.size() - 1) {
                    g2.drawLine(
                            toUnit(routeDrone.getPosition(i).getX()),
                            toUnit(routeDrone.getPosition(i).getY()),
                            toUnit(routeDrone.getPosition(i + 1).getX()),
                            toUnit(routeDrone.getPosition(i + 1).getY())
                    );
                }
            }
        }
        if (enabledResources.showPositionDrone && printDrone){
            g2.setColor(COLOR_DRONE);
            g2.fillOval(toUnit(pxDrone) - 30, toUnit(pyDrone) - 30, 60, 60);
        }
        if (enabledResources.showMaxDistReached && printDrone){
            g2.setColor(COLOR_MAX_DIST);
            g2.fillOval(toUnit(pxDrone) - toUnit(maxDistReached)/2, 
                    toUnit(pyDrone) - toUnit(maxDistReached)/2, 
                    toUnit(maxDistReached), toUnit(maxDistReached));
        }
    }

    /**
     * The method wait until the routes are ready.
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
                                routeMOSA.readHGA(fileMOSA);
                                repaint();
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
                                routeMOSA.read(fileMOSA);
                                repaint();
                                break;
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
                                repaint();
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
                                routeMOSASimplifier.readGeo(fileSimplifier);
                                repaint();
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
                            pxDrone = UtilGeo.convertGeoToX(GCS.pointGeo, lngDrone);
                            pyDrone = UtilGeo.convertGeoToY(GCS.pointGeo, latDrone);
                            maxDistReached = drone.estimatedMaxDistReached;
                            routeDrone.addPosition(new Position3D(pxDrone, pyDrone, 0));
                            printDrone = true;
                        }
                        repaint();
                    } catch (InterruptedException ex) {

                    }
                }
            }
        });
    }
    
    /**
     * Converts to the unit used in the plot (in centimeters).
     * @param value the value to convert
     * @return the converted value
     * @since version 3.0.0
     */
    public static int toUnit(double value) {
        return Math.round((float) (value / CONST_UNIT));
    }
}
