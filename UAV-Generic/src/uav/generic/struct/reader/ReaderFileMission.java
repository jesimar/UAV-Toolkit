package uav.generic.struct.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import uav.generic.struct.Waypoint;
import uav.generic.struct.mission.Mission3D;
import uav.generic.struct.geom.Position3D;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.mission.Route3D;
import uav.generic.util.UtilString;

/**
 * Class that reads different mission files.
 * @author Jesimar S. Arantes
 */
public class ReaderFileMission {
    
    /**
     * Read a file in this format (Cartesian coordinates):.
     * ---------------------------------------
     * | 8.644050340 29.3082899 0.00         |
     * | 10.10606405 6.59494416 0.00         |
     * | ...                                 |
     * ---------------------------------------
     * @param file - File to read
     * @param mission3D - object to put the waypoints of the mission 3D
     * @throws FileNotFoundException 
     */
    public static void mission3D(File file, Mission3D mission3D) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                Position3D p3d = UtilString.split3D(line, " ");
                mission3D.addPosition3D(p3d);
            }
            sc.close();        
        } catch (NoSuchElementException ex) {
            
        }
    }        
    
    /**
     * Read a file in this format (geographical coordinates [lat, lng]):.
     * ---------------------------------------
     * | turn-on-the-buzzer_1                |
     * | -22.00593333;-47.89870833;0.00      |
     * | turn-on-the-buzzer_2                |
     * | -22.00613771;-47.89869416;0.00      |
     * | ...                                 |
     * ---------------------------------------
     * @param file - File to read
     * @param wptsBuzzer - object to put the waypoints of the buzzer
     * @throws FileNotFoundException 
     */
    public static void missionBuzzer(File file, Mission wptsBuzzer) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                sc.nextLine();//read line of commentary
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
    
    /**
     * Read a file in this format (geographical coordinates [lat, lng]):.
     * ---------------------------------------
     * | take-a-picture-1                    |
     * | -22.00593333;-47.89870833;0.00      |
     * | take-a-picture-2                    |
     * | -22.00613771;-47.89869416;0.00      |
     * | ...                                 |
     * ---------------------------------------
     * @param file - File to read
     * @param wptsCamera - object to put the waypoints of the camera
     * @throws FileNotFoundException 
     */
    public static void missionCamera(File file, Mission wptsCamera) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                sc.nextLine();//read line of commentary
                String line = sc.nextLine();
                String v[] = line.split(";");
                double lat = Double.parseDouble(v[0]);
                double lng = Double.parseDouble(v[1]);
                double alt = Double.parseDouble(v[2]);
                Waypoint wpt = new Waypoint(lat, lng, alt);
                wptsCamera.addWaypoint(wpt);
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }
    
    public static void readerRoute(File inFile, Route3D route3D) 
            throws FileNotFoundException {
        double alt = ReaderFileConfigGlobal.getInstance().getAltRelMission();
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
