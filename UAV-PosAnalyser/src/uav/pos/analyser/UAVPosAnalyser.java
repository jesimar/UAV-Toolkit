package uav.pos.analyser;

import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.Executors;
import uav.generic.module.data_acquisition.DataAcquisition;
import uav.hardware.aircraft.Ararinha;
import uav.hardware.aircraft.Drone;

/**
 *
 * @author jesimar
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
        this.drone = new Ararinha();
        this.dataAcquisition = new DataAcquisition(drone, "IFA");
    }
    
    private void positionsAnalyser() {         
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                        dataAcquisition.getGPS2();
                    } catch (InterruptedException ex) {
                        
                    }
                }
            }
        });
    }
}
