package uav.gcs.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import uav.generic.struct.geom.Position3D;
import uav.generic.struct.mission.Route3D;
import uav.generic.util.UtilString;

/**
 *
 * @author Jesimar Arantes
 */
public class ReaderRoute {

    private final Route3D route3D = new Route3D();
    private boolean ready = false;
    
    public ReaderRoute() {

    }
    
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
                    route3D.addPosition3D(new Position3D(pos[0], pos[1], pos[2]));
                }else{
                    route3D.addPosition3D(new Position3D(pos[0], pos[1], 0));
                }
            }
            ready = true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void readHGA(File file){
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                double pos[] = new double[2];
                pos[0] = sc.nextDouble();
                pos[1] = sc.nextDouble();
                sc.nextDouble();
                sc.nextDouble();
                route3D.addPosition3D(new Position3D(pos[0], pos[1], 0));
            }
            ready = true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public Route3D getRoute3D(){
        return route3D;
    }

    public boolean isReady() {
        return ready;
    }
    
}
