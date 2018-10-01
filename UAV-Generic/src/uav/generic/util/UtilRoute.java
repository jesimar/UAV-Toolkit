package uav.generic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import uav.generic.struct.Waypoint;
import uav.generic.struct.constants.TypeWaypoint;
import uav.generic.struct.mission.Mission;

/**
 * Class with feature set on the route.
 * @author Jesimar S. Arantes
 * @since version 4.0.0
 */
public class UtilRoute {
    
    /**
     * Read the file route and put in a object Mission.
     * Note: used by CCQSP4m and FixedRoute
     * @param mission object that stores mission waypoints
     * @param path path the file with route
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
    public static boolean readFileRouteMOSA(Mission mission, String path){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            boolean firstTime = true;
            double lat = 0.0;
            double lng = 0.0;
            double alt = 0.0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);
                if (firstTime){
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, alt));
                    firstTime = false;
                }
                mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
            }
            if (mission.getMission().size() > 0){
                mission.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException]: readFileRoute()");
            return false;
        } catch (IOException ex) {
            System.out.println("Warning [IOException]: readFileRoute()");
            return false;
        }
    }
    
    /**
     * Read the file route and put in a object Mission.
     * Note: used by HGa4m
     * @param mission object that stores mission waypoints
     * @param path path the file with route
     * @param nRoute the number of route
     * @param size the size of mission
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
    public static boolean readFileRouteMOSA(Mission mission, String path, int nRoute, int size) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            boolean firstTime = true;
            double lat = 0.0;
            double lng = 0.0;
            double alt = 0.0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);
                if (firstTime && (nRoute == 0 || nRoute == -2)) {
                    mission.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, alt));
                    firstTime = false;
                }
                mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
            }
            if (mission.getMission().size() > 0) {
                if (nRoute == size - 2) {
                    mission.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException]: readFileRoute()");
            return false;
        } catch (IOException ex) {
            System.out.println("Warning [IOException]: readFileRoute()");
            return false;
        }
    }
    
    /**
     * Read the file route and put in a object Mission.
     * Note: used by IFA (all methods)
     * @param mission object that stores mission waypoints
     * @param path path the file with route
     * @param numberWptJumps the number of waypoints to jump
     * @return {@code true} if success {@code false} otherwise
     * @since version 4.0.0
     */
    public static boolean readFileRouteIFA(Mission mission, String path, int numberWptJumps) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String sCurrentLine;
            double lat = 0.0;
            double lng = 0.0;
            double alt = 0.0;
            int count = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = UtilString.changeValueSeparator(sCurrentLine);
                String s[] = sCurrentLine.split(";");
                lat = Double.parseDouble(s[0]);
                lng = Double.parseDouble(s[1]);
                alt = Double.parseDouble(s[2]);
                if (count >= numberWptJumps) {
                    mission.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
                }
                count++;
            }
            if (mission.getMission().size() > 0) {
                mission.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
            }
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Warning [FileNotFoundException]: readFileRoute()");
            return false;
        } catch (IOException ex) {
            System.out.println("Warning [IOException]: readFileRoute()");
            return false;
        }
    }
    
    /**
     * Execute the route simplifier and compresses the number of route waypoints.
     * @param pathRoute the path of route to be simplifer
     * @param dirRouteSimplifier the directory containing the route simplifier
     * @param factor compression factor
     * @param separator type of separator of fields
     * @since version 4.0.0
     */
    public static void execRouteSimplifier(String pathRoute, String dirRouteSimplifier,
            String factor, String separator){
        try {
            boolean print = true;
            String cmd = "python Route-Simplifier.py " + factor + 
                    " " + "../" + pathRoute + " '" + separator + "'";
            UtilRunThread.dualSingleThreadWaitFor(cmd, new File(dirRouteSimplifier), print, print);
        } catch (IOException ex) {
            System.out.println("Warning [IOException] execRouteSimplifier()");
        } catch (InterruptedException ex) {
            System.out.println("Warning [InterruptedException] execRouteSimplifier()");
        } 
    }
    
}
