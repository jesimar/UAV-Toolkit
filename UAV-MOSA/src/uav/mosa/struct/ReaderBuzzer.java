package uav.mosa.struct;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import uav.generic.struct.Mission;
import uav.generic.struct.Position3D;
import uav.generic.struct.Route3D;
import uav.generic.struct.Waypoint;

/**
 *
 * @author jesimar
 */
public class ReaderBuzzer {
    
    private final Mission wptsBuzzer;
    
    public ReaderBuzzer(Mission wptsBuzzer){
        this.wptsBuzzer = wptsBuzzer;
    }
    
    public void reader(File inFile) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine()){
                sc.nextLine();
                String line = sc.nextLine();
                String v[] = line.split(";");
                double lat = Double.parseDouble(v[0]);
                double lng = Double.parseDouble(v[1]);
                double alt = Double.parseDouble(v[2]);
                Waypoint wpt = new Waypoint(lat, lng, alt);
                wptsBuzzer.addWaypoint(wpt);
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }        
}
