package uav.gcs.struct;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import lib.uav.struct.geom.Position3D;
import lib.uav.struct.mission.Route3D;
import lib.uav.util.UtilGeo;
import lib.uav.util.UtilString;
import uav.gcs.GCS;

/**
 * The class that reads the route file
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class ReaderRoute {

    private final Route3D route = new Route3D();
    private boolean isReady = false;
    
    /**
     * Class constructor.
     * @since version 4.0.0
     */
    public ReaderRoute() {

    }
    
    /**
     * Method that reads the route file
     * @param file the file of route
     * @since version 4.0.0
     */
    public void read(File file){
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {                
                String line = sc.nextLine();
                line = UtilString.changeValueSeparator(line);
                String v[] = line.split(";");
                double pos[] = new double[3];
                pos[0] = Double.parseDouble(v[0]);
                pos[1] = Double.parseDouble(v[1]);
                if (v.length == 3){
                    pos[2] = Double.parseDouble(v[2]);
                    route.addPosition(new Position3D(pos[0], pos[1], pos[2]));
                }else{
                    route.addPosition(new Position3D(pos[0], pos[1], 0));
                }
            }
            isReady = true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Method that reads the route file
     * @param file the file of route
     * @since version 4.0.0
     */
    public void readGeo(File file){
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {                
                String line = sc.nextLine();
                line = UtilString.changeValueSeparator(line);
                String v[] = line.split(";");
                double pos[] = new double[3];
                pos[0] = Double.parseDouble(v[0]);
                pos[1] = Double.parseDouble(v[1]);
                pos[1] = UtilGeo.convertGeoToX(GCS.pointGeo, pos[1]);
                pos[0] = UtilGeo.convertGeoToY(GCS.pointGeo, pos[0]);
                if (v.length == 3){
                    pos[2] = Double.parseDouble(v[2]);
                    route.addPosition(new Position3D(pos[1], pos[0], pos[2]));
                }else{
                    route.addPosition(new Position3D(pos[1], pos[0], 0));
                }
            }
            isReady = true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Method that reads the route file
     * @param file the file of route
     * @since version 4.0.0
     */
    public void readHGA(File file){
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                double pos[] = new double[2];
                pos[0] = sc.nextDouble();
                pos[1] = sc.nextDouble();
                sc.nextDouble();
                sc.nextDouble();
                route.addPosition(new Position3D(pos[0], pos[1], 0));
            }
            isReady = true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
        
    /**
     * Method that reads the route file
     * @param file the file of route
     * @since version 4.0.0
     */
    public void readAStar(File file){
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                line = UtilString.changeValueSeparator(line);
                String v[] = line.split(";");
                double pos[] = new double[3];
                pos[0] = Double.parseDouble(v[0]);
                pos[1] = Double.parseDouble(v[1]);
                pos[1] = Double.parseDouble(v[2]);
                route.addPosition(new Position3D(pos[0], pos[1], 0));
            }
            isReady = true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public Route3D getRoute3D(){
        return route;
    }

    public boolean isReady() {
        return isReady;
    }
    
}
