package uav.ifa.module.path_replanner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.struct.geom.PointGeo;
import uav.generic.struct.reader.ReaderFileConfig;
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
            boolean print = false;
            boolean error = false;
            File f = new File(dir);
            final Process comp = Runtime.getRuntime().exec(config.getCmdExecReplanner(), null, f);
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
