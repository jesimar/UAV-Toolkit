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
 *
 * @author Jesimar S. Arantes
 */
public class UtilRoute {
    
    //used by CCQSP4m and FixedRoute
    public static boolean readFileRouteMOSA(Mission wps, String path){
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
                    wps.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, alt));
                    firstTime = false;
                }
                wps.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
            }
            if (wps.getMission().size() > 0){
                wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
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
    
    //used by HGa4m
    public static boolean readFileRouteMOSA(Mission wps, String path, int nRoute, int size) {
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
                    wps.addWaypoint(new Waypoint(TypeWaypoint.TAKEOFF, 0.0, 0.0, alt));
                    firstTime = false;
                }
                wps.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
            }
            if (wps.getMission().size() > 0) {
                if (nRoute == size - 2) {
                    wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
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
    
    public static boolean readFileRouteIFA(Mission wps, String path, int numberWptJumps) {
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
                    wps.addWaypoint(new Waypoint(TypeWaypoint.GOTO, lat, lng, alt));
                }
                count++;
            }
            if (wps.getMission().size() > 0) {
                wps.addWaypoint(new Waypoint(TypeWaypoint.LAND, lat, lng, 0.0));
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
    
    public static void execRouteSimplifier(String nameRoute, String dirRouteSimplifier,
            String factor, String separator){
        try {
            boolean print = true;
            String cmd = "python Route-Simplifier.py " + factor + 
                    " " + "../" + nameRoute + " '" + separator + "'";
            UtilRunThread.dualSingleThread(cmd, new File(dirRouteSimplifier), print, print);
        } catch (IOException ex) {
            System.out.println("Warning [IOException] execRouteSimplifier()");
        } catch (InterruptedException ex) {
            System.out.println("Warning [InterruptedException] execRouteSimplifier()");
        } 
    }
    
}
