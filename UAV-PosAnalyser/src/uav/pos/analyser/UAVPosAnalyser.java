package uav.pos.analyser;

import java.util.Locale;
import java.util.concurrent.Executors;
import lib.uav.hardware.aircraft.Drone;
import lib.uav.hardware.aircraft.DroneFixedWing;
import lib.uav.module.comm.DataAcquisition;
import lib.uav.module.comm.DataAcquisitionS2DK;

/**
 *
 * @author Jesimar S. Arantes
 */
public final class UAVPosAnalyser {        
       
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
       
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("UAV-POSITIONS-ANALYSER");
        UAVPosAnalyser uav = new UAVPosAnalyser();        
        uav.positionsAnalyser(); 
    }    
        
    public UAVPosAnalyser() {
        this.drone = new DroneFixedWing("iDroneAlpha");
        this.dataAcquisition = new DataAcquisitionS2DK(drone, "IFA");
    }
    
    private void positionsAnalyser() {         
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                        dataAcquisition.getLocation();
                    } catch (InterruptedException ex) {
                        
                    }
                }
            }
        });
    }
}
