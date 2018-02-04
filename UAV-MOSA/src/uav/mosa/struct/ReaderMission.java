package uav.mosa.struct;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import uav.generic.struct.Mission3D;
import uav.generic.struct.Position3D;
import uav.generic.util.UtilString;

/**
 *
 * @author jesimar
 */
public class ReaderMission {
    
    private final Mission3D mission3D; 
    
    public ReaderMission(Mission3D mission3D){
        this.mission3D = mission3D;
    }
    
    public void reader(File inFile) throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                Position3D p3d = UtilString.split3D(line, " ");
                mission3D.addPosition3D(p3d);
            }
            sc.close();        
        } catch (NoSuchElementException ex) {
            
        }
    }        
}
