package uav.generic.reader;

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
 * @since version 2.0.0
 */
public class ReaderFileMission {
    
    /**
     * Read a file in this format (Cartesian coordinates):.
     * ---------------------------------------
     * | 8.644050340 29.3082899 0.00         |
     * | 10.10606405 6.59494416 0.00         |
     * | ...                                 |
     * ---------------------------------------
     * @param file File to read
     * @param mission3D object to put the waypoints of the mission 3D
     * @throws FileNotFoundException 
     * @since version 2.0.0
     */
    public static void mission3D(File file, Mission3D mission3D) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                Position3D p3d = UtilString.split3D(line, " ");
                mission3D.addPosition(p3d);
            }
            sc.close();        
        } catch (NoSuchElementException ex) {
            
        }
    }        
    
    /**
     * Read a file in this format (geographical coordinates [lat, lng]):.
     * ---------------------------------------
     * | Waypoints Buzzer                    |
     * | 3                                   |
     * | -22.00593333;-47.89870833;0.00      |
     * | -22.00613771;-47.89869416;0.00      |
     * | -22.00204909;-47.93333564;0.00      |
     * | ...                                 |
     * ---------------------------------------
     * @param file File to read
     * @param wptsBuzzer object to put the waypoints of the buzzer
     * @throws FileNotFoundException 
     * @since version 2.0.0
     */
    public static void missionBuzzer(File file, Mission wptsBuzzer) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String lineCommentary = sc.nextLine();
                String lineNumber = sc.nextLine();
                if (lineCommentary.equals("Waypoints Buzzer")){
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        String line = sc.nextLine();
                        String v[] = line.split(";");
                        double lat = Double.parseDouble(v[0]);
                        double lng = Double.parseDouble(v[1]);
                        double alt = Double.parseDouble(v[2]);
                        Waypoint wpt = new Waypoint(lat, lng, alt);
                        wptsBuzzer.addWaypoint(wpt);
                    }
                }
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }
    
    /**
     * Read a file in this format (geographical coordinates [lat, lng]):.
     * ---------------------------------------
     * | ...                                 |
     * | Waypoints Camera Picture            |
     * | 2                                   |
     * | -22.00593333;-47.89870833;0.00      |
     * | -22.00613771;-47.89869416;0.00      |
     * | ...                                 |
     * ---------------------------------------
     * @param file File to read
     * @param wptsPhoto object to put the waypoints of the camera to picture
     * @throws FileNotFoundException 
     * @since version 3.0.0
     */
    public static void missionCameraPicture(File file, Mission wptsPhoto) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String lineCommentary = sc.nextLine();
                String lineNumber = sc.nextLine();
                if (lineCommentary.equals("Waypoints Camera Picture")){
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        String line = sc.nextLine();
                        String v[] = line.split(";");
                        double lat = Double.parseDouble(v[0]);
                        double lng = Double.parseDouble(v[1]);
                        double alt = Double.parseDouble(v[2]);
                        Waypoint wpt = new Waypoint(lat, lng, alt);
                        wptsPhoto.addWaypoint(wpt);
                    }
                }else{
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        sc.nextLine();
                    }
                }
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }
    
    /**
     * Read a file in this format (geographical coordinates [lat, lng]):.
     * ---------------------------------------
     * | ...                                 |
     * | Waypoints Camera Video              |
     * | 2                                   |
     * | -22.00593333;-47.89870833;0.00      |
     * | -22.00613771;-47.89869416;0.00      |
     * | ...                                 |
     * ---------------------------------------
     * @param file File to read
     * @param wptsVideo object to put the waypoints of the camera to video
     * @throws FileNotFoundException 
     * @since version 3.0.0
     */
    public static void missionCameraVideo(File file, Mission wptsVideo) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String lineCommentary = sc.nextLine();
                String lineNumber = sc.nextLine();
                if (lineCommentary.equals("Waypoints Camera Video")){
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        String line = sc.nextLine();
                        String v[] = line.split(";");
                        double lat = Double.parseDouble(v[0]);
                        double lng = Double.parseDouble(v[1]);
                        double alt = Double.parseDouble(v[2]);
                        Waypoint wpt = new Waypoint(lat, lng, alt);
                        wptsVideo.addWaypoint(wpt);
                    }
                }else{
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        sc.nextLine();
                    }
                }
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }
    
    /**
     * Read a file in this format (geographical coordinates [lat, lng]):.
     * ---------------------------------------
     * | ...                                 |
     * | Waypoints Camera Photo In Sequence  |
     * | 2                                   |
     * | -22.00593333;-47.89870833;0.00      |
     * | -22.00613771;-47.89869416;0.00      |
     * | ...                                 |
     * ---------------------------------------
     * @param file File to read
     * @param wptsPhoto object to put the waypoints of the camera to photo in sequence
     * @throws FileNotFoundException 
     * @since version 3.0.0
     */
    public static void missionCameraPhotoInSequence(File file, Mission wptsPhoto) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String lineCommentary = sc.nextLine();
                String lineNumber = sc.nextLine();
                if (lineCommentary.equals("Waypoints Camera Photo In Sequence")){
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        String line = sc.nextLine();
                        String v[] = line.split(";");
                        double lat = Double.parseDouble(v[0]);
                        double lng = Double.parseDouble(v[1]);
                        double alt = Double.parseDouble(v[2]);
                        Waypoint wpt = new Waypoint(lat, lng, alt);
                        wptsPhoto.addWaypoint(wpt);
                    }
                }else{
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        sc.nextLine();
                    }
                }
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }
    
    /**
     * Read a file in this format (geographical coordinates [lat, lng]):.
     * ---------------------------------------
     * | ...                                 |
     * | Waypoints Spraying                  |
     * | 2                                   |
     * | -22.00203126;-47.93308617;0.00      |
     * | -22.00207005;-47.93320877;0.00      |
     * | ...                                 |
     * ---------------------------------------
     * @param file File to read
     * @param wptsSpraying object to put the waypoints of the camera to spraying
     * @throws FileNotFoundException 
     * @since version 3.0.0
     */
    public static void missionSpraying(File file, Mission wptsSpraying) 
            throws FileNotFoundException {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String lineCommentary = sc.nextLine();
                String lineNumber = sc.nextLine();
                if (lineCommentary.equals("Waypoints Spraying")){
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        String line = sc.nextLine();
                        String v[] = line.split(";");
                        double lat = Double.parseDouble(v[0]);
                        double lng = Double.parseDouble(v[1]);
                        double alt = Double.parseDouble(v[2]);
                        Waypoint wpt = new Waypoint(lat, lng, alt);
                        wptsSpraying.addWaypoint(wpt);
                    }
                }else{
                    for (int i = 0; i < Integer.parseInt(lineNumber); i++){
                        sc.nextLine();
                    }
                }
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    }
    
    /**
     * Read a file in this format (cartesian coordinates [x, y, vx, vy]):.
     * ---------------------------------------
     * | 8.644050340 29.3082899 3.00 4.29    |
     * | 10.10606405 6.59494416 2.00 2.13    |
     * | ...                                 |
     * ---------------------------------------
     * @param inFile File to read
     * @param route3D object to put the waypoints/positions read
     * @throws FileNotFoundException 
     * @since version 2.0.0
     */
    public static void readerRoute(File inFile, Route3D route3D) 
            throws FileNotFoundException {
        double alt = ReaderFileConfig.getInstance().getAltRelMission();
        try {
            Scanner sc = new Scanner(inFile);
            while(sc.hasNextLine()){
                double px = sc.nextDouble();
                double py = sc.nextDouble();
                double vx = sc.nextDouble();
                double vy = sc.nextDouble();
                Position3D p3d = new Position3D(px, py, alt);
                route3D.addPosition(p3d);
            }
            sc.close();
        } catch (NoSuchElementException ex) {
            
        }
    } 
}
