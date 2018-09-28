package uav.ifa.module.path_replanner;

import java.io.File;
import java.io.IOException;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.struct.geom.PointGeo;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.util.UtilRunThread;
import uav.ifa.module.security_manager.SecurityManager;

/**
 * Classe que modela o replanejador de rota do drone evitando obstáculos.
 * @author Jesimar S. Arantes
 */
public abstract class Replanner {
    
    final ReaderFileConfig config;
    final String dir;
    final Drone drone; 
    final PointGeo pointGeo;

    /**
     * Class constructor
     * @param drone instance of the aircraft
     */
    public Replanner(Drone drone) {
        this.config = ReaderFileConfig.getInstance();
        this.dir = config.getDirReplanner();
        this.drone = drone;
        this.pointGeo = SecurityManager.pointGeo;
    }
    
    public abstract boolean exec();
    
    public abstract boolean updateFileConfig();
    
    public abstract boolean parseRoute3DtoGeo();
    
    public abstract void clearLogs();
    
    boolean execMethod() {
        try{
            boolean isPrint = false;
            boolean isPrintError = false;
            String cmd = config.getCmdExecReplanner();
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
