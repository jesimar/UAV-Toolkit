package uav.gcs2.path_replanner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import uav.gcs2.struct.Drone;

/**
 * Classe que modela o replanejador de rota do drone evitando obstáculos.
 * @author Jesimar S. Arantes
 */
public abstract class Replanner {
    
    String dir;
    final Drone drone; 
    
    final String dirFiles;//global
    final String fileGeoBase;//global
    final String cmdExecReplanner;//local
    final String typeAltitudeDecay;//local
    final String time;//local
    final String qtdWpt;//local
    final String delta;//local

    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param dirFiles
     * @param fileGeoBase
     * @param dirReplanner
     * @param cmdExecReplanner
     * @param typeAltitudeDecay
     * @param time
     * @param qtdWpt
     * @param delta
     */
    public Replanner(Drone drone, String dirFiles, String fileGeoBase, 
            String dirReplanner, String cmdExecReplanner, String typeAltitudeDecay, 
            String time, String qtdWpt, String delta) {
        this.drone = drone;      
        this.dirFiles = dirFiles;
        this.fileGeoBase = fileGeoBase;
        this.dir = dirReplanner;
        this.cmdExecReplanner = cmdExecReplanner;
        this.typeAltitudeDecay = typeAltitudeDecay;
        this.time = time;
        this.qtdWpt = qtdWpt;
        this.delta = delta;
    }
    
    public abstract boolean exec();
    
    public abstract boolean updateFileConfig();
    
    public abstract boolean parseRoute3DtoGeo();
    
    public abstract void clearLogs();
    
    boolean execMethod() {
        try{
            boolean print = false;
            boolean error = false;
            File f = new File(dir);
            final Process comp = Runtime.getRuntime().exec(cmdExecReplanner, null, f);
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
            System.out.println("Warning [IOException] execMethod()");
            return false;
        } catch (InterruptedException ex) {
            System.out.println("Warning [InterruptedException] execMethod()");
            return false;
        }
    }   
}
