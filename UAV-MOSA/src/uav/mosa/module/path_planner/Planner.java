package uav.mosa.module.path_planner;

import java.io.File;
import java.io.IOException;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.struct.constants.TypeCC;
import uav.generic.struct.mission.Mission;
import uav.generic.struct.mission.Mission3D;
import uav.generic.struct.constants.TypeOperationMode;
import uav.generic.struct.constants.TypePlanner;
import uav.generic.struct.geom.PointGeo;
import uav.generic.reader.ReaderFileConfig;
import uav.generic.util.UtilRunThread;
import uav.mosa.module.mission_manager.MissionManager;

/**
 * Classe que modela o planejador da missão do drone evitando obstáculos.
 * @author Jesimar S. Arantes
 */
public abstract class Planner {
    
    final ReaderFileConfig config;
    final String dir;
    final Drone drone;
    final Mission3D waypointsMission;
    final Mission3D mission3D;
    final Mission missionGeo;
    final PointGeo pointGeo;

    /**
     * Class constructor
     * @param drone instance of the aircraft
     * @param waypointsMission waypoints of the mission
     */
    public Planner(Drone drone, Mission3D waypointsMission) {
        this.config = ReaderFileConfig.getInstance();
        this.dir = config.getDirPlanner();
        this.drone = drone;
        this.waypointsMission = waypointsMission; 
        this.mission3D = new Mission3D();
        this.missionGeo = new Mission();
        this.pointGeo = MissionManager.pointGeo;
    }
    
    public abstract void clearLogs();
    
    public Mission3D getMission3D(){
        return mission3D;
    }
    
    public Mission getMissionGeo(){
        return missionGeo;
    }
    
    boolean execMethod(){
        try {
            boolean isPrint = true;
            boolean isPrintError = true;
            String cmd = "";
            if (config.getTypePlanner().equals(TypePlanner.HGA4M) || 
                    config.getTypePlanner().equals(TypePlanner.CCQSP4M)){
                if (config.getOperationMode().equals(TypeOperationMode.SITL)){
                    cmd = config.getCmdExecPlanner() + " local";
                } else if (config.getOperationMode().equals(TypeOperationMode.HITL) ||
                        config.getOperationMode().equals(TypeOperationMode.REAL_FLIGHT)){
                    if (config.getTypeCC().equals(TypeCC.INTEL_EDISON)){
                        cmd = config.getCmdExecPlanner() + " edison";
                    }
                }
            }else if (config.getTypePlanner().equals(TypePlanner.A_STAR4M)){
                cmd = config.getCmdExecPlanner();
            }
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
