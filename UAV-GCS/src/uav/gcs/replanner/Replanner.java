package uav.gcs.replanner;

import java.io.File;
import java.io.IOException;
import lib.uav.util.UtilRunThread;
import uav.gcs.struct.Drone;

/**
 * Class models mission replanner of drone avoiding obstacles and landing the aircraft.
 * @author Jesimar S. Arantes
 * @since version 2.0.0
 */
public abstract class Replanner {
    
    String dir;
    final Drone drone; 
    
    final String dirFiles;           //global
    final String fileGeoBase;        //global
    final String cmdExecReplanner;   //local
    final String typeAltitudeDecay;  //local
    final String time;               //local
    final String qtdWpt;             //local
    final String delta;              //local

    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param dirFiles directory of main files 
     * @param fileGeoBase name of file geoBase
     * @param dirReplanner replanner directory
     * @param cmdExecReplanner command to exec the replanner
     * @param typeAltitudeDecay type of altitude decay [CONSTANT or LINEAR]
     * @param time processing time
     * @param qtdWpt amount of waypoints
     * @param delta delta parameter/risk allocation
     * @since version 2.0.0
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
    
    /**
     * Execute the replanner
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 2.0.0
     */
    public abstract boolean exec();
    
    /**
     * Updates the configuration file used by the method.
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 2.0.0
     */
    public abstract boolean updateFileConfig();
    
    /**
     * Converts the route in Cartesian coordinates to geographic coordinates
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 2.0.0
     */
    public abstract boolean parseRoute3DtoGeo();
    
    /**
     * Clears log files generated by method
     * @since version 2.0.0
     */
    public abstract void clearLogs();
    
    /**
     * Method that runs the path replanner.
     * @return {@code true} if the execution was successful
     *         {@code false} otherwise
     * @since version 2.0.0
     */
    boolean execMethod() {
        try{
            boolean isPrint = false;
            boolean isPrintError = false;
            String cmd = cmdExecReplanner;
            UtilRunThread.dualSingleThreadWaitFor(cmd, new File(dir), isPrint, isPrintError);
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
