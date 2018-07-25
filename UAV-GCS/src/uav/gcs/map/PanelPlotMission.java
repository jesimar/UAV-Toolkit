package uav.gcs.map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import marte.swing.graphics.pkg2d.navigation.sPanelDraw;

/**
 *
 * @author Jesimar Arantes
 */
public class PanelPlotMission extends sPanelDraw {

    private static final double CONST_UNIT = 0.01; //in centimeters

    private static final Color COLOR_NFZ = new Color(97, 97, 97);
    private static final Color COLOR_PENALTY = new Color(255, 103, 61);
    private static final Color COLOR_BONUS = new Color(103, 167, 255);
    private static final Color COLOR_ROUTE = Color.RED;
    private ReaderMap map;
    private ReaderRoute route;

    public PanelPlotMission() {
        super(Color.WHITE);
        ReaderConfig reader = ReaderConfig.getInstance();
        reader.read();

        String pathMap = reader.getFileMap();
        if (pathMap.equals("")) {
            System.out.println("Informe o nome do arquivo de mapa");
            System.exit(0);
        }
        map = new ReaderMap(pathMap);
        map.read();

        String pathRoute = reader.getFileRoute();
        route = new ReaderRoute(pathRoute);
        if (!pathRoute.equals("")) {
            route.read();
        }
    }
    
    public void setNewDimensions(int width, int height){
        this.Config(width, height);
        this.restart_system();
        int xInit = Arrays.stream(map.getVetorX_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .min().getAsInt()
                ).min().getAsInt();
        int yInit = Arrays.stream(map.getVetorY_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .min().getAsInt()
                ).min().getAsInt();
        int xFinal = Arrays.stream(map.getVetorX_NFZ())
                .mapToInt(
                        (d) -> Arrays.stream(d)
                                .mapToInt(v -> (int) v)
                                .max().getAsInt()
                ).max().getAsInt();
        int yFinal = Arrays.stream(map.getVetorY_NFZ())
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
        for (int i = 0; i < map.getSizeNFZ(); i++) {
            g2.fillPolygon(
                    Arrays.stream(map.getVetorX_NFZ(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    Arrays.stream(map.getVetorY_NFZ(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    map.getVetorX_NFZ(i).length);
        }
        g2.setColor(COLOR_PENALTY);
        for (int i = 0; i < map.getSizePenalty(); i++) {
            g2.fillPolygon(
                    Arrays.stream(map.getVetorX_Penalty(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    Arrays.stream(map.getVetorY_Penalty(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    map.getVetorX_Penalty(i).length);
        }
        g2.setColor(COLOR_BONUS);
        for (int i = 0; i < map.getSizeBonus(); i++) {
            g2.fillPolygon(
                    Arrays.stream(map.getVetorX_Bonus(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    Arrays.stream(map.getVetorY_Bonus(i)).mapToInt((v) -> toUnit(v)).toArray(),
                    map.getVetorX_Bonus(i).length);
        }

        //Draw Routes
        g2.setColor(COLOR_ROUTE);
        for (int i = 0; i < route.getRoute3D().size(); i++) {
            g2.fillOval(
                    toUnit(route.getRoute3D().getPosition3D(i).getX()) - 15,
                    toUnit(route.getRoute3D().getPosition3D(i).getY()) - 15,
                    30,
                    30
            );
            if (i + 1 < route.getRoute3D().size()) {
                g2.drawLine(
                        toUnit(route.getRoute3D().getPosition3D(i).getX()),
                        toUnit(route.getRoute3D().getPosition3D(i).getY()),
                        toUnit(route.getRoute3D().getPosition3D(i + 1).getX()),
                        toUnit(route.getRoute3D().getPosition3D(i + 1).getY())
                );
            }
        }
    }

    public static int toUnit(double value) {
        return Math.round((float) (value / CONST_UNIT));
    }
}
