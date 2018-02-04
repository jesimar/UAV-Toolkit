package uav.mosa.struct;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import uav.generic.struct.Position3D;
import uav.generic.struct.Route3D;

/**
 *
 * @author jesimar
 */
public class ReaderRoute {
    
    private final Route3D route3D; 
    private final double alt;
    
    public ReaderRoute(Route3D route3D){
        this.route3D = route3D;
        this.alt = ReaderFileConfig.getInstance().getAltitudeRelative();
    }
    
    public void reader(File inFile) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine()){
                double px = sc.nextDouble();
                double py = sc.nextDouble();
                double vx = sc.nextDouble();
                double vy = sc.nextDouble();
                Position3D p3d = new Position3D(px, py, alt);
                route3D.addPosition3D(p3d);
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }        
}
