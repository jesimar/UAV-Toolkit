package uav.gcs.planner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.gcs.struct.Drone;
import uav.generic.struct.mission.Mission3D;

/**
 * Classe que modela o planejador da missão do drone evitando obstáculos.
 * @author Jesimar S. Arantes
 */
public abstract class Planner {
    
    final String dir;
    final Drone drone;
    final Mission3D waypointsMission;
    
    final String fileWaypointsMission;
    final String sizeWpt;
    final String dirFiles;//global
    final String fileGeoBase;//global
    final String cmdExecPlanner;//local
    final String localExec;//global
    final String altitudeFlight;//local
    final String time;//local
    final String delta;//local
    final String maxVel;//local
    final String maxCtrl;//local
    final String speedCruize;//local
    final String typeAircraft;//local

    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param fileWaypointsMission
     * @param sizeWpt
     * @param dirFiles
     * @param fileGeoBase
     * @param dirPlanner
     * @param cmdExecPlanner
     * @param localExec
     * @param altitudeFlight
     * @param time
     * @param delta
     * @param maxVel
     * @param maxCtrl
     * @param speedCruize
     * @param typeAircraft
     */
    public Planner(Drone drone, String fileWaypointsMission, String sizeWpt, String dirFiles,
            String fileGeoBase, String dirPlanner, String cmdExecPlanner, 
            String localExec, String altitudeFlight, String time, String delta, 
            String maxVel, String maxCtrl, String speedCruize, String typeAircraft) {
        this.drone = drone; 
        this.fileWaypointsMission = fileWaypointsMission;
        this.sizeWpt = sizeWpt;
        this.dirFiles = dirFiles;
        this.fileGeoBase = fileGeoBase;
        this.dir = dirPlanner;
        this.cmdExecPlanner = cmdExecPlanner;
        this.localExec = localExec;
        this.altitudeFlight = altitudeFlight;
        this.time = time;
        this.delta = delta;
        this.maxVel = maxVel;
        this.maxCtrl = maxCtrl;
        this.speedCruize = speedCruize;
        this.typeAircraft = typeAircraft;       
        this.waypointsMission = new Mission3D();
    }
            
    public abstract boolean execMission(int i);
    
    public abstract boolean updateFileConfig(int i);
    
    public abstract boolean parseRoute3DtoGeo(int i);
    
    public abstract void clearLogs();
    
    boolean execMethod(){
        try {
            boolean print = false;
            boolean error = false;
            File f = new File(dir);
            String cmd = cmdExecPlanner + " local";
            final Process comp = Runtime.getRuntime().exec(cmd, null, f);
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getInputStream());
                    if (print) {
                        while (sc.hasNextLine()) {
                            System.out.println(sc.nextLine());
                        }
                    }
                    sc.close();
                }
            });
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Scanner sc = new Scanner(comp.getErrorStream());
                    if (error) {
                        while (sc.hasNextLine()) {
                            System.err.println("err:" + sc.nextLine());
                        }
                    }
                    sc.close();
                }
            });
            comp.waitFor();
            return true;
        } catch (IOException ex) {
            StandardPrints.printMsgWarning("Warning [IOException] execMethod()");
            return false;
        } catch (InterruptedException ex) {
            StandardPrints.printMsgWarning("Warning [InterruptedException] execMethod()");
            return false;
        }
    }
}
