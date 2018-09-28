package uav.monitoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.Executors;
import uav.generic.module.comm.DataAcquisitionS2DK;
import uav.generic.hardware.aircraft.Drone;
import uav.generic.hardware.aircraft.DroneRotaryWing;
import uav.generic.module.comm.DataAcquisition;

/**
 *
 * @author jesimar
 */
public final class UAVMonitoring {        
       
    private final long timeInit;
    private long timeActual;
    private final Drone drone;
    private final DataAcquisition dataAcquisition;
    private PrintStream printLogAircraft;
       
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("UAV-MONITORING");
        UAVMonitoring uav = new UAVMonitoring();
        uav.createFileLogAircraft();
        uav.monitoringAircraft(); 
    }    
        
    public UAVMonitoring() {
        timeInit = System.currentTimeMillis();
        this.drone = new DroneRotaryWing("iDroneAlpha");    
        this.dataAcquisition = new DataAcquisitionS2DK(drone, "IFA");                
    }
    
    public void createFileLogAircraft(){
        try {
            int i = 0;
            File file;
            do{
                i++;
                file = new File("uav-data" + i + ".csv");  
            }while(file.exists());
            printLogAircraft = new PrintStream(file);
        } catch (FileNotFoundException ex) {
            System.err.println("Error [FileNotFoundException]: createFileLogAircraft()");
            ex.printStackTrace();
            System.exit(0);
        } 
    }
    
    private void monitoringAircraft() {
        System.out.println(drone.title());
        printLogAircraft.println(drone.title());   
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        timeActual = System.currentTimeMillis();
                        double timeDiff = (timeActual - timeInit)/1000.0;
                        drone.getInfo().setTime(timeDiff);
                        dataAcquisition.getAllInfoSensors();    
                        System.out.println(drone.toString());
                        printLogAircraft.println(drone.toString());
                        printLogAircraft.flush();
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        break;
                    }
                }
            }
        });
    }
}
