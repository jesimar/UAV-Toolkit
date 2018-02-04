package uav.monitoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.Executors;
import lib.color.StandardPrints;
import uav.generic.module.data_acquisition.DataAcquisition;
import uav.hardware.aircraft.Ararinha;
import uav.hardware.aircraft.Drone;

/**
 *
 * @author jesimar
 */
public final class UAVMonitoring {        
       
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
        this.drone = new Ararinha();    
        this.dataAcquisition = new DataAcquisition(drone, "IFA");
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
            StandardPrints.printMsgError2("Error [FileNotFoundException]: createFileLogAircraft()");
            ex.printStackTrace();
            System.exit(0);
        } 
    }
    
    private void monitoringAircraft() {
        System.out.println(drone.toString());
        printLogAircraft.println(drone.title());   
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {                       
                        dataAcquisition.getAllInfoSensors();    
                        System.out.println(drone.toString());
                        printLogAircraft.println(drone.toString());
                        printLogAircraft.flush();
                        drone.incrementTime(1000);
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
