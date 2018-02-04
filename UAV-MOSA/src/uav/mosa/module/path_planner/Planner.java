package uav.mosa.module.path_planner;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.struct.Mission3D;
import uav.hardware.aircraft.Drone;
import uav.mosa.struct.ReaderFileConfig;

/**
 *
 * @author jesimar
 */
public abstract class Planner {
    
    final ReaderFileConfig config;
    final String dir;
    final Drone drone;
    final Mission3D pointsMission;    

    public Planner(Drone drone, Mission3D pointsMission) {
        this.config = ReaderFileConfig.getInstance();
        this.dir = config.getDirPlanner();
        this.drone = drone;
        this.pointsMission = pointsMission;        
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
            final Process comp = Runtime.getRuntime().exec(config.getCmdExecPlanner(), null, f);
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
