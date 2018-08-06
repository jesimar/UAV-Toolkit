package uav.pos.analyser;

import java.util.Locale;
import java.util.concurrent.Executors;
import uav.generic.module.data_communication.DataCommunication;
import uav.generic.hardware.aircraft.FixedWing;
import uav.generic.hardware.aircraft.Drone;

/**
 *
 * @author Jesimar S. Arantes
 */
public final class UAVPosAnalyser {        
       
    private final Drone drone;
    private final DataCommunication dataAcquisition;
       
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("UAV-POSITIONS-ANALYSER");
        UAVPosAnalyser uav = new UAVPosAnalyser();        
        uav.positionsAnalyser(); 
    }    
        
    public UAVPosAnalyser() {
        this.drone = new FixedWing("iDroneAlpha");
        this.dataAcquisition = new DataCommunication(drone, "IFA");
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
