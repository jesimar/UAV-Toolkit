package uav.gcs.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import uav.generic.struct.geom.Position3D;
import uav.generic.struct.mission.Route3D;

/**
 *
 * @author Jesimar Arantes
 */
public class ReaderRoute {

    private final Route3D route3D = new Route3D();
    private final String path;
    
    public ReaderRoute(String pathFile) {
        path = pathFile;
    }
    
    public void read(){
        File file = new File(path);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {                
                String line = sc.nextLine();
                String v[] = line.split(" ");
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
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public Route3D getRoute3D(){
        return route3D;
    }
    
}
