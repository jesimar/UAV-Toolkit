package uav.gcs.replanner;

import java.io.File;
import java.io.IOException;
import uav.gcs.struct.Drone;
import uav.generic.util.UtilRunThread;

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
            boolean isPrint = false;
            boolean isPrintError = false;
            String cmd = cmdExecReplanner;
            UtilRunThread.dualSingleThread(cmd, new File(dir), isPrint, isPrintError);
            return true;
        } catch (IOException ex) {
            System.err.println("Error [IOException] execMethod()");
            ex.printStackTrace();
            return false;
        } catch (InterruptedException ex) {
            System.err.println("Error [InterruptedException] execMethod()");
            ex.printStackTrace();
            return false;
        }
    }   
}
