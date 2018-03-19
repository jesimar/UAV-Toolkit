package uav.ifa.module.path_replanner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.struct.ReaderFileConfigGlobal;
import uav.ifa.struct.ReaderFileConfig;

/**
 *
 * @author Jesimar S. Arantes
 */
public abstract class Replanner {
    
    final ReaderFileConfig configLocal;
    final ReaderFileConfigGlobal configGlobal;
    final String dir;
    final Drone drone; 

    public Replanner(Drone drone) {
        this.configLocal = ReaderFileConfig.getInstance();
        this.configGlobal = ReaderFileConfigGlobal.getInstance();
        this.dir = configLocal.getDirReplanner();
        this.drone = drone;      
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
            final Process comp = Runtime.getRuntime().exec(configLocal.getCmdExecReplanner(), null, f);
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
