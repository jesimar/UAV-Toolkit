package uav.ifa.struct;

import uav.generic.struct.ReaderFileConfigGlobal;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import uav.generic.struct.geom.Position3D;
import uav.generic.struct.mission.Route3D;

/**
 *
 * @author Jesimar S. Arantes
 */
public class ReaderRoute {
    
    private final Route3D route3D; 
    private final double alt;
    
    public ReaderRoute(Route3D route3D){
        this.route3D = route3D;
        this.alt = ReaderFileConfigGlobal.getInstance().getAltitudeRelativeMission();
    }
    
    public void reader(File inFile) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine()){
                double px = sc.nextDouble();
                double py = sc.nextDouble();
                Position3D p3d = new Position3D(px, py, alt);
                route3D.addPosition3D(p3d);
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }        
}
